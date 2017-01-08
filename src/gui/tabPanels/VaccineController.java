package gui.tabPanels;

import core.Database;
import core.binders.DiseaseModelBinder;
import core.binders.VaccineModelBinder;
import core.repositories.ModelRepository;
import entities.Disease;
import entities.Vaccine;
import gui.MainModel;
import gui.VaccineModel;
import gui.dialogBoxes.AddVaccineDialog;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 08.01.2017.
 */
public class VaccineController extends TabController
{
    private VaccineModel model;
    private VaccineView view;

    public VaccineController(Database database)
    {
        super(database);
        view = (VaccineView) getView();
    }
    @Override
    protected MainModel getNewModel()
    {
        ModelRepository<Vaccine> repository = new ModelRepository<>(database, new VaccineModelBinder());
        model = new VaccineModel(repository);
        return model;
    }

    @Override
    protected TabView getNewView(TabController controller, MainModel mainModel)
    {
        return new VaccineView(controller, mainModel);
    }

    public void addVaccineClicked()
    {
        try
        {
            List<Disease> diseases = new ModelRepository<>(database, new DiseaseModelBinder()).getAll();
            AddVaccineDialog addDiseaseDialog = new AddVaccineDialog(diseases.toArray(new Disease[diseases.size()]));
            int result = addDiseaseDialog.display();
            if (result == JOptionPane.CANCEL_OPTION)
                return;
            Disease disease = addDiseaseDialog.getSelectedDisease();
            Vaccine vaccine = new Vaccine();
            vaccine.setDiseaseId(disease.getId());
            vaccine.setHowManyMonthsPerDose(addDiseaseDialog.getMonthsPerDose());
            vaccine.setObligatory(addDiseaseDialog.isObligatorySelected());
            new ModelRepository<>(database, new VaccineModelBinder()).add(vaccine);
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e)
        {
            view.displayError("Database error occurred.");
            e.printStackTrace();
        }
    }
}
