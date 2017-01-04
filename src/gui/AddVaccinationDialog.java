package gui;

import entities.Vaccine;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kamil on 04.01.2017.
 */
public class AddVaccinationDialog
{

    private JFormattedTextField textField;
    private JComboBox comboBox;

    private JButton okButton;
    private JButton cancelButton;

    private JComponent[] components;

    public AddVaccinationDialog(Vaccine[] items)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        textField = new JFormattedTextField(dateFormat);
        textField.setValue(new Date());
        comboBox = new JComboBox(items);
        comboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Vaccine){
                    Vaccine vaccine= (Vaccine) value;
                    setText(String.valueOf(vaccine.getId()));
                }
                return this;
            }
        });
        JPanel buttonsPanel = new JPanel();
        okButton = new JButton("OK");
        cancelButton=  new JButton("Cancel");
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        components = new JComponent[] {new JLabel("Choose vaccine:"), comboBox, new JLabel("Vaccination date:"), textField};
    }

    public int display()
    {
        return JOptionPane.showOptionDialog(null, components, "Add vaccination", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
//        dialog.setVisible(true);
    }

    public Vaccine getSelectedVaccine()
    {
        return (Vaccine) comboBox.getSelectedItem();
    }

    public Date getDate()
    {
        return new Date(textField.getText());
    }
}
