package gui.tabPanels;

import core.Database;
import core.repositories.DiseaseStatisticsRepository;
import entities.DiseaseStatistics;
import gui.DiseaseStatisticsModel;

import java.sql.SQLException;

/**
 * Created by Kamil on 11.01.2017.
 */
public class DiseaseStatisticsController
{
    Database database;
    DiseaseStatisticsRepository repository;

    DiseaseStatisticsView view;

    DiseaseStatisticsModel model;
    public DiseaseStatisticsController(Database database)
    {
        this.database = database;
        repository = new DiseaseStatisticsRepository(database);

        model = new DiseaseStatisticsModel();
        view = new DiseaseStatisticsView(this, model);
    }

    public void viewStatistics(String diseaseName, String year)
    {
        try
        {
            DiseaseStatistics statistics = repository.getStatistics(diseaseName, year);
            if (statistics == null)
                view.displayInfo("Statistics not found.");
            model.displayStatistics(statistics);

        } catch (SQLException | NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public DiseaseStatisticsView getView()
    {
        return view;
    }
}
