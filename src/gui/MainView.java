package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import core.City;

import java.awt.*;
import java.sql.SQLException;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainView
{
    private final String title = "Schronisko";
    private JButton button;

    private JFrame mainFrame;
    private MainController mainController;
    private MainModel mainModel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

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
        button = new JButton("losowy button");
        mainFrame.add(button, BorderLayout.WEST);
    }

    private void setUpActionListeners()
    {
        button.addActionListener(e -> mainController.onButtonClicked(e, table.getSelectedRow()));
    }

    public void setVisible(boolean value)
    {
        mainFrame.setVisible(value);
    }

    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
    }

    public void displayDialogFor(City city)
    {
        String msg = "Name: " + city.getName() + "\n District: " + city.getDistrict() + "\n Population: "
                + city.getPopulation() + "\n Country Code: " + city.getCountryCode();
        JOptionPane.showMessageDialog(null, msg, "City details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
