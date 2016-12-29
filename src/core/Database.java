package core;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Created by Kamil on 27.12.2016.
 */
public class Database implements AutoCloseable
{
    private Connection connection;

    public Database()
    {
    }

    public void connect(DatabaseConnectionProperties properties) throws SQLException
    {
        StringBuilder builder = new StringBuilder("jdbc:mysql://");
        builder.append(properties.getServerName());
        builder.append("/");
        builder.append(properties.getDatabaseName());
        String connectionString = builder.toString();
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", properties.getUserName());
        connectionProperties.setProperty("password", String.valueOf(properties.getPassword()));
        connectionProperties.setProperty("useSSL", String.valueOf(properties.isUsingSSL()));
        connectionProperties.setProperty("verifyServerCertificate", String.valueOf(properties.shouldVerifyServerCertificate()));
        connectionProperties.setProperty("autoReconnect", String.valueOf(properties.shouldAutoReconnect()));
        connection = DriverManager.getConnection(connectionString, connectionProperties);
    }

    public ArrayList<HashMap<String, Object>> query(String query, List<Object> parameters) throws SQLException
    {
        ResultSet resultSet = null;
        ArrayList<HashMap<String, Object>> result = null;
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query) )
        {
            int i = 1;
            for (Object parameter : parameters)
            {
                preparedStatement.setObject(i++, parameter);
            }
            resultSet = preparedStatement.executeQuery();
            result = mapResultSet(resultSet);
        }
        return result;
    }

    private ArrayList<HashMap<String, Object>> mapResultSet(ResultSet resultSet) throws SQLException
    {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        while (resultSet.next())
        {
            HashMap<String, Object> row = new HashMap<>();
            for (int i = 1; i <= numberOfColumns; ++i)
            {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                row.put(columnName, value);
            }
            result.add(row);
        }
        return result;
    }

    public int update(String query, List<Object> parameters) throws SQLException
    {
        int numberOfRowsUpdated = 0;
        boolean isAutoCommit = false;
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query) )
        {
            isAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            int i = 0;
            for (Object parameter : parameters)
            {
                preparedStatement.setObject(i++, parameter);
            }
            numberOfRowsUpdated = preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e)
        {
            rollback();
            throw e;
        }
        finally
        {
            setAutoCommit(isAutoCommit);
        }
        return numberOfRowsUpdated;
    }

    private void setAutoCommit(boolean value)
    {
        try
        {
            connection.setAutoCommit(value);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void rollback()
    {
        try
        {
            connection.rollback();
        } catch (SQLException e1)
        {
            e1.printStackTrace();
        }
    }

    @Override
    public void close()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                // who cares
            }
            connection = null;
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        close();
    }

    static
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
