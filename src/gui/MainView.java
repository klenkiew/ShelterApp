package gui;

import gui.tabPanels.TabController;
import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainView
{
    private final String title = "Shelter";
    private MainController mainController;
    private JFrame mainFrame;
    private JMenuItem exitItem;
    private JMenuItem forceReloadItem;
    private JCheckBoxMenuItem preloadItem;
    private JCheckBoxMenuItem autoPreloadItem;
    private JMenuItem reconntectItem;


    public MainView(MainController controller, ArrayList<Pair<String, Component>> tabs)
    {
        this.mainController = controller;
        initializeComponents(tabs);
        initializeActionListeners();
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

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        exitItem = new JMenuItem("Exit");
        forceReloadItem = new JMenuItem("Reload database");
        forceReloadItem.setEnabled(false);
        reconntectItem = new JMenuItem("Reconnect");
        preloadItem = new JCheckBoxMenuItem("Database preload");
        autoPreloadItem = new JCheckBoxMenuItem("Auto-reload on change");
        autoPreloadItem.setEnabled(false);
        menu.add(reconntectItem);
        menu.addSeparator();
        menu.add(preloadItem);
        menu.add(forceReloadItem);
        menu.add(autoPreloadItem);
        menu.addSeparator();
        menu.add(exitItem);
        menuBar.add(menu);
        mainFrame.setJMenuBar(menuBar);

        // Add current tab container to main tabbed panel
        JTabbedPane tabPane = new JTabbedPane();
        for (Pair<String, Component> tab : tabs)
            tabPane.addTab(tab.getKey(), tab.getValue());

        mainFrame.add(tabPane);
    }

    private void initializeActionListeners()
    {
        exitItem.addActionListener(e ->  mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING)));
        preloadItem.addActionListener(e ->
        {
            boolean preloadItemSelected = preloadItem.isSelected();
            mainController.setPreloadDatabase(preloadItemSelected);
            if (preloadItemSelected)
                mainController.reloadModels();
            forceReloadItem.setEnabled(preloadItemSelected);
            autoPreloadItem.setEnabled(preloadItemSelected);
            for (TabController tab : mainController.getTabs())
                tab.setSearchButtonEnabled(!preloadItemSelected);
        });
        forceReloadItem.addActionListener(e -> mainController.reloadModels());
        autoPreloadItem.addActionListener(e -> mainController.setAutoPreload(autoPreloadItem.isSelected()));
        reconntectItem.addActionListener(e -> mainController.reconnect());
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
