package gui;

import entities.Dog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainView
{
    private final String title = "Shelter";
    private JButton detailsButton;
    private JButton addVaccinationButton;

    private JFrame mainFrame;
    private MainController mainController;
    private MainModel mainModel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JPanel leftMenu;

    public MainView(MainModel mainModel)
    {
        initializeComponents(mainModel);
        setUpActionListeners();
    }

    private void initializeComponents(MainModel mainModel)
    {
        this.mainModel = mainModel;
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle(title);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(new BorderLayout());
        tableModel = mainModel.getTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        detailsButton = new JButton("Details");
        addVaccinationButton = new JButton("Add vaccination");
        // prevents stretching in y axis
        detailsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, detailsButton.getPreferredSize().height));
        addVaccinationButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addVaccinationButton.getPreferredSize().height));
        leftMenu = new JPanel();
        leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));
        leftMenu.add(detailsButton);
        leftMenu.add(addVaccinationButton);
        mainFrame.add(leftMenu, BorderLayout.WEST);
    }

    private void setUpActionListeners()
    {
        detailsButton.addActionListener(e -> mainController.onDetailsButtonClicked());
        addVaccinationButton.addActionListener(e -> mainController.onAddVaccinationClicked());
    }

    public void setVisible(boolean value)
    {
        mainFrame.setVisible(value);
    }

    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
    }

    public void displayDialogFor(Dog dog)
    {
        String msg = "Name: " + dog.getName() + "\n Age: " + dog.getAge() + "\n Description: "
                + dog.getDescription() + "\n Aggressiveness: " + dog.isAggressive();
        JOptionPane.showMessageDialog(null, msg, "Dog details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int getSelectedRow()
    {
        return table.getSelectedRow();
    }

    public int[] getSelectedRows()
    {
        return table.getSelectedRows();
    }

}
