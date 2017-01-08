package gui.tabPanels;

import core.binders.DiseaseModelBinder;
import core.binders.DogModelBinder;
import core.repositories.ModelRepository;
import entities.Disease;
import entities.DiseaseHistoryRecord;
import entities.Dog;
import gui.MainModel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class DiseaseHistoryRecordView extends TabView {

    public DiseaseHistoryRecordView(TabController controller, MainModel mainModel)
    {
        super(controller, mainModel);
    }

    @Override
    protected ArrayList<Component> getAllButtons()
    {
        ArrayList<Component> buttonList = new ArrayList<>();
        return buttonList;
    }

    @Override
    protected void setUpActionListeners()
    {
        DiseaseHistoryRecordController recordController = (DiseaseHistoryRecordController) controller;
        table.addMouseListener(recordController.new onMouseDoubleClick());
    }

    public void displayDialogFor(DiseaseHistoryRecord record)
    {
        Dog dog;
        Disease disease;
        try {
            disease = new ModelRepository<>(controller.database, new DiseaseModelBinder()).getById(record.getDiseaseId());
            dog = new ModelRepository<>(controller.database, new DogModelBinder()).getById(record.getDogId());

            String msg = "Disease name: " + disease.getName()
                    + "\nDisease cases: " + disease.getCaseCount()
                    + "\nDisease deaths: " + disease.getDeathCount()
                    + "\n\nDog name: " + dog.getName()
                    + "\nDog age: " + dog.getAge()
                    + "\n\nStart date: " + record.getDiseaseBeginningDate()
                    + "\nEnd date: " + record.getDiseaseEndDate();
            if (record.isFatal())
                msg += "\n\nResulted in death of the dog.";
            JOptionPane.showMessageDialog(null, msg, "Case details", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
