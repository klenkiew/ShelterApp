package legacy;

import core.Database;
import core.binders.ModelBinder;
import core.binders.ModelBinderBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamil on 27.12.2016.
 */
    // repository for testing purposes, has nothing to do with dogs at all
public class CitiesRepository
{
    private Database database;
    private ModelBinder<City> binder;

    public CitiesRepository() throws SQLException
    {
        database = new Database();
        HashMap<String, String> columnsToFieldsMappings = new HashMap<>();
        columnsToFieldsMappings.put("Population", "Population");
        binder = new ModelBinderBase<>(columnsToFieldsMappings, () -> new City());
    }

    public ArrayList<City> getAll() throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<HashMap<String, Object>> result = database.query("select * from city", new ArrayList<>());
        ArrayList<City> cities = new ArrayList<>();
        for (HashMap<String, Object> row : result)
        {
            City city = binder.bindModel(row);
            cities.add(city);
        }
        return cities;
    }

    public City getById(int id) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(id);
        ArrayList<HashMap<String, Object>> result = database.query("select * from city where ID = ?", parameters);
        City city = null;
        if (result.isEmpty())
            return city;
        city = binder.bindModel(result.get(0));
        return city;
    }
}
