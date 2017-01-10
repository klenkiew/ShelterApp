package gui.tabPanels;

import core.Database;
import core.binders.DiseaseModelBinder;
import core.repositories.ModelRepository;
import entities.Disease;
import gui.DiseaseModel;
import gui.MainController;
import gui.MainModel;
import gui.dialogBoxes.AddDiseaseDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Created by Kamil on 09.01.2017.
 */
public class DiseaseController extends TabController
{
    private DiseaseModel model;
    private DiseaseView view;

    public DiseaseController(Database database)
    {
        super(database);
        view = (DiseaseView) getView();
    }
    @Override
    protected MainModel getNewModel()
    {
        ModelRepository<Disease> repository = new ModelRepository<>(database, new DiseaseModelBinder());
        model = new DiseaseModel(repository);
        return model;
    }

    @Override
    protected TabView getNewView(TabController controller, MainModel mainModel)
    {
        return new DiseaseView(controller, mainModel);
    }

    public void addDiseaseClicked()
    {
        try
        {
            AddDiseaseDialog addDiseaseDialog = new AddDiseaseDialog();
            int result = addDiseaseDialog.display();
            if (result == JOptionPane.CANCEL_OPTION)
                return;
            Disease disease = new Disease();
            disease.setName(addDiseaseDialog.getName());
            disease.setLethality(addDiseaseDialog.getLethalitySelected());
            disease.setSymptoms(addDiseaseDialog.getSymptoms());
            disease.setDescription(addDiseaseDialog.getDescription());
            new ModelRepository<>(database, new DiseaseModelBinder()).add(disease);
        } catch (SQLException e)
        {
            view.displayError("Database error occurred.");
            e.printStackTrace();
        }
        if (MainController.getControllerInstance().isAutoPreload())
            MainController.getControllerInstance().reloadModels();
    }

    public class onMouseDoubleClick extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)
            {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();

                try
                {
                    Disease disease = model.getObjById((Integer) target.getValueAt(row, 0));
                    view.displayDialogFor(disease);
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
    }
}
