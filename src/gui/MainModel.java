package gui;

import core.repositories.DogsRepository;
import entities.Dog;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainModel
{
    private DefaultTableModel tableModel;
    private DogsRepository dogsRepository;

    public MainModel(DogsRepository dogsRepository)
    {
        this.dogsRepository = dogsRepository;
        String[] columnTitles = {"Id", "Name", "Age", "Description"};
        int rowCount = 0;
        tableModel = new DefaultTableModel(columnTitles, rowCount)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
    }

    public void loadData() throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        ArrayList<Dog> dogsArrayList = dogsRepository.getAll();
        Dog[] dogs = dogsArrayList.toArray(new Dog[dogsArrayList.size()]);
        for(Dog dog : dogs)
        {
            Object[] rowData = new Object[] {dog.getId(), dog.getName(), dog.getAge(), dog.getDescription()};
            tableModel.addRow(rowData);
        }
    }

    public Dog getDogById(int id) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        return dogsRepository.getById(id);
    }

    public DefaultTableModel getTableModel()
    {
        return tableModel;
    }
}
