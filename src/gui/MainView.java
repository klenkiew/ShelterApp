package gui;

import entities.Dog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

/**
 * Created by Kamil on 27.12.2016.
 */
public class MainView
{
    private final String title = "Shelter";
    private MainController mainController;

    private JFrame mainFrame;
    private JTextField filterText;
    private JTable table;
    private TableRowSorter<TableModel> sorter;
    private JPanel leftMenu;
    private JButton addVaccinationButton;
    private JButton addDogButton;

    public MainView(MainController controller, MainModel mainModel)
    {
        this.mainController = controller;
        initializeComponents(mainModel);
        setUpActionListeners();
    }

    private void initializeComponents(MainModel mainModel)
    {
        // Main window setup
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle(title);
        mainFrame.setMinimumSize(new Dimension(500, 300));
        mainFrame.setSize(new Dimension(600, 600));
        mainFrame.setLocationRelativeTo(null); ///< Used to center window
        mainFrame.setLayout(new BorderLayout());

        // Main tab container
        JPanel mainPanel = new JPanel();
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
                    filterCells();
                }
                public void insertUpdate(DocumentEvent e) {
                    filterCells();
                }
                public void removeUpdate(DocumentEvent e) {
                    filterCells();
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

        // SUB(Left) AddVaccination button
        addVaccinationButton = new JButton("Add vaccination");
        addVaccinationButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addVaccinationButton.getPreferredSize().height));
        leftMenu.add(addVaccinationButton);

        // SUB(Left) AddDog button
        addDogButton = new JButton("Add dog");
        addVaccinationButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addVaccinationButton.getPreferredSize().height));
        leftMenu.add(addVaccinationButton);
        addDogButton.setEnabled(false); ///< TODO: rm after implementation

        // Add parent panels to main tab container
        mainPanel.add(leftMenu, BorderLayout.WEST);
        mainPanel.add(dataPanel, BorderLayout.CENTER);

        // Add current tab container to main tabbed panel
        JTabbedPane tb = new JTabbedPane();
        tb.addTab("Dogs", mainPanel);
        mainFrame.add(tb);
    }

    private void setUpActionListeners()
    {
        table.addMouseListener(mainController.new onMouseDoubleClick());
        addVaccinationButton.addActionListener(e -> mainController.onAddVaccinationClicked());

    }

    private void filterCells() {
        RowFilter<TableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(filterText.getText(), IntStream.rangeClosed(1, table.getColumnCount()).toArray());
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    public void setVisible(boolean value)
    {
        mainFrame.setVisible(value);
    }

    public void displayDialogFor(Dog dog)
    {
        String traits = "";
        if (dog.isAggressive())
            traits += " aggresive";
        if (dog.isOpen()) {
            if (!traits.isEmpty())
                traits += ",";
            traits += " open";
        }
        if (dog.isVulnerable()) {
            if (!traits.isEmpty())
                traits += ",";
            traits += " vulnerable";
        }

        String msg = "Name: " + dog.getName()
                + "\nAge: " + dog.getAge()
                + "\nHair color: " + dog.getHairColor()
                + "\nDescription: " + dog.getDescription();
        if (!traits.isEmpty())
            msg += "\nTraits: " + traits;
        JOptionPane.showMessageDialog(null, msg, dog.getName() + "'s details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int getSelectedRow()
    {
        return table.getSelectedRow();
    }

    public Object getCellValue(int row, int column)
    {
        return table.getValueAt(row, column);
    }

    public int[] getSelectedRows()
    {
        return table.getSelectedRows();
    }

}
