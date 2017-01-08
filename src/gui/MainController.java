package gui;

import core.*;
import core.binders.*;
import core.repositories.*;
import entities.*;
import gui.dialogBoxes.AddDiseaseDialog;
import gui.dialogBoxes.AddDogDialog;
import gui.dialogBoxes.AddVaccinationDialog;
import gui.tabPanels.DiseaseHistoryRecordController;
import gui.tabPanels.DogController;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainController
{
    private MainView view;
    private Database database;

    public static MainController getControllerInstance()
    {
        Database database = new Database();
        MainController controller = new MainController(database);
        return controller;
    }

    public MainController(Database database)
    {
        this.database = database;
        initializeDatabase();
        ArrayList<Pair<String, Component>> tabList = initializeTabs();
        this.view = new MainView(this, tabList);
    }

    private ArrayList<Pair<String, Component>> initializeTabs()
    {
        ArrayList<Pair<String, Component>> tabList = new ArrayList<>();

        // Dog tab
        Pair<String, Component> dogTab = new Pair<>("Dogs", new DogController(database).getView().getMainPanel());
        tabList.add(dogTab);

        // Disease history tab
        Pair<String, Component> recordTab = new Pair<>("DiseasesHistory", new DiseaseHistoryRecordController(database).getView().getMainPanel());
        tabList.add(recordTab);

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
}
