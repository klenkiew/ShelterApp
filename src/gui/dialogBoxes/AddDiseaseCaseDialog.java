package gui.dialogBoxes;

import entities.Disease;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kamil on 08.01.2017.
 */
public class AddDiseaseCaseDialog
{
    private JFormattedTextField textField;
    private JComboBox comboBox;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private JCheckBox checkBox;

    private JButton okButton;
    private JButton cancelButton;

    private JComponent[] components;

    public AddDiseaseCaseDialog(Disease[] items)
    {
        textField = new JFormattedTextField(new DateFormatter(dateFormat));
        textField.setValue(new Date());
        comboBox = new JComboBox(items);
        comboBox.setRenderer(new DefaultListCellRenderer()
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
        JPanel buttonsPanel = new JPanel();
        okButton = new JButton("OK");
        cancelButton=  new JButton("Cancel");
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        checkBox = new JCheckBox("Was fatal: ");
        components = new JComponent[] {new JLabel("Choose disease:"), comboBox, checkBox, new JLabel("Infection date:"), textField};
    }

    public int display()
    {
        return JOptionPane.showOptionDialog(null, components, "Add disease", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
//        dialog.setVisible(true);
    }

    public Disease getSelectedDisease()
    {
        return (Disease) comboBox.getSelectedItem();
    }

    public Date getDate() throws ParseException
    {
        return dateFormat.parse(textField.getText());
    }

    public boolean wasFatal()
    {
        return checkBox.isSelected();
    }
}
