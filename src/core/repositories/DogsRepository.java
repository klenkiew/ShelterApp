package core.repositories;

import core.binders.ModelBinder;
import core.Database;
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
        ArrayList<HashMap<String, Object>> result = database.query("SELECT * FROM Pies", new ArrayList<>());
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
        ArrayList<HashMap<String, Object>> result = database.query("SELECT * FROM Pies WHERE Id = ?", parameters);
        Dog dog = null;
        if (result.isEmpty())
            return dog;
        dog = binder.bindModel(result.get(0));
        return dog;
    }

    public void add(Dog dog) throws SQLException
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(dog.getName());
        parameters.add(dog.getAge());
        parameters.add(dog.isAggressive());
        parameters.add(dog.isOpen());
        parameters.add(dog.isVulnerable());
        parameters.add(dog.getHairColor());
        parameters.add(dog.getDescription());
        database.update("INSERT INTO Pies VALUES(?, ?, ?, ?, ?, ?, ?, ?)", parameters);
    }
}
