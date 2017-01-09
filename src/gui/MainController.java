package gui;

import core.*;
import gui.tabPanels.*;
import javafx.util.Pair;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainController
{
    private MainView view;
    private Database database;
    private ArrayList<TabController> tabs;
    private static MainController instance;

    public static synchronized MainController getControllerInstance()
    {
        if (instance == null) {
            Database database = new Database();
            instance = new MainController(database);
        }
        return instance;
    }

    private MainController(Database database)
    {
        this.database = database;
        initializeDatabase();
        this.view = new MainView(this, initializeTabs());
    }

    private ArrayList<Pair<String, Component>> initializeTabs()
    {
        ArrayList<Pair<String, Component>> tabList = new ArrayList<>();
        tabs = new ArrayList<>();

        // Dog tab
        TabController tempCtrl = new DogController(database);
        tabs.add(tempCtrl);
        tabList.add(new Pair<>("Dogs", tempCtrl.getView().getMainPanel()));


        // Disease history tab
        tempCtrl = new DiseaseHistoryRecordController(database);
        tabs.add(tempCtrl);
        tabList.add(new Pair<>("DiseasesHistory", tempCtrl.getView().getMainPanel()));

        // Disease history tab
        tempCtrl = new DiseaseController(database);
        tabs.add(tempCtrl);
        tabList.add(new Pair<>("Diseases", tempCtrl.getView().getMainPanel()));


        // Vaccination tab
        tempCtrl = new VaccinationController(database);
        tabs.add(tempCtrl);
        tabList.add(new Pair<>("Vaccinations", tempCtrl.getView().getMainPanel()));


        // Vaccine tab
        tempCtrl = new VaccineController(database);
        tabs.add(tempCtrl);
        tabList.add(new Pair<>("Vaccines", tempCtrl.getView().getMainPanel()));

        return tabList;
    }

    public void run() {
        view.setVisible(true);
    }

    private void initializeDatabase()
    {
        try
        {
            DatabaseConnectionProperties properties = new ConfigurationManager().readDatabaseConnectionProperties();
            database.connect(properties);
        } catch (SQLException e)
        {
            String errorMessage = "Unable to get data from database.";
            view.displayError(errorMessage);
            e.printStackTrace();
        } catch (IOException e)
        {
            String errorMessage = "Couldn't read database configuration file.";
            view.displayError(errorMessage);
            e.printStackTrace();
        } catch (ParseException e)
        {
            String errorMessage = "Database configuration file parsing error.";
            view.displayError(errorMessage);
            e.printStackTrace();
        }
    }

    // TODO: call only if 'preloadDatabase' selected
    public void reloadModels()
    {
        try {
            for (TabController tab : tabs)
                tab.getModel().loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
