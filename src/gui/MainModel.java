package gui;

import core.repositories.ModelRepository;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class MainModel<ModelType> {
    private DefaultTableModel tableModel;
    private ModelRepository<ModelType> repository;

    protected String[] getColumnTitles()
    {
        return null;
    }

    protected Object[] getRowData(ModelType obj)
    {
        return null;
    }

    public MainModel(ModelRepository<ModelType> repository)
    {
        this.repository = repository;
        String[] columnTitles = getColumnTitles();
        int rowCount = 0;
        tableModel = new DefaultTableModel(columnTitles, rowCount)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }

            @Override
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount()) && getRowCount() > 0) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };

    }

    public void loadData() throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        tableModel.setRowCount(0); ///< Clears all old rows

        ArrayList<ModelType> objectArrayList = repository.getAll();
        for(ModelType obj : objectArrayList)
            tableModel.addRow(getRowData(obj));
        tableModel.fireTableDataChanged();
    }

    public void loadFilteredData(String filterText) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        tableModel.setRowCount(0); ///< Clears all old rows

        ArrayList<ModelType> objectArrayList = repository.getByText(filterText);
        for(ModelType obj : objectArrayList)
            tableModel.addRow(getRowData(obj));
        tableModel.fireTableDataChanged();
    }


    public void loadFilteredData(String text, String selectedColumn) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        tableModel.setRowCount(0); ///< Clears all old rows

        ArrayList<ModelType> objectArrayList = repository.getByColumnText(text, selectedColumn);
        for(ModelType obj : objectArrayList)
            tableModel.addRow(getRowData(obj));
        tableModel.fireTableDataChanged();
    }

    public ModelType getObjById(int id) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        return repository.getById(id);
    }

    public DefaultTableModel getTableModel()
    {
        return tableModel;
    }

    public void loadFilteredData(String text, String selectedItem, String dogComboBoxSelectedItem) throws NoSuchFieldException, IllegalAccessException, SQLException
    {
        tableModel.setRowCount(0); ///< Clears all old rows

        HashMap<String, String> likeColumns = new HashMap<>();
        likeColumns.put(selectedItem, text);

        HashMap<String, String> equalColumns = new HashMap<>();
        equalColumns.put("Dog id", dogComboBoxSelectedItem);

        ArrayList<ModelType> objectArrayList = repository.getByComplexColumnText(likeColumns, equalColumns);
        for(ModelType obj : objectArrayList)
            tableModel.addRow(getRowData(obj));
        tableModel.fireTableDataChanged();
    }
}
