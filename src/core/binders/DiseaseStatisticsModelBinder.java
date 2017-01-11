package core.binders;

import entities.DiseaseStatistics;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamil on 11.01.2017.
 */
public class DiseaseStatisticsModelBinder
{
    public DiseaseStatistics bindModel(ArrayList<HashMap<String, Object>> data) throws NoSuchFieldException, IllegalAccessException
    {
        DiseaseStatistics statistics = new DiseaseStatistics();
        for (HashMap<String, Object> row : data)
        {
            int month = (int) row.get("Miesiac");
            statistics.getNumbersODeaths()[month] = (int) row.get("LiczbaZgonow");
            statistics.getNumbersOfCases()[month] = (int) row.get("LiczbaZachorowan");
        }
        return statistics;
    }
}
