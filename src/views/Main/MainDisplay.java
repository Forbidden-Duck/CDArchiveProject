package views.Main;

// Local Classes

import structures.data.CD;
import structures.lists.DoublyLinkedList;
import util.ComponentsManager;
import util.FileManager;

// External Classes

// Component Classes
import javax.swing.*;
import java.awt.*;
// Table Classes
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
// Event Classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainDisplay {
    JFrame frame;
    MainEvents events = new MainEvents();
    FileManager fileManager = new FileManager("CDArchive.csv");
    ComponentsManager components;
    JList processHistory;

    public MainDisplay() {
        frame = new JFrame("Archive Console");
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder());

        components = new ComponentsManager(frame);
        components.setWeight(0.5, 0.5);
        components.setAnchor(GridBagConstraints.FIRST_LINE_START);
        components.getConstraints().insets = new Insets(5, 5, 0, 0);

        CreateInterface();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void CreateInterface() {
        // Add padding to left outside panels
        components.getConstraints().insets = new Insets(0, 5, 5, 5);
        components.setFill(GridBagConstraints.VERTICAL);

        Footer();
        SearchString();
        TableButtons();

        // Add padding to left inside panels
        components.setFill(GridBagConstraints.BOTH);

        ArchiveCDs();
        ProcessLog();

        // Add/reset padding to right side panels
        components.getConstraints().insets = new Insets(0, 0, 5, 5);

        ManageCD();
        Automation();
    }

    //region Frame Components

    private void Footer() {
        //region Create Panel

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setPreferredSize(new Dimension(200, 25));

        //endregion

        //region Create Panel Components Manager

        ComponentsManager paneComponents = new ComponentsManager(pane);
        paneComponents.setWeight(0.5, 0.5);

        //endregion

        //region Create Components

        JButton btnExit = new JButton("Exit");

        //endregion

        //region Display Components

        components.setFill(GridBagConstraints.HORIZONTAL);
        components.setConstraints(6, 4, 3, 1);
        components.addComponent(btnExit);
        components.setFill(GridBagConstraints.VERTICAL);

        //endregion

        //region Component Events

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainEvents.Exit();
            }
        });

        //endregion

        //region Display Panel

        components.setConstraints(0, 4);
        components.addComponent(pane);

        //endregion
    }

    private void SearchString() {
        //region Create Panel

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setPreferredSize(new Dimension(300, 25));

        //endregion

        //region Create Panel Components Manager

        ComponentsManager paneComponents = new ComponentsManager(pane);
        paneComponents.setWeight(0.5, 0.5);

        //endregion

        //region Create Components

        JLabel lblSearch = new JLabel("Search: ");
        JTextField txtSearch = new JTextField(10);
        JButton btnSearch = new JButton("Search");

        //endregion

        //region Display Components

        paneComponents.setConstraints(0, 0);
        paneComponents.addComponent(lblSearch);

        paneComponents.setConstraints(1, 0);
        paneComponents.addComponent(txtSearch);

        paneComponents.setConstraints(2, 0);
        paneComponents.addComponent(btnSearch);

        //endregion

        //region Component Events

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.Search(fileManager, txtSearch.getText());
            }
        });

        //endregion

        //region Display Panel

        components.setConstraints(0, 0);
        components.addComponent(pane);

        //endregion
    }

    private void TableButtons() {
        //region Create Panel

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setPreferredSize(new Dimension(300, 25));

        //endregion

        //region Create Panel Components Manager

        ComponentsManager paneComponents = new ComponentsManager(pane);
        paneComponents.setWeight(0.5, 0.5);

        //endregion

        //region Create Components

        JButton btnLoad = new JButton("Load File");
        JButton btnSave = new JButton("Save File");

        //endregion

        //region Display Components

        paneComponents.setConstraints(0, 0);
        paneComponents.addComponent(btnLoad);

        paneComponents.setConstraints(1, 0);
        paneComponents.addComponent(btnSave);

        //endregion

        //region Component Events

        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileManager.loadIntoJTable(events.GetTableComponent());
                events.ClearCD();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileManager.tableToFile(events.GetTableComponent());
                events.ClearCD();
            }
        });

        //endregion

        //region Display Panel

        components.setConstraints(6, 0);
        components.addComponent(pane);

        //endregion
    }

    private void ArchiveCDs() {
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
        JTable tblCDs = fileManager.loadIntoJTable();
        JScrollPane scrlTable = new JScrollPane(tblCDs);
        JLabel lblSort = new JLabel("Sort: ");
        JButton btnByTitle = new JButton("By Title");
        JButton btnByAuthor = new JButton("By Author");
        JButton btnByBarcode = new JButton("By Barcode");

        //endregion

        //region Save Components

        events.SaveTableComponent(tblCDs);

        //endregion

        //region Edit Components

        scrlTable.setPreferredSize(new Dimension(50, 150));
        tblCDs.setFillsViewportHeight(true);
        tblCDs.getTableHeader().setReorderingAllowed(false);
        tblCDs.setDefaultEditor(Object.class, null);
        tblCDs.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (tblCDs.getColumnCount() == 8) {
            tblCDs.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblCDs.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblCDs.getColumnModel().getColumn(3).setPreferredWidth(25);
            tblCDs.getColumnModel().getColumn(4).setPreferredWidth(25);
            tblCDs.getColumnModel().getColumn(6).setPreferredWidth(90);
            tblCDs.getColumnModel().getColumn(7).setPreferredWidth(55);
        }

        //endregion

        //region Display Components

        //region Title

        paneComponents.setConstraints(2, 0);
        paneComponents.addComponent(lblTitle);

        //endregion

        //region ScrollPane

        // ScrollPane goes over 6 Grids and fills it all up
        paneComponents.setFill(GridBagConstraints.BOTH);
        paneComponents.getConstraints().gridwidth = 6;
        paneComponents.setConstraints(0, 1);
        paneComponents.addComponent(scrlTable);

        //endregion

        //region Reset Scrollpane Constraints

        // Reset so that the Sort area goes over the correct distance
        paneComponents.setFill(GridBagConstraints.HORIZONTAL);
        paneComponents.getConstraints().gridwidth = 1;
        // Moves the buttons closer
        paneComponents.getConstraints().insets = new Insets(0, 5, 0, -100);

        //endregion

        //region Sort Grid

        paneComponents.setConstraints(0, 2);
        paneComponents.addComponent(lblSort);
        paneComponents.getConstraints().insets = new Insets(0, 5, 0, 5);

        paneComponents.setConstraints(1, 2);
        paneComponents.addComponent(btnByTitle);

        paneComponents.setConstraints(2, 2);
        paneComponents.addComponent(btnByAuthor);

        paneComponents.setConstraints(3, 2);
        paneComponents.addComponent(btnByBarcode);

        //endregion

        //endregion

        //region Component Events

        tblCDs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tblCDs.getSelectedRow();
                if (row >= 0) {
                    events.RowSelectionUpdate(row, tblCDs);
                }
            }
        });

        InputMap tblInput = tblCDs.getInputMap(JTable.WHEN_FOCUSED);
        ActionMap tblAction = tblCDs.getActionMap();
        tblInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
        tblAction.put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblCDs.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel dm = (DefaultTableModel) tblCDs.getModel();
                    dm.removeRow(row);
                }
            }
        });

        btnByTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.ByTitle();
            }
        });

        btnByAuthor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.ByAuthor();
            }
        });

        btnByBarcode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.ByBarcode();
            }
        });

        //endregion

        //region Display Panel

        components.setConstraints(0, 1, 6, 1);
        components.addComponent(pane);

        // Reset Component Constraints
        components.getConstraints().gridwidth = 1;

        //endregion
    }

    private void ProcessLog() {
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

        JLabel lblTitle = new JLabel("Process Log:", JLabel.LEFT);
        JButton btnLog = new JButton("Process Log");
        processHistory = new JList();
        JScrollPane scrlList = new JScrollPane(processHistory);

        // Binary Grid
        JLabel lblBinary = new JLabel("Display Binary Tree: ", JLabel.RIGHT);
        JButton btnPreOrder = new JButton("Pre-Order");
        JButton btnInOrder = new JButton("In-Order");
        JButton btnPostOrder = new JButton("Post-Order");
        JButton btnGraphical = new JButton("Graphical");

        // HashMap/Set Grid
        JLabel lblHash = new JLabel("HashMap: ", JLabel.RIGHT);
        JButton btnSave = new JButton("Save");
        JButton btnDisplay = new JButton("Display");

        //endregion

        //region Save Components

        events.SaveListComponent(processHistory);

        //endregion

        //region Edit Components

        scrlList.setPreferredSize(new Dimension(50, 100));

        //endregion

        //region Display Components

        //region Title

        paneComponents.setConstraints(0, 0);
        paneComponents.addComponent(lblTitle);

        //endregion

        //region Process Log Button

        paneComponents.setConstraints(6, 0);
        paneComponents.addComponent(btnLog);

        //endregion

        //region Process Log List

        // List goes over 6 Grids and fills it all up
        paneComponents.setFill(GridBagConstraints.BOTH);
        paneComponents.getConstraints().gridwidth = 7;
        paneComponents.setConstraints(0, 1);
        paneComponents.addComponent(scrlList);

        //endregion

        //region Reset List Constraints

        paneComponents.setFill(GridBagConstraints.HORIZONTAL);
        paneComponents.getConstraints().gridwidth = 1;

        //endregion

        paneComponents.getConstraints().insets = new Insets(0, 2, 0, 2);

        //region Binary Grid

        // Label
        paneComponents.setConstraints(0, 2);
        paneComponents.addComponent(lblBinary);

        // Buttons
        paneComponents.setConstraints(1, 2);
        paneComponents.addComponent(btnPreOrder);
        paneComponents.setConstraints(2, 2);
        paneComponents.addComponent(btnInOrder);
        paneComponents.setConstraints(3, 2);
        paneComponents.addComponent(btnPostOrder);
        paneComponents.setConstraints(4, 2);
        paneComponents.addComponent(btnGraphical);

        //endregion

        //region Hash Grid

        // Label
        paneComponents.setConstraints(0, 3);
        paneComponents.addComponent(lblHash);

        paneComponents.setConstraints(1, 3);
        paneComponents.addComponent(btnSave);
        paneComponents.setConstraints(2, 3);
        paneComponents.addComponent(btnDisplay);

        //endregion

        //endregion

        //region Component Events

        btnLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.ProcessLog();
            }
        });

        btnPreOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.PreOrder();
            }
        });

        btnInOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.InOrder();
            }
        });

        btnPostOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.PostOrder();
            }
        });

        btnGraphical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.Graphical();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.HashSave();
            }
        });

        btnDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.HashDisplay();
            }
        });

        //endregion

        //region Display Panel

        components.getConstraints().gridwidth = 6;
        components.setConstraints(0, 2);
        components.addComponent(pane);

        // Reset Component Constraints
        components.getConstraints().gridwidth = 1;

        //endregion
    }

    private void ManageCD() {
        //region Create Panel

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        pane.setPreferredSize(new Dimension(300, 200));

        //endregion

        //region Create Panel Components Manager

        ComponentsManager paneComponents = new ComponentsManager(pane);
        paneComponents.setWeight(0.5, 0.5);
        paneComponents.setAnchor(GridBagConstraints.FIRST_LINE_START);
        paneComponents.getConstraints().insets = new Insets(8, 8, 0, 0);

        //endregion

        //region Create Components

        JLabel lblTitle = new JLabel("Title: ", JLabel.LEFT);
        JTextField txtTitle = new JTextField(12);

        JLabel lblOnLoan = new JLabel("On Loan: ", JLabel.LEFT);
        JRadioButton rdoOnLoan = new JRadioButton();

        JLabel lblAuthor = new JLabel("Author: ", JLabel.LEFT);
        JTextField txtAuthor = new JTextField(12);

        JLabel lblSection = new JLabel("Section: ", JLabel.LEFT);
        JTextField txtSection = new JTextField(4);

        JLabel lblX = new JLabel("X: ", JLabel.LEFT);
        JTextField txtX = new JTextField(4);

        JLabel lblY = new JLabel("Y: ", JLabel.LEFT);
        JTextField txtY = new JTextField(4);

        JLabel lblBarcode = new JLabel("Barcode: ", JLabel.LEFT);
        JTextField txtBarcode = new JTextField(12);

        JLabel lblDescription = new JLabel("Description: ", JLabel.LEFT);
        JTextField txtDescription = new JTextField();
        JScrollPane scrlDescription = new JScrollPane(txtDescription);

        JButton btnNew = new JButton("New Item");
        JButton btnSave = new JButton("Save/Update");

        //endregion

        //region Save Components

        events.SaveCDComponents(
                txtTitle, txtAuthor,
                txtSection, txtX,
                txtY, txtBarcode,
                txtDescription, rdoOnLoan
        );

        //endregion

        //region Edit Components

        scrlDescription.setPreferredSize(new Dimension(220, 38));
        scrlDescription.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrlDescription.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        //endregion

        //region Display Components

        //region Labels

        paneComponents.setConstraints(0, 0);
        paneComponents.addComponent(lblTitle);
        paneComponents.getConstraints().insets = new Insets(0, 8, 0, 0);

        paneComponents.setConstraints(0, 1);
        paneComponents.addComponent(lblAuthor);

        paneComponents.setConstraints(0, 2);
        paneComponents.addComponent(lblSection);

        paneComponents.setConstraints(0, 3);
        paneComponents.addComponent(lblX);

        paneComponents.setConstraints(0, 4);
        paneComponents.addComponent(lblY);

        paneComponents.setConstraints(0, 5);
        paneComponents.addComponent(lblBarcode);

        paneComponents.setConstraints(0, 6);
        paneComponents.addComponent(lblDescription);

        paneComponents.getConstraints().insets = new Insets(0, 8, 5, 0);
        paneComponents.setConstraints(0, 7);
        paneComponents.addComponent(lblOnLoan);
        paneComponents.getConstraints().insets = new Insets(0, 8, 0, 0);

        //endregion

        //region Text Fields

        paneComponents.getConstraints().insets = new Insets(8, 8, 0, 0);
        paneComponents.setConstraints(1, 0);
        paneComponents.addComponent(txtTitle);
        paneComponents.getConstraints().insets = new Insets(0, 8, 0, 0);

        paneComponents.setConstraints(1, 1);
        paneComponents.addComponent(txtAuthor);

        paneComponents.setConstraints(1, 2);
        paneComponents.addComponent(txtSection);

        paneComponents.setConstraints(1, 3);
        paneComponents.addComponent(txtX);

        paneComponents.setConstraints(1, 4);
        paneComponents.addComponent(txtY);

        paneComponents.setConstraints(1, 5);
        paneComponents.addComponent(txtBarcode);

        paneComponents.getConstraints().gridwidth = 2;
        paneComponents.setConstraints(1, 6);
        paneComponents.addComponent(scrlDescription);

        //endregion

        //region Buttons

        paneComponents.setConstraints(1, 7);
        paneComponents.addComponent(rdoOnLoan);

        paneComponents.setConstraints(0, 8);
        paneComponents.addComponent(btnNew);

        paneComponents.getConstraints().insets = new Insets(0, 0, 5, 15);
        paneComponents.setAnchor(GridBagConstraints.EAST);
        paneComponents.getConstraints().gridwidth = 2;
        paneComponents.setConstraints(1, 8);
        paneComponents.addComponent(btnSave);

        //endregion

        //endregion

        //region Component Events

        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoublyLinkedList.Node[] cds = fileManager.getCDs().toArray();
                int[] barcodes = new int[cds.length];
                for (int i = 0; i < barcodes.length; i++) {
                    barcodes[i] = ((CD) cds[i].getData()).getBarcode();
                }
                events.NewItem(barcodes);
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                events.SaveItem();
            }
        });

        //endregion

        //region Display Panel

        components.setConstraints(6, 1, 3, 1);
        components.addComponent(pane);

        // Reset Component Constraints
        components.getConstraints().gridwidth = 1;
        components.getConstraints().gridheight = 1;

        //endregion
    }

    private void Automation() {
        //region Create Panel

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        pane.setPreferredSize(new Dimension(300, 200));

        //endregion

        //region Create Panel Components Manager

        ComponentsManager paneComponents = new ComponentsManager(pane);
        paneComponents.setWeight(0.5, 0.5);
        paneComponents.getConstraints().insets = new Insets(8, 8, 0, 0);

        //endregion

        //region Create Components

        JLabel lblTitle = new JLabel("Automation Action Request for the item above", JLabel.CENTER);
        JButton btnRetrieve = new JButton("Retrieve");
        JButton btnRemove = new JButton("Remove");
        JButton btnReturn = new JButton("Return");
        JButton btnAdd = new JButton("Add");

        JLabel lblSort = new JLabel("Sort Section: ");
        JTextField txtSection = new JTextField(10);
        JButton btnRandom = new JButton("Random Sort");
        JButton btnMostly = new JButton("Mostly Sort");
        JButton btnReverse = new JButton("Reverse Sort");

        //endregion

        //region Edit Components

        btnRemove.setPreferredSize(btnRetrieve.getPreferredSize());
        btnReturn.setPreferredSize(btnRetrieve.getPreferredSize());
        btnAdd.setPreferredSize(btnRetrieve.getPreferredSize());

        btnRandom.setPreferredSize(new Dimension(111, 26));
        btnMostly.setPreferredSize(btnRandom.getPreferredSize());
        btnReverse.setPreferredSize(btnRandom.getPreferredSize());

        //endregion

        //region Display Components

        //region Title

        paneComponents.addComponent(lblTitle, 0, 0, 2, 1);
        paneComponents.getConstraints().insets = new Insets(0, 8, 0, 8);
        paneComponents.getConstraints().gridwidth = 1;

        //endregion

        //region Sort

        paneComponents.setConstraints(0, 3);
        paneComponents.addComponent(lblSort);
        paneComponents.setConstraints(1, 3);
        paneComponents.addComponent(txtSection);

        //endregion

        //region Buttons

        paneComponents.setConstraints(0, 1);
        paneComponents.addComponent(btnRetrieve);
        paneComponents.setConstraints(1, 1);
        paneComponents.addComponent(btnRemove);

        paneComponents.setConstraints(0, 2);
        paneComponents.addComponent(btnReturn);
        paneComponents.setConstraints(1, 2);
        paneComponents.addComponent(btnAdd);

        //region Sort Buttons

        paneComponents.setConstraints(1, 4);
        paneComponents.addComponent(btnRandom);
        paneComponents.setConstraints(1, 5);
        paneComponents.addComponent(btnMostly);
        paneComponents.setConstraints(1, 6);
        paneComponents.addComponent(btnReverse);

        //endregion

        //endregion

        //endregion

        //region Component Events

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = events.GetTableComponent().getSelectedRow();
                if (row >= 0) {
                    events.Add(row);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No CD Selected",
                            "Return",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = events.GetTableComponent().getSelectedRow();
                if (row >= 0) {
                    events.Return(row);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No CD Selected",
                            "Return",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = events.GetTableComponent().getSelectedRow();
                if (row >= 0) {
                    events.Retrieve(row);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No CD Selected",
                            "Return",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = events.GetTableComponent().getSelectedRow();
                if (row >= 0) {
                    events.Remove(row);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No CD Selected",
                            "Return",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String section = txtSection.getText();
                if (section.length() > 0) {
                    events.RandomSort(section);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No Section Specified",
                            "Random Sort",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnMostly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String section = txtSection.getText();
                if (section.length() > 0) {
                    events.MostlySort(section);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No Section Specified",
                            "Mostly Sort",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnReverse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String section = txtSection.getText();
                if (section.length() > 0) {
                    events.ReverseSort(section);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "No Section Specified",
                            "Reverse Sort",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //endregion

        //region Display Panel

        components.setConstraints(6, 2, 3, 1);
        components.addComponent(pane);

        // Reset Component Constraints
        components.getConstraints().gridwidth = 1;
        components.getConstraints().gridheight = 1;

        //endregion
    }

    //endregion
}