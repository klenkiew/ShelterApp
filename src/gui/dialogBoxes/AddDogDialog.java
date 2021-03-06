package gui.dialogBoxes;

import entities.Breed;
import entities.Coop;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamil on 08.01.2017.
 */
public class AddDogDialog
{
    private JComboBox breedComboBox;
    private JComboBox coopComboBox;

    private JCheckBox aggressiveCheckBox;
    private JCheckBox openCheckBox;
    private JCheckBox vulnerableCheckBox;

    private JTextField nameTextField;
    private JTextField ageTextField;
    private JTextField hairColorTextField;

    private JTextArea descriptionTextArea;
    private JScrollPane descriptionScrollPane;

    private JButton okButton;
    private JButton cancelButton;

    private JComponent[] components;

    public AddDogDialog(Breed[] breeds, Coop[] coops)
    {
        initComboBoxes(breeds, coops);

        nameTextField = new JTextField();
        ageTextField = new JTextField();
        hairColorTextField = new JTextField();
        descriptionTextArea = new JTextArea(10, 20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionScrollPane = new JScrollPane(descriptionTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonsPanel = new JPanel();
        okButton = new JButton("OK");
        cancelButton=  new JButton("Cancel");
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        aggressiveCheckBox = new JCheckBox("Is aggresive");
        openCheckBox = new JCheckBox("Is open");
        vulnerableCheckBox = new JCheckBox("Is vulnerable");

        components = new JComponent[] {new JLabel("Name:"), nameTextField,
                new JLabel("Age:"), ageTextField,
                new JLabel("Choose breed"), breedComboBox,
                new JLabel("Hair color"), hairColorTextField,
                new JLabel("Choose coop id"), coopComboBox,
                aggressiveCheckBox,
                openCheckBox,
                vulnerableCheckBox,
                new JLabel("Description:"), descriptionScrollPane};
    }

    public int display()
    {
        return JOptionPane.showOptionDialog(null, components, "Add dog", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }


    private void initComboBoxes(Breed[] breeds, Coop[] coops)
    {
        breedComboBox = new JComboBox(breeds);
        breedComboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Breed){
                    Breed breed = (Breed) value;
                    setText(String.valueOf(breed.getName()));
                }
                return this;
            }
        });

        coopComboBox = new JComboBox(coops);
        coopComboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Coop){
                    Coop coop = (Coop) value;
                    setText(String.valueOf(coop.getId()));
                }
                return this;
            }
        });
        coopComboBox.setName("Choose coop id");
    }

    public Breed getSelectedBreed()
    {
        return (Breed) breedComboBox.getSelectedItem();
    }

    public Coop getSelectedCoop()
    {
        return (Coop) coopComboBox.getSelectedItem();
    }

    public String getName()
    {
        return nameTextField.getText();
    }

    public int getAge()
    {
        int age = -1;
        try {
            age = Integer.parseInt(ageTextField.getText());
        } catch (Exception ex){}

        return age;
    }

    public String getHairColor()
    {
        return hairColorTextField.getText();
    }

    public String getDescription()
    {
        return descriptionTextArea.getText();
    }

    public boolean isAggresiveChecked()
    {
        return aggressiveCheckBox.isSelected();
    }

    public boolean isOpenChecked()
    {
        return openCheckBox.isSelected();
    }

    public boolean isVulnerableChecked()
    {
        return vulnerableCheckBox.isSelected();
    }
}
