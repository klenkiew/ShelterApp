package gui;

import core.*;
import core.binders.DogModelBinder;
import core.binders.ModelBinder;
import core.binders.VaccinationModelBinder;
import core.binders.VaccineModelBinder;
import core.repositories.DogsRepository;
import core.repositories.ModelRepository;
import core.repositories.VaccinationRepository;
import core.repositories.VaccineRepository;
import entities.Dog;
import entities.Vaccination;
import entities.Vaccine;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
        ModelRepository<Dog> dogsRepository = new ModelRepository<>(database, binder);
        MainModel model = new MainModel(dogsRepository);
        MainController controller = new MainController(model, database);
        return controller;
    }

    public MainController(MainModel model, Database database)
    {
        this.view = new MainView(this, model);
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

    public class onMouseDoubleClick extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)
            {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();

                try
                {
                    Dog dog = model.getDogById((Integer) target.getValueAt(row, 0));
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
            List<Vaccine> vaccines = new VaccineRepository(database, new VaccineModelBinder()).getAll();
            AddVaccinationDialog addVaccinationDialog = new AddVaccinationDialog(vaccines.toArray(new Vaccine[vaccines.size()]));
            int result = addVaccinationDialog.display();
            if (result == JOptionPane.CANCEL_OPTION)
                return;
            Vaccine vaccine = addVaccinationDialog.getSelectedVaccine();
            Date date = addVaccinationDialog.getDate();
            Vaccination vaccination = new Vaccination();
            vaccination.setVaccinationDate(date);
            vaccination.setVaccineId(vaccine.getId());
            VaccinationRepository vaccinationRepository = new VaccinationRepository(database, new VaccinationModelBinder());
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
}
