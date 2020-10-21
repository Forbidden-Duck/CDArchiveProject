package views.BinaryTree;

import structures.trees.BinaryTree;
import structures.trees.BinaryTree.Node;

import java.awt.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MainDisplay extends JPanel {
    int screenWidth = 800;
    int screenHeight = 500;

    JFrame frame = new JFrame("Binary Tree");
    SpringLayout layout = new SpringLayout();
    int rootX = 350;
    int rootY = 5;
    int heightSize = 50;
    int widthSize = 67;
    Graphics g;
    BinaryTree tree;


    @Override
    public void paintComponent(Graphics g) {
        this.g = g;
        this.traverseTree(tree);
        frame.repaint();
        this.repaint();
    }

    public MainDisplay(BinaryTree tree) {
        this.tree = tree;

        this.setLayout(layout);
        this.setAutoscrolls(true);
        JScrollPane scrlPanel = new JScrollPane(this);
        scrlPanel.setPreferredSize(new Dimension(screenWidth - 15, screenHeight - 38));
        frame.getContentPane().add(scrlPanel);

        frame.setSize(new Dimension(screenWidth, screenHeight));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
    }

    private void DrawNode(Graphics g, String text, int x, int y) {
        g.drawRect(x, y, widthSize, heightSize);
        JLabel label = new JLabel(text, JLabel.CENTER);
        this.add(label);
        layout.putConstraint(SpringLayout.WEST, label, x + 30 - (3 * text.length()), SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, y + 15, SpringLayout.NORTH, this);
    }

    private void traverseTree(BinaryTree tree) {
        Node root = tree.getRoot();
        String v1 = root.getKey() + "";
        DrawNode(g, v1, rootX, rootY);
        this.traverseTree(root, rootX, rootY);
    }

    private void traverseTree(Node focused, int fX, int fY) {
        if (focused != null) {
            int RowsOnScreen = (fY - 5) / 70;
            int distanceApart = 120;
            if (RowsOnScreen >= 1) {
                if ((RowsOnScreen & 1) == 0) {
                    distanceApart = distanceApart - 30;
                } else {
                    distanceApart = distanceApart - 60;
                }
            }

            Node left = focused.getLeft();
            Node right = focused.getRight();
            if (left != null) {
                int lXSize = fX - distanceApart;
                int lYSize = fY + 70;
                int lX1 = fX;
                int lY1 = fY + (heightSize / 2);
                int lX2 = lXSize + (widthSize / 2);
                int lY2 = lYSize;

                String v2 = left.getKey() + "";
                DrawNode(g, v2, lXSize, lYSize);
                g.drawLine(lX1, lY1, lX2, lY2);
                this.traverseTree(left, lXSize, lYSize);
            }

            if (right != null) {
                int rXSize = fX + distanceApart;
                int rYSize = fY + 70;
                int rX1 = fX + widthSize;
                int rY1 = fY + (heightSize / 2);
                int rX2 = rXSize + (widthSize / 2);
                int rY2 = rYSize;

                String v3 = right.getKey() + "";
                DrawNode(g, v3, rXSize, rYSize);
                g.drawLine(rX1, rY1, rX2, rY2);
                this.traverseTree(right, rXSize, rYSize);
            }
        }
    }
}
