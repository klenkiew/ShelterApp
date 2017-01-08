package core.repositories;

import core.Database;
import core.binders.ModelBinder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
}