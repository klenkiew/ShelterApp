package gui.tabPanels;

import core.binders.DiseaseModelBinder;
import core.binders.DogModelBinder;
import core.binders.VaccineModelBinder;
import core.repositories.ModelRepository;
import entities.*;
import gui.MainModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

/**
 * Created by Kamil on 08.01.2017.
 */
public class VaccinationView extends TabView
{
    private JComboBox dogComboBox;
    private JComboBox diseaseComboBox;

    public VaccinationView(TabController controller, MainModel mainModel)
    {
        super(controller, mainModel);
    }

    @Override
    protected ArrayList<Component> getAllButtons()
    {
        ArrayList<Component> buttonList = new ArrayList<>();
        ModelRepository<Dog> dogRepository = new ModelRepository<>(controller.database, new DogModelBinder());
        ModelRepository<Disease> diseaseRepository = new ModelRepository<>(controller.database, new DiseaseModelBinder());

        ArrayList<Dog> dogs = new ArrayList<>();
        ArrayList<Disease> diseases = new ArrayList<>();
        try
        {
            dogs = dogRepository.getAll();
            diseases = diseaseRepository.getAll();
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e)
        {
            displayError("Whatever.");
            e.printStackTrace();
        }
        dogComboBox = new JComboBox(dogs.toArray(new Dog[dogs.size()]));
        diseaseComboBox = new JComboBox(diseases.toArray(new Disease[diseases.size()]));
        diseaseComboBox.setEnabled(false); // TODO: remove when implemented properly
        dogComboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Dog){
                    Dog dog = (Dog) value;
                    setText(String.valueOf(dog.getName()));
                }
                return this;
            }
        });
        diseaseComboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Disease){
                    Disease disease = (Disease) value;
                    setText(String.valueOf(disease.getName()));
                }
                return this;
            }
        });
        buttonList.add(new JLabel("Choose dog:"));
        buttonList.add(dogComboBox);
        buttonList.add(new JLabel("Choose disease:"));
        buttonList.add(diseaseComboBox);
        return buttonList;
    }

    @Override
    protected void setUpActionListeners()
    {
        VaccinationController vaccinationController = (VaccinationController) controller;
        table.addMouseListener(vaccinationController.new onMouseDoubleClick());
        diseaseComboBox.addItemListener(e -> filterTableCells());
        dogComboBox.addItemListener(e -> filterTableCells());
    }

    @Override
    protected void filterTableCells() {
        ArrayList<RowFilter<TableModel, Object>> filters = new ArrayList<>();
        RowFilter<TableModel, Object> rf = null;
        // TODO: add filtering by disease in combo box
        // but it requires query to database because we only have vaccine id in table
        // not disease id
        try {
            filters.add(RowFilter.regexFilter('^' + filterText.getText(), IntStream.rangeClosed(0, table.getColumnCount()-1).toArray()));
            Dog selectedDog = (Dog) dogComboBox.getSelectedItem();
            String regex = String.valueOf('^' + selectedDog.getId() + '$');
            filters.add(RowFilter.regexFilter(regex, 2));
        } catch (PatternSyntaxException e) {
            return;
        }
        rf = RowFilter.andFilter(filters);
        sorter.setRowFilter(rf);
    }

    public void displayDialogFor(Vaccination vaccination)
    {
        Dog dog;
        Vaccine vaccine;
        Disease disease;
        try {
            vaccine = new ModelRepository<>(controller.database, new VaccineModelBinder()).getById(vaccination.getVaccineId());
            disease = new ModelRepository<>(controller.database, new DiseaseModelBinder()).getById(vaccine.getDiseaseId());
            dog = new ModelRepository<>(controller.database, new DogModelBinder()).getById(vaccination.getDogId());

            String msg = "Vaccine: " + vaccine.getId()
                    + "\nMonths per dose: " + vaccine.getHowManyMonthsPerDose()
                    + "\nIs obligatory: " + (vaccine.isObligatory() ? "Yes" : "No")
                    + "\n\nDog name: " + dog.getName()
                    + "\nDog age: " + dog.getAge()
                    + "\n\nDisease: " + disease.getName()
                    + "\nVaccination date: " + vaccination.getVaccinationDate();
            JOptionPane.showMessageDialog(null, msg, "Case details", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
