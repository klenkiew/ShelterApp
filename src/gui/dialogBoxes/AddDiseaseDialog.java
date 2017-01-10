package gui.dialogBoxes;

import entities.Disease;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamil on 09.01.2017.
 */
public class AddDiseaseDialog
{
    private JComboBox lethalityComboBox;

    private JTextField nameTextField;

    private JComponent[] components;

    private JTextArea symptomsTextArea;
    private JScrollPane symptomsScrollPane;

    private JTextArea descriptionTextArea;
    private JScrollPane descriptionScrollPane;

    public AddDiseaseDialog()
    {
        lethalityComboBox = new JComboBox(new String[] {"Znikoma", "Niska", "Srednia", "Wysoka"});
        nameTextField = new JTextField();

        descriptionTextArea = new JTextArea(10, 20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionScrollPane = new JScrollPane(descriptionTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        symptomsTextArea = new JTextArea(10, 20);
        symptomsTextArea.setLineWrap(true);
        symptomsTextArea.setWrapStyleWord(true);
        symptomsScrollPane = new JScrollPane(symptomsTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        components = new JComponent[] {new JLabel("Name:"), nameTextField, new JLabel("Lethality:"), lethalityComboBox,
                new JLabel("Symptoms:"), symptomsScrollPane, new JLabel("Description:"), descriptionScrollPane};
    }

    public int display()
    {
        return JOptionPane.showOptionDialog(null, components, "Add disease", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    public String getLethalitySelected()
    {
        return (String) lethalityComboBox.getSelectedItem();
    }

    public String getName()
    {
        return nameTextField.getText();
    }

    public String getSymptoms()
    {
        return symptomsTextArea.getText();
    }

    public String getDescription()
    {
        return descriptionTextArea.getText();
    }
}
