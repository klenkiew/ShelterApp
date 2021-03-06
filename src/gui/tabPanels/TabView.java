package gui.tabPanels;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import gui.MainController;
import gui.MainModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class TabView {
    protected TabController controller;

    protected JPanel mainPanel;
    protected JTextField filterText;
    protected JTable table;
    protected TableRowSorter<TableModel> sorter;
    protected JPanel leftMenu;
    protected JComboBox tableColumns;
    protected JButton searchButton;

    public TabView(TabController controller, MainModel mainModel)
    {
        this.controller = controller;
        initializeComponents(mainModel);
        setUpActionListeners();
    }

    private void initializeComponents(MainModel mainModel)
    {
        // Main tab container
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Data panel holding data table & related stuff
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        // SUB(Data) Table filtering panel
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterText = new JTextField();
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        if (MainController.getControllerInstance().isPreloadDatabase())
                            filterTableCells();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        if (MainController.getControllerInstance().isPreloadDatabase())
                            filterTableCells();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        if (MainController.getControllerInstance().isPreloadDatabase())
                            filterTableCells();
                    }
                }
        );
//        filterText.setMaximumSize(new Dimension(Integer.MAX_VALUE, filterText.getPreferredSize().height));
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, filterText.getPreferredSize().height));
        filterPanel.add(new JLabel("Filter: "), BorderLayout.WEST);

        // combo box for column-based filtering
        tableColumns = new JComboBox<>(new String[] {"Any"});
        tableColumns.addItemListener( e ->
        {
            if (MainController.getControllerInstance().isPreloadDatabase())
                filterTableCells();
        });
        DefaultTableModel tableModel = controller.getModel().getTableModel();
        for (int i = 0; i < tableModel.getColumnCount(); ++i)
            tableColumns.addItem(tableModel.getColumnName(i));

        // button for executing query to database
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> filterTableCells());

        JPanel buttonComboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonComboBoxPanel.add(tableColumns);
        buttonComboBoxPanel.add(searchButton);

//        filterPanel.add(tableColumns, BorderLayout.EAST);
        filterPanel.add(buttonComboBoxPanel, BorderLayout.EAST);

        filterPanel.add(filterText);
        dataPanel.add(filterPanel, BorderLayout.NORTH);

        // SUB(Data) Table panel
        table = new JTable(mainModel.getTableModel());
        sorter = new TableRowSorter<>(mainModel.getTableModel());
        table.setRowSorter(sorter);
        table.getTableHeader().setReorderingAllowed(false);
        dataPanel.add(new JScrollPane(table));

        // Left button panel holding buttons and functionality related to visible data
        leftMenu = new JPanel();
        leftMenu.setLayout(new BoxLayout(leftMenu, BoxLayout.Y_AXIS));

        // SUB(Left) Buttons
        for (Component button : getAllButtons())
        {
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
            leftMenu.add(button);
        }

        // Add parent panels to main tab container
        mainPanel.add(leftMenu, BorderLayout.WEST);
        mainPanel.add(dataPanel, BorderLayout.CENTER);
    }

    protected ArrayList<Component> getAllButtons()
    {
        return null;
    }

    protected void setUpActionListeners()
    {
//        searchButton.addActionListener(e -> filterTableCells());
//        tableColumns.addItemListener( e ->
//        {
//            if (MainController.getControllerInstance().isPreloadDatabase())
//                filterTableCells();
//        });
    }

    protected void filterTableCells() {
        String selectedItem = (String) tableColumns.getSelectedItem();
        if (MainController.getControllerInstance().isPreloadDatabase())
        {
            RowFilter<TableModel, Object> rf = null;
            try
            {
                if (Objects.equals(selectedItem, "Any"))
                    rf = RowFilter.regexFilter('^' + filterText.getText(), IntStream.rangeClosed(0, table.getColumnCount() - 1).toArray());
                else
                    rf = RowFilter.regexFilter('^' + filterText.getText(), table.getColumn(selectedItem).getModelIndex());
            } catch (PatternSyntaxException e)
            {
                return;
            }
            sorter.setRowFilter(rf);
        }
        else
        {
            if (Objects.equals(selectedItem, "Any"))
                controller.filterData(filterText.getText());
            else
                controller.filterData(filterText.getText(), selectedItem);
        }
    }

    public void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public Object getCellValue(int row, int column)
    {
        return table.getValueAt(row, column);
    }

    public int[] getSelectedRows()
    {
        return table.getSelectedRows();
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    public void setSearchButtonEnabled(boolean b)
    {
        searchButton.setEnabled(b);
    }
}
