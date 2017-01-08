package gui.tabPanels;

import entities.Vaccine;
import gui.MainModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kamil on 08.01.2017.
 */
public class VaccineView extends TabView
{
    private JButton addVaccineButton;

    public VaccineView(TabController controller, MainModel mainModel)
    {
        super(controller, mainModel);
    }

    @Override
    protected ArrayList<Component> getAllButtons()
    {
        ArrayList<Component> buttonList = new ArrayList<>();

        addVaccineButton= new JButton("Add vaccine");
        buttonList.add(addVaccineButton);

        return buttonList;
    }

    @Override
    protected void setUpActionListeners()
    {
        VaccineController vaccineController = (VaccineController) controller;
//        table.addMouseListener(vaccineController.new onMouseDoubleClick());
        addVaccineButton.addActionListener(e -> vaccineController.addVaccineClicked());
    }
}
