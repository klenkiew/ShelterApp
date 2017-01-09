package gui.tabPanels;

import gui.MainModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class TabView {
    protected TabController controller;

    final boolean preloadDatabase = false;

    protected JPanel mainPanel;
    protected JTextField filterText;
    protected JTable table;
    protected TableRowSorter<TableModel> sorter;
    protected JPanel leftMenu;


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
                        filterTableCells();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        filterTableCells();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        filterTableCells();
                    }
                }
        );
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, filterText.getPreferredSize().height));
        filterPanel.add(new JLabel("Filter: "), BorderLayout.WEST);
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
    }

    protected void filterTableCells() {
        // TODO: gui option for database preload?
        if (preloadDatabase)
        {
            RowFilter<TableModel, Object> rf = null;
            try
            {
                rf = RowFilter.regexFilter('^' + filterText.getText(), IntStream.rangeClosed(0, table.getColumnCount() - 1).toArray());
            } catch (PatternSyntaxException e)
            {
                return;
            }
            sorter.setRowFilter(rf);
        }
        else
            controller.filterData(filterText.getText());
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
}
