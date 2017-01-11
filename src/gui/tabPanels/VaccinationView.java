package gui.tabPanels;

import core.binders.DiseaseModelBinder;
import core.binders.DogModelBinder;
import core.binders.VaccineModelBinder;
import core.repositories.ModelRepository;
import entities.*;
import gui.MainController;
import gui.MainModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

/**
 * Created by Kamil on 08.01.2017.
 */
public class VaccinationView extends TabView
{
    private JComboBox dogComboBox;
    private JCheckBox dogCheckBox;
    private JComboBox diseaseComboBox;
    private JCheckBox diseaseCheckBox;

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
        dogComboBox.setEnabled(false);
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
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        dogCheckBox = new JCheckBox();
        diseaseCheckBox = new JCheckBox();
        diseaseCheckBox.setEnabled(false); ///< TODO: enable when disease filtering is implemented

        buttonList.add(new JLabel("Choose dog:"));
        pane.add(dogCheckBox, BorderLayout.WEST);
        pane.add(dogComboBox);
        buttonList.add(pane);

        pane = new JPanel();
        pane.setLayout(new BorderLayout());
//        buttonList.add(new JLabel("Choose disease:"));
//        pane.add(diseaseCheckBox, BorderLayout.WEST);
//        pane.add(diseaseComboBox);
        buttonList.add(pane);
        return buttonList;
    }

    @Override
    protected void setUpActionListeners()
    {
        VaccinationController vaccinationController = (VaccinationController) controller;
        table.addMouseListener(vaccinationController.new onMouseDoubleClick());
        diseaseComboBox.addItemListener(e -> filterTableCells());
        dogComboBox.addItemListener(e -> filterTableCells());
        dogCheckBox.addItemListener(e -> onDogCheckboxChange());
        diseaseCheckBox.addItemListener(e -> onDiseaseCheckboxChange());
    }

    @Override
    protected void filterTableCells() {
        ArrayList<RowFilter<TableModel, Object>> filters = new ArrayList<>();
        String selectedItem = (String) tableColumns.getSelectedItem();
        if (MainController.getControllerInstance().isPreloadDatabase())
        {
            RowFilter<TableModel, Object> rf = null;
            try
            {
                if (Objects.equals(selectedItem, "Any"))
                    filters.add(RowFilter.regexFilter('^' + filterText.getText(), IntStream.rangeClosed(0, table.getColumnCount() - 1).toArray()));
                else
                    filters.add(RowFilter.regexFilter('^' + filterText.getText(), table.getColumn(selectedItem).getModelIndex()));
                if (dogComboBox.isEnabled())
                {
                    Dog selectedDog = (Dog) dogComboBox.getSelectedItem();
                    String regex = "^" + String.valueOf(selectedDog.getId()) + "$";
                    filters.add(RowFilter.regexFilter(regex, 2));
                }
            } catch (PatternSyntaxException e)
            {
                return;
            }
            rf = RowFilter.andFilter(filters);
            sorter.setRowFilter(rf);
        }
        else
            if (dogComboBox.isEnabled())
            {
                Dog selectedDog = (Dog) dogComboBox.getSelectedItem();
                if (selectedDog == null)
                    return;
                controller.filterData(filterText.getText(), selectedItem, String.valueOf(selectedDog.getId()));
            }
            else
                if (Objects.equals(selectedItem, "Any"))
                    controller.filterData(filterText.getText());
                else
                    controller.filterData(filterText.getText(), selectedItem);
    }

    private void onDogCheckboxChange()
    {
        if (dogCheckBox.isSelected())
            dogComboBox.setEnabled(true);
        else
            dogComboBox.setEnabled(false);
        if (MainController.getControllerInstance().isPreloadDatabase())
            filterTableCells();
    }

    private void onDiseaseCheckboxChange()
    {
        if (diseaseCheckBox.isSelected())
            diseaseComboBox.setEnabled(true);
        else
            diseaseComboBox.setEnabled(false);
        if (MainController.getControllerInstance().isPreloadDatabase())
            filterTableCells();
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
