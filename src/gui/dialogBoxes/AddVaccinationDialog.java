package gui.dialogBoxes;

import core.binders.DiseaseModelBinder;
import core.binders.VaccineModelBinder;
import core.repositories.ModelRepository;
import entities.Disease;
import entities.Vaccine;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kamil on 04.01.2017.
 */
public class AddVaccinationDialog
{
    private JFormattedTextField textField;
    private JComboBox diseaseComboBox;
    private JComboBox comboBox;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private ArrayList<Vaccine> allVaccines;

    private JComponent[] components;

    public AddVaccinationDialog(Disease[] diseases, Vaccine[] items)
    {
        allVaccines = new ArrayList<>(Arrays.asList(items));

        textField = new JFormattedTextField(new DateFormatter(dateFormat));
        textField.setValue(new Date());

        diseaseComboBox = new JComboBox(diseases);
        diseaseComboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Disease){
                    Disease disease = (Disease)value;
                    setText(String.valueOf(disease.getName()));
                }
                return this;
            }
        });
        diseaseComboBox.addActionListener(e -> onDiseaseChanged());

        comboBox = new JComboBox();
        comboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Vaccine){
                    Vaccine vaccine = (Vaccine)value;
                    setText(String.valueOf(vaccine.getId()));
                }
                return this;
            }
        });

        components = new JComponent[] { new JLabel("Choose disease:"), diseaseComboBox,
                                        new JLabel("Choose vaccine for the disease:"), comboBox,
                                        new JLabel("Vaccination date:"), textField
        };

        // Fill out vaccination combobox
        onDiseaseChanged();
    }

    public void onDiseaseChanged() {
        Disease disease = (Disease) diseaseComboBox.getSelectedItem();
        int chosenDiseaseId = disease.getId();

        comboBox.removeAllItems();
        allVaccines.forEach( vac -> {
            if (vac.getDiseaseId() == chosenDiseaseId)
                comboBox.addItem(vac);
        });

        if (comboBox.getItemCount() <= 0) {
            comboBox.setEnabled(false);
        } else {
            comboBox.setEnabled(true);
        }
    }

    public int display()
    {
        return JOptionPane.showOptionDialog(null, components, "Add vaccination", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    public Vaccine getSelectedVaccine()
    {
        return (Vaccine) comboBox.getSelectedItem();
    }

    public Date getDate() throws ParseException
    {
        return dateFormat.parse(textField.getText());
    }
}
