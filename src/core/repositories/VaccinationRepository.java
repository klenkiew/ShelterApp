package core.repositories;

import core.binders.ModelBinder;
import core.Database;
import entities.Vaccination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamil on 04.01.2017.
 */
public class VaccinationRepository
{
    private Database database;
    private ModelBinder<Vaccination> binder;

    public VaccinationRepository(Database database, ModelBinder<Vaccination> binder)
    {
        this.database = database;
        this.binder = binder;
    }

    public ArrayList<Vaccination> getAll() throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<HashMap<String, Object>> result = database.query("select * from szczepienie", new ArrayList<>());
        ArrayList<Vaccination> vaccinations = new ArrayList<>();
        for (HashMap<String, Object> row : result)
        {
            Vaccination vaccine = binder.bindModel(row);
            vaccinations.add(vaccine);
        }
        return vaccinations;
    }

    public Vaccination getById(int id) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(id);
        ArrayList<HashMap<String, Object>> result = database.query("select * from szczepienie where Id = ?", parameters);
        Vaccination vaccination = null;
        if (result.isEmpty())
            return vaccination;
        vaccination = binder.bindModel(result.get(0));
        return vaccination;
    }

    public void add(Vaccination vaccination) throws SQLException
    {
        ArrayList<Object> parameters = new ArrayList<>();
       // parameters.add(vaccination.getId()); // TODO: comment when auto-increment enabled in database
        parameters.add(null);  // id - auto-increment in database
        parameters.add(vaccination.getVaccinationDate());
        parameters.add(vaccination.getDogId());
        parameters.add(vaccination.getVaccineId());
        database.update("insert into szczepienie values (?, ?, ?, ?)", parameters);
    }
}
