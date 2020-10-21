package util;

import javax.swing.*;
import java.awt.*;

/**
 * Manage Components of a JFrame
 */
public class ComponentsManager {
    JFrame frame;
    JPanel pane;
    GridBagConstraints constraints;

    /**
     *
     * @param frame Already instantiated frame (Does not create a new one)
     */
    public ComponentsManager(JFrame frame) {
        this.frame = frame;
        constraints = new GridBagConstraints();
    }

    /**
     *
     * @param pane Already instantiate pane (Does not create a new one)
     */
    public ComponentsManager(JPanel pane) {
        this.pane = pane;
        constraints = new GridBagConstraints();
    }

    /**
     * Add component and update constraints
     *
     * @param component
     * @param gridX
     * @param gridY
     * @param gridWidth
     * @param gridHeight
     * @param <C>
     * @return C component
     */
    public <C extends Component> C addComponent(C component, int gridX, int gridY, int gridWidth, int gridHeight) {
        setConstraints(gridX, gridY, gridWidth, gridHeight);

        if (frame != null) {
            frame.getContentPane().add(component, constraints);
        } else if (pane != null) {
            pane.add(component, constraints);
        }
        return component;
    }

    /**
     * Add component with previous constaints
     *
     * @param component
     * @return C component
     */
    public <C extends Component> C addComponent(C component) {
        if (frame != null) {
            frame.getContentPane().add(component, constraints);
        } else if (pane != null) {
            pane.add(component, constraints);
        }
        return component;
    }

    /**
     * Set the constraints of the window
     *
     * @param gridX
     * @param gridY
     * @param gridWidth
     * @param gridHeight
     */
    public void setConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridheight = gridHeight;
        constraints.gridwidth = gridWidth;
    }

    /**
     * Set constraints of the window
     *
     * @param gridX
     * @param gridY
     */
    public void setConstraints(int gridX, int gridY) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
    }

    /**
     * Set the weight contraints of the window
     *
     * @param weightX
     * @param weightY
     */
    public void setWeight(double weightX, double weightY) {
        constraints.weightx = weightX;
        constraints.weighty = weightY;
    }

    /**
     * Set the fill constraints of the window
     *
     * @param fill
     */
    public void setFill(int fill) {
        constraints.fill = fill;
    }

    /**
     * Set the anchor constraints of the window
     *
     * @param anchor
     */
    public void setAnchor(int anchor) {
        constraints.anchor = anchor;
    }

    /**
     * Get the window constraints
     * Can be used to modify the constraints without the provided methods
     *
     * @return GridBagConstraints constraints
     */
    public GridBagConstraints getConstraints() {
        return this.constraints;
    }
}
