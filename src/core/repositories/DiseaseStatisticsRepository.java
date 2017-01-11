package core.repositories;

import core.Database;
import core.binders.DiseaseStatisticsModelBinder;
import entities.DiseaseStatistics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kamil on 11.01.2017.
 */
public class DiseaseStatisticsRepository
{
    Database database;
    DiseaseStatisticsModelBinder binder = new DiseaseStatisticsModelBinder();

    public DiseaseStatisticsRepository(Database database)
    {
        this.database = database;
    }

    public DiseaseStatistics getStatistics(String diseaseName, String year) throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        String query = "SELECT Rok, Miesiac, Nazwa, StatystykaChorob.LiczbaZachorowan,  StatystykaChorob.LiczbaZgonow " +
                "FROM StatystykaChorob INNER JOIN " +
                "( SELECT Nazwa, Id FROM Choroba WHERE Nazwa = ? LIMIT 1) ch ON ch.Id = StatystykaChorob.ChorobaId" +
                " WHERE Rok = ? ORDER By Miesiac";
        List<Object> parameters = new ArrayList<>();
        parameters.add(diseaseName);
        parameters.add(year);
        ArrayList<HashMap<String, Object>> result = database.query(query, parameters);
        if (result == null || result.isEmpty())
            return null;
        DiseaseStatistics statistics = binder.bindModel(result);
        statistics.setDiseaseName(diseaseName);
        statistics.setYear(Integer.parseInt(year));
        return statistics;
    }
}
