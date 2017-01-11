package gui.tabPanels;

import core.Database;
import core.repositories.ModelRepository;
import gui.MainModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class TabController {
    protected TabView view;
    protected MainModel model;
    protected Database database;

    public TabController(Database database)
    {
        this.database = database;
        initialize();
    }

    public TabView getView()
    {
        return view;
    }

    public MainModel getModel()
    {
        return model;
    }

    protected TabView getNewView(TabController controller, MainModel mainModel)
    {
        return null;
    }

    protected MainModel getNewModel()
    {
        return null;
    }

    protected void filterData(String filterText)
    {
        try
        {
            model.loadFilteredData(filterText);
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e)
        {
            String errorMessage = "Unable to get data from database.";
            view.displayError(errorMessage);
            e.printStackTrace();
        }
    }

    public void filterData(String text, String selectedColumn)
    {
        try
        {
            model.loadFilteredData(text, selectedColumn);
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e)
        {
            String errorMessage = "Unable to get data from database.";
            view.displayError(errorMessage);
            e.printStackTrace();
        }
    }

    private void initialize()
    {
//        try
//        {
            model = getNewModel();
            view = getNewView(this, model);
//            model.loadData();
//        } catch (NoSuchFieldException | IllegalAccessException e)
//        {
//            String errorMessage = "Model binding error occurred.";
//            view.displayError(errorMessage);
//            e.printStackTrace();
//        } catch (SQLException e)
//        {
//            String errorMessage = "Unable to get data from database.";
//            view.displayError(errorMessage);
//            e.printStackTrace();
//        }
    }

    public void filterData(String text, String selectedItem, String dogComboBoxSelectedItem)
    {
        try
        {
            model.loadFilteredData(text, selectedItem, dogComboBoxSelectedItem);
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e)
        {
            String errorMessage = "Unable to get data from database.";
            view.displayError(errorMessage);
            e.printStackTrace();
        }
    }

    public void setSearchButtonEnabled(boolean b)
    {
        view.setSearchButtonEnabled(b);
    }
}
