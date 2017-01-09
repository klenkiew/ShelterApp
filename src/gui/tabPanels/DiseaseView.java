package gui.tabPanels;

import entities.Disease;
import gui.MainModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kamil on 09.01.2017.
 */
public class DiseaseView extends TabView
{
    private JButton addDiseaseButton;

    public DiseaseView(TabController controller, MainModel mainModel)
    {
        super(controller, mainModel);
    }

    @Override
    protected ArrayList<Component> getAllButtons()
    {
        ArrayList<Component> buttonList = new ArrayList<>();

        addDiseaseButton = new JButton("Add disease");
        buttonList.add(addDiseaseButton);

        return buttonList;
    }

    @Override
    protected void setUpActionListeners()
    {
        DiseaseController diseaseController = (DiseaseController) controller;
        addDiseaseButton.addActionListener(e -> diseaseController.addDiseaseClicked());
        table.addMouseListener(diseaseController.new onMouseDoubleClick());
    }

    public void displayDialogFor(Disease disease)
    {
        String msg = "Name: " + disease.getName()
                + "\n\nSymptoms: " + disease.getSymptoms()
                + "\n\nDescription: " + disease.getDescription();
        JOptionPane.showMessageDialog(null, msg, disease.getName() + "'s details", JOptionPane.INFORMATION_MESSAGE);
    }
}
