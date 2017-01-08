package gui.tabPanels;

import entities.Dog;
import gui.MainModel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kamil on 27.12.2016.
 */
public class DogView extends TabView
{
    private JButton addVaccinationButton;
    private JButton addDogButton;
    private JButton addDiseaseButton;


    public DogView(TabController controller, MainModel mainModel)
    {
        super(controller, mainModel);
    }

    @Override
    protected ArrayList<Component> getAllButtons()
    {
        ArrayList<Component> buttonList = new ArrayList<>();

        addVaccinationButton = new JButton("Add vaccination");
        buttonList.add(addVaccinationButton);

        addDiseaseButton =new JButton("Add disease");
        buttonList.add(addDiseaseButton);

        addDogButton = new JButton("Add dog");
        buttonList.add(addDogButton);

        return buttonList;
    }

    @Override
    protected void setUpActionListeners()
    {
        DogController dogController = (DogController) controller;
        table.addMouseListener(dogController.new onMouseDoubleClick());
        addVaccinationButton.addActionListener(e -> dogController.onAddVaccinationClicked());
        addDiseaseButton.addActionListener(e -> dogController.addDiseaseClicked());
        addDogButton.addActionListener(e -> dogController.addDogClicked());
    }

    public void displayDialogFor(Dog dog)
    {
        String traits = "";
        if (dog.isAggressive())
            traits += " aggresive";
        if (dog.isOpen()) {
            if (!traits.isEmpty())
                traits += ",";
            traits += " open";
        }
        if (dog.isVulnerable()) {
            if (!traits.isEmpty())
                traits += ",";
            traits += " vulnerable";
        }

        String msg = "Name: " + dog.getName()
                + "\nAge: " + dog.getAge()
                + "\nHair color: " + dog.getHairColor()
                + "\nDescription: " + dog.getDescription();
        if (!traits.isEmpty())
            msg += "\nTraits: " + traits;
        JOptionPane.showMessageDialog(null, msg, dog.getName() + "'s details", JOptionPane.INFORMATION_MESSAGE);
    }
}
