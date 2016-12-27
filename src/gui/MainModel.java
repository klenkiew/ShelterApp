package gui;

import core.CitiesRepository;
import core.City;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainModel
{
    private DefaultTableModel tableModel;
    private CitiesRepository citiesRepository;

    public MainModel() throws SQLException, NoSuchFieldException, IllegalAccessException
    {
        citiesRepository = new CitiesRepository();
        String[] columnTitles = {"Id", "Name", "Population"};
        int rowCount = 0;
        tableModel = new DefaultTableModel(columnTitles, rowCount)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        for(City city : getData())
        {
            Object[] rowData = new Object[] {city.getID(), city.getName(), city.getPopulation()};
            tableModel.addRow(rowData);
        }
    }

    public City[] getData() throws NoSuchFieldException, IllegalAccessException
    {
        ArrayList<City> cities = new ArrayList<>();
        cities = citiesRepository.getAll();
        City[] data = new City[cities.size()];
        return cities.toArray(data);
    }

    public DefaultTableModel getTableModel()
    {
        return tableModel;
    }
}
