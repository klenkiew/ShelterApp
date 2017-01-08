package gui.tabPanels;

import core.Database;
import core.binders.VaccinationModelBinder;
import core.repositories.ModelRepository;
import entities.DiseaseHistoryRecord;
import entities.Vaccination;
import gui.MainModel;
import gui.VaccinationModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Created by Kamil on 08.01.2017.
 */
public class VaccinationController extends TabController
{
    private VaccinationView view;
    private VaccinationModel model;

    public VaccinationController(Database database)
    {
        super(database);
        this.view = (VaccinationView) getView();
    }

    @Override
    protected MainModel getNewModel()
    {
        ModelRepository<Vaccination> repository = new ModelRepository<>(database, new VaccinationModelBinder());
        model = new VaccinationModel(repository);
        return model;
    }

    @Override
    protected TabView getNewView(TabController controller, MainModel mainModel)
    {
        return new VaccinationView(controller, mainModel);
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
                    Vaccination vaccination = model.getObjById((Integer) target.getValueAt(row, 0));
                    view.displayDialogFor(vaccination);
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
