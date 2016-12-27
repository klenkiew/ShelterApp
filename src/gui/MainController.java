package gui;

import core.CitiesRepository;
import core.City;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainController
{
    private MainView view;
    private MainModel model;
    private CitiesRepository citiesRepository;

    public static MainController getControllerInstance() throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        MainModel model = new MainModel();
        MainView view = new MainView(model);
        MainController controller = new MainController(view, model);
        view.setMainController(controller);
        return controller;
    }

    public MainController(MainView view, MainModel model)
    {
        this.view = view;
        this.model = model;
        try
        {
            citiesRepository = new CitiesRepository();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        view.setVisible(true);
    }

    public void onButtonClicked(ActionEvent e, int selectedRow)
    {
        Vector data = (Vector) model.getTableModel().getDataVector().get(selectedRow);
        City city = null;
        try
        {
            city = citiesRepository.getById((Integer) data.get(0));
            city.setName(city.getName());
            city.setDistrict(city.getDistrict());
            city.setCountryCode(city.getCountryCode());
            city.setPopulation(city.getPopulation());
            view.displayDialogFor(city);
        } catch (NoSuchFieldException | IllegalAccessException e1)
        {
            String errorMessage = "Model binding error occurred.";
            view.displayError(errorMessage);
            e1.printStackTrace();
        }

    }
}
