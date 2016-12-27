package core;

import javax.xml.transform.Result;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Created by Kamil on 27.12.2016.
 */
public class Database implements AutoCloseable
{
    private Connection connection;
    private final String username = "kamil";
    private final String password = "1234";
    private final String serverName = "localhost";
    private final String databaseName = "world";

    public Database() throws SQLException
    {
        StringBuilder builder = new StringBuilder("jdbc:mysql://");
        builder.append(serverName);
        builder.append("/");
        builder.append(databaseName);
        String connectionString = builder.toString();
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", username);
        connectionProperties.setProperty("password", password);
        connectionProperties.setProperty("useSSL", "true");
        connectionProperties.setProperty("verifyServerCertificate", "false");
        connectionProperties.setProperty("autoReconnect", "true");
        connection = DriverManager.getConnection(connectionString, connectionProperties);
    }

    public ArrayList<HashMap<String, Object>> query(String query, List<Object> parameters)
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
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    private ArrayList<HashMap<String, Object>> mapResultSet(ResultSet resultSet)
    {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        try
        {
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
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public int update(String query, List<Object> parameters)
    {
        int numberOfRowsUpdated = 0;
        boolean isAutoCommit = false;
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query) )
        {
            isAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(true);
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
            try
            {
                connection.rollback();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally
        {
            try
            {
                connection.setAutoCommit(isAutoCommit);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return numberOfRowsUpdated;
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        close();
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

    public void test() throws SQLException
    {
        Statement statement = connection.createStatement();
        String query = "select * from city";
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        StringBuilder stringBuilder = new StringBuilder();

        while (resultSet.next()) {
            for(int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
                stringBuilder.append(resultSetMetaData.getColumnName(i)+ ": ");
//                System.out.println(">" + Integer.toString(resultSetMetaData.getColumnType(i)));

                if (resultSetMetaData.getColumnType(i)==Types.NVARCHAR || resultSetMetaData.getColumnType(i)==Types.VARCHAR)
                    stringBuilder.append(resultSet.getNString(i));
                if (resultSetMetaData.getColumnType(i)==Types.NUMERIC || resultSetMetaData.getColumnType(i)==Types.INTEGER)
                    stringBuilder.append(Integer.toString(resultSet.getInt(i)));
                if (resultSetMetaData.getColumnType(i)==Types.FLOAT)
                    stringBuilder.append(resultSet.getFloat(i));
                if (resultSetMetaData.getColumnType(i)==Types.CHAR)
                    stringBuilder.append(resultSet.getString(i));
                if (resultSetMetaData.getColumnType(i)==Types.TIMESTAMP) {
                    java.util.Date date =  resultSet.getDate(i);
                    if (resultSet.wasNull())
                        stringBuilder.append("NULL");
                    else
                        stringBuilder.append(date.toString());
                }
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    public void testSqlTypes() throws IllegalAccessException
    {
        Map<Integer, String> result = new HashMap<Integer, String>();

        for (Field field : Types.class.getFields()) {
            result.put((Integer)field.get(null), field.getName());
        }
        System.out.println("Data type: " + result.get(4));
    }
}
