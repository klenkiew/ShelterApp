package core;

import entities.Dog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamil on 29.12.2016.
 */
public class DogsRepository
{
    private Database database;
    private ModelBinder<Dog> binder;

    public DogsRepository(Database database, ModelBinder<Dog> binder)
    {
        this.database = database;
        this.binder = binder;
    }

    public ArrayList<Dog> getAll() throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<HashMap<String, Object>> result = database.query("select * from pies", new ArrayList<>());
        ArrayList<Dog> dogs = new ArrayList<>();
        for (HashMap<String, Object> row : result)
        {
            Dog dog = binder.bindModel(row);
            dogs.add(dog);
        }
        return dogs;
    }

    public Dog getById(int id) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(id);
        ArrayList<HashMap<String, Object>> result = database.query("select * from pies where Id = ?", parameters);
        Dog dog = null;
        if (result.isEmpty())
            return dog;
        dog = binder.bindModel(result.get(0));
        return dog;
    }
}
