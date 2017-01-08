package gui.dialogBoxes;

import entities.Disease;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamil on 08.01.2017.
 */
public class AddVaccineDialog
{
    private JComboBox diseaseComboBox;

    private JCheckBox obligatoryCheckBox;

    private JTextField monthsTextField;

    private JComponent[] components;

    public AddVaccineDialog(Disease[] items)
    {
        obligatoryCheckBox = new JCheckBox("Is obligatory");
        monthsTextField = new JTextField();

        diseaseComboBox = new JComboBox(items);
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
        components = new JComponent[] {new JLabel("How many months per dose:"), monthsTextField, obligatoryCheckBox,
                new JLabel("Disease:"), diseaseComboBox};
    }

    public int display()
    {
        return JOptionPane.showOptionDialog(null, components, "Add vaccine", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    public boolean isObligatorySelected()
    {
        return obligatoryCheckBox.isSelected();
    }

    public int getMonthsPerDose()
    {
        return Integer.parseInt(monthsTextField.getText());
    }

    public Disease getSelectedDisease()
    {
        return (Disease) diseaseComboBox.getSelectedItem();
    }
}
