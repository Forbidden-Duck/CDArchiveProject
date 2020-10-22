package views.Automation;

import structures.data.CD;
import util.ComponentsManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainDisplay {
    JFrame frame;
    ComponentsManager components;
    MainEvents events = new MainEvents();
    CD[] loadCDs;
    String loadSection;
    int loadMethod;
    String[] loadActions = MainEvents.defaultActions();

    /**
     * Automation console for a collection of CDs
     * @param cds An array of CDs
     * @param section Section the CDs belong to
     * @param method Method to auto select
     */
    public MainDisplay(CD[] cds, String section, int method) {
        this.loadCDs = cds;
        this.loadSection = section;
        this.loadMethod = method;
        this.loadActions = MainEvents.allActions();
        CreateDisplay();
    }

    /**
     * Automation console for a single CD
     * @param cd Single to load
     * @param method Method to auto select
     */
    public MainDisplay(CD cd, int method) {
        this.loadCDs = new CD[]{cd};
        this.loadSection = cd.getSection();
        this.loadMethod = method;
        CreateDisplay();
    }

    /**
     * Creates the display
     */
    private void CreateDisplay() {
        frame = new JFrame("Automation Console");
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setPreferredSize(new Dimension(800, 500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder());

        components = new ComponentsManager(frame);
        components.setWeight(0, 0);

        CreateInterface();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        events.Connect();
    }

    /**
     * Creates and loads the interface
     */
    private void CreateInterface() {
        components.getConstraints().insets = new Insets(5, 5, 5, 5);
        Footer();
        components.setWeight(0.5, 0.5);

        // Add padding to all panels
        components.getConstraints().insets = new Insets(5, 5, 5, 5);
        components.setFill(GridBagConstraints.VERTICAL);

        TopLevelPanel();

        // Update fill for table panel
        components.setFill(GridBagConstraints.BOTH);

        TablePanel();
    }

    /**
     * Loads the Footer of the interface
     * Components:
     * Exit Button
     */
    private void Footer() {
        //region Create Components

        JButton btnExit = new JButton("Exit");

        //endregion

        //region Edit Components

        btnExit.setPreferredSize(new Dimension(100, 30));

        //endregion

        //region Display Components

        components.addComponent(btnExit, 3, 2, 1, 1);

        //endregion

        //region Component Events

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainEvents.Exit(frame);
            }
        });

        //endregion
    }

    /**
     * Loads the Top Level Panel of the interface
     * Panel Components:
     * Action Label
     * Action Combobox
     * Process Button
     * Barcode Label
     * Barcode Field
     * Section Label
     * Section Field
     */
    private void TopLevelPanel() {
        //region Create Panel

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setPreferredSize(new Dimension(500, 10));

        //endregion

        //region Create Panel Components Manager

        ComponentsManager paneComponents = new ComponentsManager(pane);
        paneComponents.setWeight(0.5, 0.5);

        //endregion

        //region Create Components

        JLabel lblAction = new JLabel("Current Requested Action: ", JLabel.CENTER);
        JComboBox cboActions = new JComboBox(loadActions);
        JButton btnProcess = new JButton("Process");

        JLabel lblBarcode = new JLabel("Selected Item Barcode", JLabel.CENTER);
        JTextField txtBarcode = new JTextField(8);
        JLabel lblSection = new JLabel("Section: ", JLabel.CENTER);
        JTextField txtSection = new JTextField(2);

        //endregion

        //region Save Components

        events.SaveFieldComponent(txtBarcode);

        //endregion

        //region Edit Components

        cboActions.setSelectedIndex(loadMethod);
        txtSection.setText(loadSection);
        txtSection.setEditable(false);

        //endregion

        //region Display Components

        paneComponents.setFill(GridBagConstraints.HORIZONTAL);
        paneComponents.getConstraints().insets = new Insets(8, 8, 8, 8);
        paneComponents.addComponent(lblAction, 0, 0, 1, 1);
        paneComponents.addComponent(cboActions, 1, 0, 3, 1);
        paneComponents.addComponent(btnProcess, 4, 0, 1, 1);

        paneComponents.getConstraints().insets = new Insets(0, 8, 8, 8);
        paneComponents.addComponent(lblBarcode, 0, 1, 1, 1);
        paneComponents.addComponent(txtBarcode, 1, 1, 1, 1);
        paneComponents.addComponent(lblSection, 2, 1, 1, 1);
        paneComponents.addComponent(txtSection, 3, 1, 1, 1);

        //endregion

        //region Component Events

        btnProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.Process(
                        cboActions.getSelectedIndex(),
                        (String) cboActions.getSelectedItem(),
                        events.GetTableComponent());
            }
        });

        //endregion

        //region Display Panel

        components.addComponent(pane, 2, 0, 1, 1);

        //endregion
    }

    /**
     * Loads the Table Panel of the interface
     * Panel Components:
     * Title Label
     * CD Table
     */
    private void TablePanel() {
        //region Create Panel

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        pane.setPreferredSize(new Dimension(600, 200));

        //endregion

        //region Create Panel Components Manager

        ComponentsManager paneComponents = new ComponentsManager(pane);
        paneComponents.setWeight(0.5, 0.5);
        paneComponents.setFill(GridBagConstraints.HORIZONTAL);

        //endregion

        //region Create Components

        JLabel lblTitle = new JLabel("Archive CDs", JLabel.CENTER);
        JTable tblCDs = events.LoadIntoTable(loadCDs);
        JScrollPane scrlTable = new JScrollPane(tblCDs);

        //endregion

        //region Save Components

        events.SaveTableComponent(tblCDs);

        //endregion

        //region Edit Components

        scrlTable.setPreferredSize(new Dimension(50, 200));
        tblCDs.setFillsViewportHeight(true);
        tblCDs.getTableHeader().setReorderingAllowed(false);
        tblCDs.setDefaultEditor(Object.class, null);
        tblCDs.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (tblCDs.getColumnCount() == 7) {
            tblCDs.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblCDs.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblCDs.getColumnModel().getColumn(3).setPreferredWidth(25);
            tblCDs.getColumnModel().getColumn(4).setPreferredWidth(25);
            tblCDs.getColumnModel().getColumn(6).setPreferredWidth(90);
        }

        //endregion

        //region Display Components


        paneComponents.setConstraints(2, 0);
        paneComponents.addComponent(lblTitle);

        paneComponents.setFill(GridBagConstraints.BOTH);
        paneComponents.setConstraints(0, 1, 6, 1);
        paneComponents.addComponent(scrlTable);

        //endregion

        //region Component Events

        tblCDs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tblCDs.getSelectedRow();
                if (row >= 0) {
                    events.RowSelectionUpdate(row, tblCDs);
                } else {
                    events.ClearBarcode();
                }
            }
        });

        InputMap tblInput = tblCDs.getInputMap(JTable.WHEN_FOCUSED);
        ActionMap tblAction = tblCDs.getActionMap();
        tblInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        tblAction.put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblCDs.getSelectedRow();
                if (row >= 0) {
                    tblCDs.removeRowSelectionInterval(row, row);
                }
            }
        });

        //endregion

        //region Display Panel

        components.setConstraints(0, 1, 4, 1);
        components.addComponent(pane);

        //endregion
    }
}
