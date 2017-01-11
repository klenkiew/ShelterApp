package gui;

import entities.DiseaseStatistics;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Created by Kamil on 11.01.2017.
 */
public class DiseaseStatisticsModel
{
    private DefaultTableModel resultModel;

    private final String[] months = {
        "January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"};

    public DiseaseStatisticsModel()
    {
        String[] columnNames = {"Month", "Number of cases", "Number of deaths"};
        resultModel = new DefaultTableModel(columnNames, 0);
    }

    public TableModel getTableModel()
    {
        return resultModel;
    }

    public void displayStatistics(DiseaseStatistics statistics)
    {
        resultModel.setRowCount(0);

        if (statistics == null)
            return;

        int[] numbersOfCases = statistics.getNumbersOfCases();
        int[] numbersODeaths = statistics.getNumbersODeaths();
        for (int i = 1; i <= 12; ++i)
            resultModel.addRow(new Object[]{months[i-1], numbersOfCases[i], numbersODeaths[i]});
        resultModel.addRow(new Object[] {"Total", numbersOfCases[0], numbersODeaths[0]});
    }
}
