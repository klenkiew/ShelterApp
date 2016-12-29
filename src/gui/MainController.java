package gui;

import core.*;
import entities.Dog;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainController
{
    private MainView view;
    private MainModel model;
    private Database database;

    public static MainController getControllerInstance()
    {
        Database database = new Database();
        ModelBinder<Dog> binder = new DogModelBinder();
        DogsRepository dogsRepository = new DogsRepository(database, binder);
        MainModel model = new MainModel(dogsRepository);
        MainView view = new MainView(model);
        MainController controller = new MainController(view, model, database);
        view.setMainController(controller);
        return controller;
    }

    public MainController(MainView view, MainModel model, Database database)
    {
        this.view = view;
        this.model = model;
        this.database = database;
    }

    public void run()
    {
        view.setVisible(true);
        try
        {
            DatabaseConnectionProperties properties = new ConfigurationManager().readDatabaseConnectionProperties();
            database.connect(properties);
            model.loadData();
        } catch (NoSuchFieldException | IllegalAccessException e)
        {
            String errorMessage = "Model binding error occurred.";
            view.displayError(errorMessage);
            e.printStackTrace();
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

    public void onDetailsButtonClicked()
    {
        int selectedRow = view.getSelectedRow();
        // no row selected
        if (selectedRow == -1)
            return;
        Vector data = (Vector) model.getTableModel().getDataVector().get(selectedRow);
        try
        {
            Dog dog = model.getDogById((Integer) data.get(0));
            view.displayDialogFor(dog);
        }
        catch (NoSuchFieldException | IllegalAccessException e1)
        {
            String errorMessage = "Model binding error occurred.";
            view.displayError(errorMessage);
            e1.printStackTrace();
        } catch (SQLException e1)
        {
            String errorMessage = "Database error occurred.";
            view.displayError(errorMessage);
            e1.printStackTrace();
        }
    }
}
