package ToolBar;

import Buttons.Buttons;
import UICanvas.CanvasPanel;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JToolBar {

    public CanvasPanel panel;

    public ToolBar(CanvasPanel panel) {
        this.panel = panel;
        Buttons buttons = new Buttons(panel);
        this.setBackground(new Color(171, 174, 198));

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Center the buttons with spacing

        // Add each button individually to the toolbar
        this.add(panel.buttons.palmTool);
        this.add(panel.buttons.rectangleTool);
        this.add(panel.buttons.circleTool);
        this.add(panel.buttons.eraserTool);
        this.add(panel.buttons.penTool);
        this.add(panel.buttons.lineTool);
        this.add(panel.buttons.moveTool);
        this.add(panel.buttons.selectTool);
        this.add(panel.buttons.textTool);
        this.add(panel.buttons.undoTool);
        this.add(panel.buttons.redoTool);

        this.add(Box.createHorizontalGlue());
    }
}
