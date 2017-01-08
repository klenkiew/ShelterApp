package gui.tabPanels;

import core.Database;
import core.binders.*;
import core.repositories.ModelRepository;
import entities.*;
import gui.DiseaseHistoryRecordModel;
import gui.MainModel;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


/**
 * Created by mkk-1 on 08/01/2017.
 */
public class DiseaseHistoryRecordController extends TabController
{
    private DiseaseHistoryRecordView view;
    private DiseaseHistoryRecordModel model;

    public DiseaseHistoryRecordController(Database database)
    {
        super(database);
        this.view = (DiseaseHistoryRecordView) getView();
    }

    @Override
    protected MainModel getNewModel()
    {
        ModelRepository<DiseaseHistoryRecord> repository = new ModelRepository<>(database, new DiseaseHistoryModelBinder());
        model = new DiseaseHistoryRecordModel(repository);
        return model;
    }

    @Override
    protected TabView getNewView(TabController controller, MainModel mainModel)
    {
        return new DiseaseHistoryRecordView(controller, mainModel);
    }

    public class onMouseDoubleClick extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)
            {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();

                try
                {
                    DiseaseHistoryRecord record = model.getObjById((Integer) target.getValueAt(row, 0));
                    view.displayDialogFor(record);
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