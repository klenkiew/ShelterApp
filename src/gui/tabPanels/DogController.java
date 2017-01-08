package gui.tabPanels;

import core.*;
import core.binders.*;
import core.repositories.*;
import entities.*;
import gui.DogModel;
import gui.MainModel;
import gui.dialogBoxes.AddDiseaseDialog;
import gui.dialogBoxes.AddDogDialog;
import gui.dialogBoxes.AddVaccinationDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Kamil on 27.12.2016.
 */
public class DogController extends TabController
{
    private DogView view;
    private DogModel model;

    public DogController(Database database)
    {
        super(database);
        this.view = (DogView) getView();
    }

    @Override
    protected MainModel getNewModel()
    {
        ModelRepository<Dog> repository = new ModelRepository<>(database, new DogModelBinder());
        model = new DogModel(repository);
        return model;
    }

    @Override
    protected TabView getNewView(TabController controller, MainModel mainModel)
    {
        return new DogView(controller, mainModel);
    }

    public void addDogClicked()
    {
        // TODO: implement method
        new AddDogDialog(new Breed[0], new Coop[0]).display();
        return;
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
                    Dog dog = model.getObjById((Integer) target.getValueAt(row, 0));
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
    }

    public void onAddVaccinationClicked()
    {
        int[] rows = view.getSelectedRows();
        if (rows.length == 0)
            return;

        try
        {
            List<Vaccine> vaccines = new ModelRepository<>(database, new VaccineModelBinder()).getAll();
            AddVaccinationDialog addVaccinationDialog = new AddVaccinationDialog(vaccines.toArray(new Vaccine[vaccines.size()]));
            int result = addVaccinationDialog.display();
            if (result == JOptionPane.CANCEL_OPTION)
                return;
            Vaccine vaccine = addVaccinationDialog.getSelectedVaccine();
            Date date = addVaccinationDialog.getDate();
            Vaccination vaccination = new Vaccination();
            vaccination.setVaccinationDate(date);
            vaccination.setVaccineId(vaccine.getId());
            ModelRepository<Vaccination> vaccinationRepository = new ModelRepository<>(database, new VaccinationModelBinder());
            for (int row : rows)
            {
                vaccination.setDogId(Integer.parseInt(view.getCellValue(row, 0).toString()));
                vaccinationRepository.add(vaccination);
            }
        } catch (NoSuchFieldException | IllegalAccessException | SQLException | ParseException e)
        {
            String errorMessage = "Database error occurred.";
            view.displayError(errorMessage);
            e.printStackTrace();
        }
    }

    public void addDiseaseClicked()
    {
        int[] rows = view.getSelectedRows();
        if (rows.length == 0)
            return;

        try
        {

            List<Disease> diseases = new ModelRepository<>(database, new DiseaseModelBinder()).getAll();
            AddDiseaseDialog addDiseaseDialog = new AddDiseaseDialog(diseases.toArray(new Disease[diseases.size()]));
            int result = addDiseaseDialog.display();
            if (result == JOptionPane.CANCEL_OPTION)
                return;
            Disease disease = addDiseaseDialog.getSelectedDisease();
            Date date = addDiseaseDialog.getDate();
            boolean wasFatal = addDiseaseDialog.wasFatal();
            DiseaseHistoryRecord diseaseHistoryRecord = new DiseaseHistoryRecord();
            diseaseHistoryRecord.setDiseaseBeginningDate(date);
            diseaseHistoryRecord.setDiseaseId(disease.getId());
            diseaseHistoryRecord.setFatal(wasFatal);
            ModelRepository<DiseaseHistoryRecord> diseaseHistoryRepository = new ModelRepository<>(database, new DiseaseHistoryModelBinder());
            for (int row : rows)
            {
                diseaseHistoryRecord.setDogId(Integer.parseInt(view.getCellValue(row, 0).toString()));
                diseaseHistoryRepository.add(diseaseHistoryRecord);
            }
        } catch (NoSuchFieldException | IllegalAccessException | SQLException | ParseException e)
        {
            String errorMessage = "Database error occurred.";
            view.displayError(errorMessage);
            e.printStackTrace();
        }
    }
}
