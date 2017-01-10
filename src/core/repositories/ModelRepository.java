package core.repositories;

import core.Database;
import core.binders.ModelBinder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkk-13 on 08/01/2017.
 */
public class ModelRepository<ModelType> {
    protected Database database;
    protected ModelBinder<ModelType> binder;

    public ModelRepository(Database database, ModelBinder<ModelType> binder) {
        this.database = database;
        this.binder = binder;
    }

    public ArrayList<ModelType> getAll() throws NoSuchFieldException, IllegalAccessException, SQLException {
        String query = "SELECT * FROM " + binder.getTableName();
        ArrayList<HashMap<String, Object>> result = database.query(query, new ArrayList<>());
        ArrayList<ModelType> objectList = new ArrayList<>();
        for (HashMap<String, Object> row : result) {
            ModelType object = binder.bindModel(row);
            objectList.add(object);
        }
        return objectList;
    }

    public ModelType getById(int id) throws NoSuchFieldException, IllegalAccessException, SQLException {
        String query = "SELECT * FROM " + binder.getTableName() + " WHERE Id = ?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(id);
        ArrayList<HashMap<String, Object>> result = database.query(query, parameters);
        ModelType object = null;
        if (result.isEmpty())
            return object;
        object = binder.bindModel(result.get(0));
        return object;
    }

    public ArrayList<ModelType> getByText(String text) throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        ArrayList<String> columnNames = binder.getColumnNames();
        String query = "SELECT * FROM " + binder.getTableName() + " WHERE ";
        for (int i = 0; i < columnNames.size(); i++)
        {
            String column = columnNames.get(i);
            query += column + " LIKE \'" + text + "%\'";
            if (i != columnNames.size() - 1)
                query += " OR ";
        }
        ArrayList<HashMap<String, Object>> result = database.query(query, new ArrayList<>());
        ArrayList<ModelType> objectList = new ArrayList<>();
        for (HashMap<String, Object> row : result) {
            ModelType object = binder.bindModel(row);
            objectList.add(object);
        }
        return objectList;
    }

    public ArrayList<ModelType> getByColumnText(String text, String columnName) throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        String query = "SELECT * FROM " + binder.getTableName() + " WHERE ";
        String databaseColumn = binder.getDatabaseColumnNameFor(columnName);
        query += databaseColumn + " LIKE \'" + text + "%\'";
        ArrayList<HashMap<String, Object>> result = database.query(query, new ArrayList<>());
        ArrayList<ModelType> objectList = new ArrayList<>();
        for (HashMap<String, Object> row : result) {
            ModelType object = binder.bindModel(row);
            objectList.add(object);
        }
        return objectList;
    }

    // map: column name - value
    public ArrayList<ModelType> getByComplexColumnText(Map<String, String> like, Map<String, String> equal) throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        String query = "SELECT * FROM " + binder.getTableName() + " WHERE ";
        int i = 0;
        for (Map.Entry entry : like.entrySet())
        {
            String databaseColumn = binder.getDatabaseColumnNameFor((String) entry.getKey());
            if (databaseColumn == null)
                continue;
            query += databaseColumn + " LIKE \'" + entry.getValue() + "%\' ";
            if (++i < like.entrySet().size())
                query += " OR ";
            else
                if (!equal.isEmpty())
                    query += " AND ";
        }
        i = 0;
        for (Map.Entry entry : equal.entrySet())
        {
            String databaseColumn = binder.getDatabaseColumnNameFor((String) entry.getKey());
            query += databaseColumn + " = \'" + entry.getValue() + "\' ";
            if (++i < like.entrySet().size())
            query += " OR ";
        }
        ArrayList<HashMap<String, Object>> result = database.query(query, new ArrayList<>());
        ArrayList<ModelType> objectList = new ArrayList<>();
        for (HashMap<String, Object> row : result) {
            ModelType object = binder.bindModel(row);
            objectList.add(object);
        }
        return objectList;
    }

    public void add(ModelType object) throws SQLException
    {
        List<Object> parameters = binder.getAllParameters(object);

        String query = "INSERT INTO " + binder.getTableName() + " VALUES (";
        for (int i = 0; i < parameters.size(); i++)
            query += "?,";
        query = query.substring(0, query.length() - 1) + ")";

        System.out.println(query);
        System.out.println(parameters);
        database.update(query, parameters);
    }
}