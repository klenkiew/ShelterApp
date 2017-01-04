package core.repositories;

import core.binders.ModelBinder;
import core.Database;
import entities.Vaccine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamil on 04.01.2017.
 */
public class VaccineRepository
{
    private Database database;
    private ModelBinder<Vaccine> binder;

    public VaccineRepository(Database database, ModelBinder<Vaccine> binder)
    {
        this.database = database;
        this.binder = binder;
    }

    public ArrayList<Vaccine> getAll() throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<HashMap<String, Object>> result = database.query("select * from szczepionka", new ArrayList<>());
        ArrayList<Vaccine> vaccines = new ArrayList<>();
        for (HashMap<String, Object> row : result)
        {
            Vaccine vaccine = binder.bindModel(row);
            vaccines.add(vaccine);
        }
        return vaccines;
    }

    public Vaccine getById(int id) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(id);
        ArrayList<HashMap<String, Object>> result = database.query("select * from szczepionka where Id = ?", parameters);
        Vaccine vaccine = null;
        if (result.isEmpty())
            return vaccine;
        vaccine = binder.bindModel(result.get(0));
        return vaccine;
    }
}
