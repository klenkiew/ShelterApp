package gui.tabPanels;

import gui.DiseaseStatisticsModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamil on 11.01.2017.
 */
public class DiseaseStatisticsView
{
    private DiseaseStatisticsController controller;
    private DiseaseStatisticsModel model;

    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel resultPanel;

    // input panel
    private JTextField diseaseNameTextField;
    private JTextField yearTextField;
    private JButton viewStatisticsButton;

    // result panel
    private JLabel resultLabel;
    private JTable resultTable;


    public DiseaseStatisticsView(DiseaseStatisticsController controller, DiseaseStatisticsModel model)
    {
        this.controller = controller;
        this.model = model;

        mainPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        resultPanel = new JPanel(new BorderLayout());

        diseaseNameTextField = new JTextField();
        yearTextField = new JTextField();
        viewStatisticsButton = new JButton("View statistics");

        inputPanel.add(new JLabel("Disease name:"));
        inputPanel.add(diseaseNameTextField);
        inputPanel.add((new JLabel("Year: ")));
        inputPanel.add(yearTextField);
        inputPanel.add(viewStatisticsButton);

//        resultLabel = new JLabel("");
        resultTable = new JTable(model.getTableModel());
//        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultTable), BorderLayout.CENTER);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        initializeActionListeners();
    }

    public void initializeActionListeners()
    {
        viewStatisticsButton.addActionListener(e ->
        {
            String diseaseName = diseaseNameTextField.getText();
            String year = yearTextField.getText();
            controller.viewStatistics(diseaseName, year);
        });
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    public void displayInfo(String msg)
    {
        JOptionPane.showMessageDialog(mainPanel, msg, "Not found", JOptionPane.INFORMATION_MESSAGE, null);
    }
}
