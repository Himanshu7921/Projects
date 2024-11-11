package Buttons;

import UICanvas.CanvasPanel;
import javax.swing.*;
import java.awt.*;

public class Buttons {
    public CanvasPanel panel;


    public JButton moveTool, rectangleTool, circleTool, lineTool, textTool, eraserTool;
    public JButton undoTool, redoTool, selectTool, penTool, palmTool;

    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;

    public Buttons(CanvasPanel panel) {
        this.panel = panel;

        moveTool = createButton("res/moveTool.png", "Move");
        palmTool = createButton("res/palm.png", "Palm");
        rectangleTool = createButton("res/rectangle.png", "Rectangle");
        circleTool = createButton("res/circle.png", "Circle");
        lineTool = createButton("res/line.png", "Line");
        penTool = createButton("res/pen.png", "Pen");
        textTool = createButton("res/textTool.png", "Text");
        eraserTool = createButton("res/eraser.png", "Eraser");

        // Undo and Redo buttons with action listeners
        undoTool = createButton("res/undo.png", "Undo");
        redoTool = createButton("res/redo.png", "Redo");
        selectTool = createButton("res/select.png", "Select");
    }

    private JButton createButton(String iconPath, String toolTip) {
        JButton button = new JButton(resizeIcon(iconPath, BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setToolTipText(toolTip);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        button.addActionListener(e -> handleButtonAction(toolTip));  // Add action listener for other buttons
        return button;
    }

    private ImageIcon resizeIcon(String iconPath, int width, int height) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    private void handleButtonAction(String toolTip) {
        switch (toolTip) {
            case "Move":
                // Move logic here
                break;
            case "Rectangle":
                panel.label.setText("Rectangle Selected");
                panel.myMouseListener.rectangle = true;
                panel.resetToolsExcept("rectangle");
                break;
            case "Line":
                panel.label.setText("Line Selected");
                panel.myMouseListener.line = true;
                panel.resetToolsExcept("line");
                break;
            case "Circle":
                panel.label.setText("Circle Selected");
                panel.myMouseListener.circle = true;
                panel.resetToolsExcept("circle");
                break;
            case "Eraser":
                panel.label.setText("Eraser Selected");
                panel.myMouseListener.rectEraser = true;
                panel.resetToolsExcept("rectEraser");
                break;
            case "Pen":
                panel.label.setText("Pen Selected");
                panel.myMouseListener.pen = true;
                panel.resetToolsExcept("pen");
                break;
            default:
                System.out.println("Unknown tool selected");
                break;
        }
    }
}
