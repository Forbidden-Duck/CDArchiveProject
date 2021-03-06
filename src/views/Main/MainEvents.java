package views.Main;

import structures.data.CD;
import structures.lists.DoublyLinkedList;
import structures.sockets.Client;
import structures.sockets.interfaces.Listener;
import structures.sockets.interfaces.Sender;
import structures.sorting.Bubble;
import structures.sorting.Insertion;
import structures.sorting.Quick;
import structures.trees.BinaryTree;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class MainEvents {
    Client client;
    boolean processActive = false;

    /**
     * Creates a new Event Manager for Main Display
     */
    public MainEvents() {
    }

    //region Button Events

    //region Search Panel

    // Filter without Custom Row Sorter with Filter active

    /**
     * Search for a value
     * @param fileManager File Manager to look for a value
     * @param term Term to search for
     */
    void Search(FileManager fileManager, String term) {
        DoublyLinkedList cds = fileManager.getCDs();
        DoublyLinkedList.Node focused = cds.get(0);

        while (focused != null) {
            CD cd = (CD) focused.getData();
            if (!cd.getTitle().contains(term)
                    && !cd.getAuthor().contains(term)) {
                cds.delete(focused);
            }
            focused = focused.getNext();
        }

        this.ClearTable(tblCDs);
        DefaultTableModel dm = (DefaultTableModel) tblCDs.getModel();
        for (int i = 0; i < cds.size(); i++) {
            dm.addRow(new Object[]{
                    ((CD) cds.get(i).getData()).getTitle(),
                    ((CD) cds.get(i).getData()).getAuthor(),
                    ((CD) cds.get(i).getData()).getSection(),
                    Integer.toString(((CD) cds.get(i).getData()).getXAxis()),
                    Integer.toString(((CD) cds.get(i).getData()).getYAxis()),
                    Integer.toString(((CD) cds.get(i).getData()).getBarcode()),
                    ((CD) cds.get(i).getData()).getDescription(),
                    Boolean.toString(((CD) cds.get(i).getData()).getOnLoan())
            });
        }
    }

    //endregion

    //region Archive CDs

    private JTable tblCDs = new JTable(0, 8);

    // Sort without using Custom Row Sorter

    /**
     * Sort the table by Title
     */
    void ByTitle() {
        cdTree = new BinaryTree();
        List cds = new ArrayList<CD>();
        int rowCount = tblCDs.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            CD cd = this.ConvertRowToCD(row, tblCDs);
            cds.add(cd);
            cdTree.insert(new BinaryTree.Node(cd.getBarcode(), cd));
        }
        Bubble.sortCDsByTitle(cds);

        this.ClearTable(tblCDs);
        DefaultTableModel dm = (DefaultTableModel) tblCDs.getModel();
        for (int i = 0; i < cds.size(); i++) {
            dm.addRow(new Object[]{
                    ((CD) cds.get(i)).getTitle(),
                    ((CD) cds.get(i)).getAuthor(),
                    ((CD) cds.get(i)).getSection(),
                    Integer.toString(((CD) cds.get(i)).getXAxis()),
                    Integer.toString(((CD) cds.get(i)).getYAxis()),
                    Integer.toString(((CD) cds.get(i)).getBarcode()),
                    ((CD) cds.get(i)).getDescription(),
                    Boolean.toString(((CD) cds.get(i)).getOnLoan())
            });
        }
    }

    /**
     * Sort the table by Author
     */
    void ByAuthor() {
        List cds = new ArrayList<CD>();
        int rowCount = tblCDs.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            CD cd = this.ConvertRowToCD(row, tblCDs);
            cds.add(cd);
        }
        Insertion.sortCDsByAuthor(cds);
        this.ClearTable(tblCDs);
        DefaultTableModel dm = (DefaultTableModel) tblCDs.getModel();

        for (int i = 0; i < cds.size(); i++) {
            dm.addRow(new Object[]{
                    ((CD) cds.get(i)).getTitle(),
                    ((CD) cds.get(i)).getAuthor(),
                    ((CD) cds.get(i)).getSection(),
                    Integer.toString(((CD) cds.get(i)).getXAxis()),
                    Integer.toString(((CD) cds.get(i)).getYAxis()),
                    Integer.toString(((CD) cds.get(i)).getBarcode()),
                    ((CD) cds.get(i)).getDescription(),
                    Boolean.toString(((CD) cds.get(i)).getOnLoan())
            });
        }
    }

    /**
     * Sort the table by Barcode
     */
    void ByBarcode() {
        List cds = new ArrayList<CD>();
        int rowCount = tblCDs.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            CD cd = this.ConvertRowToCD(row, tblCDs);
            cds.add(cd);
        }
        Quick.sortCDsByBarcode(cds);
        this.ClearTable(tblCDs);
        DefaultTableModel dm = (DefaultTableModel) tblCDs.getModel();

        for (int i = 0; i < cds.size(); i++) {
            dm.addRow(new Object[]{
                    ((CD) cds.get(i)).getTitle(),
                    ((CD) cds.get(i)).getAuthor(),
                    ((CD) cds.get(i)).getSection(),
                    Integer.toString(((CD) cds.get(i)).getXAxis()),
                    Integer.toString(((CD) cds.get(i)).getYAxis()),
                    Integer.toString(((CD) cds.get(i)).getBarcode()),
                    ((CD) cds.get(i)).getDescription(),
                    Boolean.toString(((CD) cds.get(i)).getOnLoan())
            });
        }
    }

    /**
     * Save the table component
     * @param table
     */
    void SaveTableComponent(JTable table) {
        this.tblCDs = table;
    }

    /**
     * Return the table component
     * @return
     */
    JTable GetTableComponent() {
        return this.tblCDs;
    }

    /**
     * Clear the table provided
     * @param table
     */
    void ClearTable(JTable table) {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();

        // Remove Rows
        int rowCount = table.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }

    //endregion

    //region Process Log

    private BinaryTree<CD> cdTree = new BinaryTree();
    private HashMap<String, String> cdMap = new HashMap();
    private JList processHistory = new JList();
    private ArrayList<String> processHistoryData = new ArrayList();

    /**
     * Display the process log
     */
    void ProcessLog() {
        processHistory.setListData(processHistoryData.toArray());
        processActive = true;
    }

    /**
     * Display the preorder binary tree
     */
    void PreOrder() {
        if (cdTree.preOrder().size() > 0) {
            ArrayList<String> processData = new ArrayList<>();
            processData = ConvertNodeToStringList(cdTree.preOrder());
            processHistory.setListData(processData.toArray());
            processActive = false;
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No Binary Tree Data. Have you clicked \"By Title\"?",
                    "Pre-Order",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Display the inorder binary tree
     */
    void InOrder() {
        if (cdTree.inOrder().size() > 0) {
            ArrayList<String> processData = new ArrayList<>();
            processData = ConvertNodeToStringList(cdTree.inOrder());
            processHistory.setListData(processData.toArray());
            processActive = false;
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No Binary Tree Data. Have you clicked \"By Title\"?",
                    "In-Order",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Display the postorder binary tree
     */
    void PostOrder() {
        if (cdTree.postOrder().size() > 0) {
            ArrayList<String> processData = new ArrayList<>();
            processData = ConvertNodeToStringList(cdTree.postOrder());
            processHistory.setListData(processData.toArray());
            processActive = false;
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No Binary Tree Data. Have you clicked \"By Title\"?",
                    "Post-Order",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Display a graphical representation of the binary tree
     */
    void Graphical() {
        if (cdTree.preOrder().size() > 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new views.BinaryTree.MainDisplay(cdTree);
                }
            });
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No Binary Tree Data. Have you clicked \"By Title\"?",
                    "Graphical",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Save the binary tree in a HashMap
     */
    void HashSave() {
        if (cdMap.size() > 0) {
            cdMap.clear();
        }

        ArrayList<BinaryTree.Node> preOrder = cdTree.preOrder();
        if (preOrder.size() < 1) {
            JOptionPane.showMessageDialog(
                    null,
                    "No Binary Tree Data. Have you clicked \"By Title\"?",
                    "Hash-Map",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (int i = 0; i < preOrder.size(); i++) {
            BinaryTree.Node node = preOrder.get(i);
            cdMap.put(node.getKey() + "", MainEvents.CDToString((CD) node.getData()));
        }
    }

    /**
     * Display the HashMap
     */
    void HashDisplay() {
        if (cdMap.size() > 0) {
            ArrayList<String> processData = new ArrayList<>();
            cdMap.forEach((key, value) -> {
                CD cd = MainEvents.StringToCD(value);
                processData.add(cd.getBarcode() + " - " + cd.getTitle());
            });
            processHistory.setListData(processData.toArray());
            processActive = false;
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No Hash Map Data. Have you clicked \"Save\"?",
                    "Hash-Map",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Convert a Node List too a String List
     * @param nodeList
     * @return
     */
    ArrayList<String> ConvertNodeToStringList(List<BinaryTree.Node> nodeList) {
        ArrayList<String> strList = new ArrayList();
        for (int i = 0; i < nodeList.size(); i++) {
            CD cd = (CD) nodeList.get(i).getData();
            strList.add(cd.getBarcode() + " - " + cd.getTitle());
        }
        return strList;
    }

    /**
     * Save the List component
     * @param history
     */
    void SaveListComponent(JList history) {
        this.processHistory = history;
    }

    /**
     * Parse a CD into a String
     * @param cd
     * @return
     */
    static String CDToString(CD cd) {
        return (
                cd.getTitle() + ";" +
                        cd.getAuthor() + ";" +
                        cd.getSection() + ";" +
                        cd.getXAxis() + ";" +
                        cd.getYAxis() + ";" +
                        cd.getBarcode() + ";" +
                        cd.getDescription() + ";" +
                        cd.getOnLoan() + ";"
        );
    }

    /**
     * Parse a CD into a String
     * @param cd
     * @return
     */
    static CD StringToCD(String cd) {
        String[] lineSplit = cd.split(";");
        return new CD(
                lineSplit[0], lineSplit[1],
                lineSplit[6], lineSplit[2],
                Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]),
                Integer.parseInt(lineSplit[5]), Boolean.parseBoolean(lineSplit[7])
        );
    }

    //endregion

    //region CD Details

    private Object[] CDDetailsComponents = new Object[0];

    /**
     * Create a new CD item
     * @param barcodes Barcodes not to use
     */
    void NewItem(int[] barcodes) {
        tblCDs.clearSelection();

        // Get a new unique barcode
        int barcode = CD.RandomBarcode();
        while (CD.ContainsBarcode(barcodes, barcode)) {
            barcode = CD.RandomBarcode();
        }
        // Display CD
        CD cd = new CD(barcode);
        DisplayCD(cd);
    }

    /**
     * Save a the CD item
     */
    void SaveItem() {
        JTextField title = (JTextField) CDDetailsComponents[0];
        JTextField author = (JTextField) CDDetailsComponents[1];
        JTextField section = (JTextField) CDDetailsComponents[2];
        JTextField xAxis = (JTextField) CDDetailsComponents[3];
        JTextField yAxis = (JTextField) CDDetailsComponents[4];
        JTextField barcode = (JTextField) CDDetailsComponents[5];
        JTextField description = (JTextField) CDDetailsComponents[6];
        JRadioButton onloan = (JRadioButton) CDDetailsComponents[7];

        Object[] newData = new Object[]{
                title.getText(),
                author.getText(),
                section.getText(),
                xAxis.getText(),
                yAxis.getText(),
                barcode.getText(),
                description.getText(),
                Boolean.toString(onloan.isSelected())
        };
        DefaultTableModel dm = (DefaultTableModel) tblCDs.getModel();
        if (tblCDs.getSelectedRow() < 0) {
            dm.addRow(newData);
        } else {
            int selectedRow = tblCDs.getSelectedRow();
            dm.insertRow(selectedRow, newData);
            dm.removeRow(selectedRow + 1);
            tblCDs.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }

    /**
     * Display a CD
     * @param cd
     */
    void DisplayCD(CD cd) {
        JTextField title = (JTextField) CDDetailsComponents[0];
        JTextField author = (JTextField) CDDetailsComponents[1];
        JTextField section = (JTextField) CDDetailsComponents[2];
        JTextField xAxis = (JTextField) CDDetailsComponents[3];
        JTextField yAxis = (JTextField) CDDetailsComponents[4];
        JTextField barcode = (JTextField) CDDetailsComponents[5];
        JTextField description = (JTextField) CDDetailsComponents[6];
        JRadioButton onloan = (JRadioButton) CDDetailsComponents[7];

        title.setText(cd.getTitle());
        author.setText(cd.getAuthor());
        section.setText(cd.getSection());
        xAxis.setText(Integer.toString(cd.getXAxis()));
        yAxis.setText(Integer.toString(cd.getYAxis()));
        barcode.setText(Integer.toString(cd.getBarcode()));
        description.setText(cd.getDescription());
        onloan.setSelected(cd.getOnLoan());

        title.setCaretPosition(0);
        author.setCaretPosition(0);
        section.setCaretPosition(0);
        xAxis.setCaretPosition(0);
        yAxis.setCaretPosition(0);
        barcode.setCaretPosition(0);
        description.setCaretPosition(0);
    }

    /**
     * Clear the CD fields
     */
    void ClearCD() {
        JTextField title = (JTextField) CDDetailsComponents[0];
        JTextField author = (JTextField) CDDetailsComponents[1];
        JTextField section = (JTextField) CDDetailsComponents[2];
        JTextField xAxis = (JTextField) CDDetailsComponents[3];
        JTextField yAxis = (JTextField) CDDetailsComponents[4];
        JTextField barcode = (JTextField) CDDetailsComponents[5];
        JTextField description = (JTextField) CDDetailsComponents[6];
        JRadioButton onloan = (JRadioButton) CDDetailsComponents[7];

        title.setText("");
        author.setText("");
        section.setText("");
        xAxis.setText("");
        yAxis.setText("");
        barcode.setText("");
        description.setText("");
        onloan.setSelected(false);
    }

    /**
     * Save the CD Fields
     * @param title
     * @param author
     * @param section
     * @param xAxis
     * @param yAxis
     * @param barcode
     * @param description
     * @param onloan
     */
    void SaveCDComponents(
            JTextField title, JTextField author,
            JTextField section, JTextField xAxis,
            JTextField yAxis, JTextField barcode,
            JTextField description, JRadioButton onloan
    ) {
        CDDetailsComponents = new Object[]{
                title, author,
                section, xAxis,
                yAxis, barcode,
                description, onloan
        };
    }

    //endregion

    //region Automation

    /**
     * Retrieve Single CD
     * @param row
     */
    void Retrieve(int row) {
        this.SingleCDAutomation(MainEvents.ConvertRowToCD(row, tblCDs), 1);
    }

    /**
     * Remove Single CD
     * @param row
     */
    void Remove(int row) {
        this.SingleCDAutomation(MainEvents.ConvertRowToCD(row, tblCDs), 3);
    }

    /**
     * Return Single CD
     * @param row
     */
    void Return(int row) {
        this.SingleCDAutomation(MainEvents.ConvertRowToCD(row, tblCDs), 2);
    }

    /**
     * Add Single CD
     * @param row
     */
    void Add(int row) {
        this.SingleCDAutomation(MainEvents.ConvertRowToCD(row, tblCDs), 0);
    }

    /**
     * Random Sort Collection of CDs
     * @param section Section the CDs exist in
     */
    void RandomSort(String section) {
        CD[] cds = this.SectionToCDs(section, tblCDs);
        this.MultipleCDsAutomation(cds, section, 4);
    }

    /**
     * Mostly Sort Collection of CDs
     * @param section Section the CDs exist in
     */
    void MostlySort(String section) {
        CD[] cds = this.SectionToCDs(section, tblCDs);
        this.MultipleCDsAutomation(cds, section, 5);
    }

    /**
     * Reverse Sort Collection of CDs
     * @param section Section the CDs exist in
     */
    void ReverseSort(String section) {
        CD[] cds = this.SectionToCDs(section, tblCDs);
        this.MultipleCDsAutomation(cds, section, 6);
    }

    /**
     * Process a Single CD
     * @param cd CD to process
     * @param method Method to auto select
     */
    void SingleCDAutomation(CD cd, int method) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new views.Automation.MainDisplay(cd, method);
            }
        });
    }

    /**
     * Process a Collection of CDs
     * @param cds CDs to process
     * @param section Section the CDs exist in
     * @param method Method to auto select
     */
    void MultipleCDsAutomation(CD[] cds, String section, int method) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new views.Automation.MainDisplay(cds, section, method);
            }
        });
    }

    //endregion

    /**
     * Exits the program
     */
    static void Exit() {
        System.exit(0);
    }

    //endregion

    //region Table Events

    /**
     * Displays the selected row
     * @param row
     * @param table
     */
    void RowSelectionUpdate(int row, JTable table) {
        CD cd = this.ConvertRowToCD(row, table);
        this.DisplayCD(cd);
    }

    /**
     * Converts a Section into a collection of CDs
     * @param section
     * @param table
     * @return
     */
    CD[] SectionToCDs(String section, JTable table) {
        int rowCount = table.getRowCount();
        List<CD> cdsList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            CD cd = MainEvents.ConvertRowToCD(i, table);
            if (cd.getSection().equals(section)) {
                cdsList.add(cd);
            }
        }

        return Arrays.copyOf(cdsList.toArray(), cdsList.size(), CD[].class);
    }

    //endregion

    //region Socket Events

    /**
     * Connect the client to the server
     */
    void Connect() {
        client = new Client("", new Listener() {
            @Override
            public void ready() {

            }

            @Override
            public void message(String msg, Sender sender) {
                processHistoryData.add(msg);
                if (processActive == true) {
                    ProcessLog();
                }
            }
        });
    }

    //endregion

    /**
     * Convert a Row into a CD
     *
     * @param row
     * @param table
     * @return
     */
    static CD ConvertRowToCD(int row, JTable table) {
        String title = (String) table.getValueAt(row, 0);
        String author = (String) table.getValueAt(row, 1);
        String section = (String) table.getValueAt(row, 2);
        int xAxis = Integer.parseInt((String) table.getValueAt(row, 3));
        int yAxis = Integer.parseInt((String) table.getValueAt(row, 4));
        int barcode = Integer.parseInt((String) table.getValueAt(row, 5));
        String description = (String) table.getValueAt(row, 6);
        boolean onLoan = Boolean.parseBoolean((String) table.getValueAt(row, 7));

        return new CD(
                title, author,
                description, section,
                xAxis, yAxis,
                barcode, onLoan
        );
    }
}
