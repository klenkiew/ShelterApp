package gui;

import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainView
{
    private final String title = "Shelter";
    private MainController mainController;
    private JFrame mainFrame;


    public MainView(MainController controller, ArrayList<Pair<String, Component>> tabs)
    {
        this.mainController = controller;
        initializeComponents(tabs);
    }

    private void initializeComponents(ArrayList<Pair<String, Component>> tabs)
    {
        // Main window setup
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle(title);
        mainFrame.setMinimumSize(new Dimension(500, 300));
        mainFrame.setSize(new Dimension(600, 600));
        mainFrame.setLocationRelativeTo(null); ///< Used to center window
        mainFrame.setLayout(new BorderLayout());

        // Add current tab container to main tabbed panel
        JTabbedPane tabPane = new JTabbedPane();
        for (Pair<String, Component> tab : tabs)
            tabPane.addTab(tab.getKey(), tab.getValue());

        mainFrame.add(tabPane);
    }

    public void setVisible(boolean value)
    {
        mainFrame.setVisible(value);
    }

    public void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
