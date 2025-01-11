package UICanvas;

import Buttons.Buttons;
import ListenerManager.*; // Change this import
import Shapes.Circle;
import Shapes.Rectangle;
import Shapes.Triangle;
import ToolBar.ToolBar;
import Tools.*;
import Pointer.*;
import Calculations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CanvasPanel extends JPanel {
    Dimension SCREEN_SIZE;
    public  int CANVAS_WIDTH;
    public  int CANVAS_HEIGHT;
    public int strokeWidth = 5;

    public Color currentColor = Color.BLACK;
    public Random random;
    public ColorSelector colorSelector;
    public Pointer pointer;
    public JLabel label;
    public MyMouseListener myMouseListener;
    public KeyboardListener keyboardListener; // Changed variable name
    public Circle circle;
    public Rectangle rectangle;
    public Triangle triangle;
    public Eraser eraser;
    public Pen pen;
    public Pencil pencil;
    public Line line;
    public ToolManager toolManager;
    public BufferedImage canvasImage;
    public DistanceCalculation distance;
    public Color color;
    public ToolBar toolbar;
    public Buttons buttons;
    public KeyBindActions keyBinding;
    public Graphics2D g2d;
    public Graphics g;


    CanvasPanel() {

        canvasImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);  // Initialize canvasImage

        this.SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
        CANVAS_WIDTH = SCREEN_SIZE.width;
        CANVAS_HEIGHT = SCREEN_SIZE.height;
        this.setBackground(Color.BLACK);
        this.setPreferredSize(SCREEN_SIZE);
        pen = new Pen(this);
        keyBinding = new KeyBindActions(this);
        line = new Line(this);
        eraser = new Eraser(this);
        pencil = new Pencil();
        circle = new Circle(this);
        rectangle = new Rectangle(this);
        distance = new DistanceCalculation();
        canvasImage = new BufferedImage(SCREEN_SIZE.width, SCREEN_SIZE.height, BufferedImage.TYPE_INT_ARGB);
        g2d = canvasImage.createGraphics();
        g2d.setColor(getBackgroundColor());
        g2d.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        toolManager = new ToolManager(this);


        colorSelector = new ColorSelector(this);
        myMouseListener = new MyMouseListener(this);
        keyboardListener = new KeyboardListener(this); // Use the new listener class

        this.addMouseListener(myMouseListener);
        this.addMouseMotionListener(myMouseListener);
        this.addKeyListener(keyboardListener); // Use the new listener class

        buttons = new Buttons(this);  // Create an instance of Buttons
        toolbar = new ToolBar(this);

        pointer = new Pointer(this);
        label = new JLabel("Enter C for Circle, R for Triangle, P for Pen, L for Line, E for Eraser, Ctrl + E for Rectangular Eraser and D for Measuring Distance");
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(122, 122, 122));
        this.add(label);

        this.setFocusable(true);
        this.requestFocusInWindow();

        // Key bindings for actions
        keyBinding.bindKeyToAction("P", "Pen", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Pen Selected");
                myMouseListener.pen = true;
                resetToolsExcept("pen");
            }
        });

        keyBinding.bindKeyToAction("L", "Line", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Line Selected");
                myMouseListener.line = true;
                resetToolsExcept("line");
            }
        });

        keyBinding.bindKeyToAction("C", "Circle", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Circle Selected");
                myMouseListener.circle = true;
                resetToolsExcept("circle");
            }
        });

        keyBinding.bindKeyToAction("R", "Rectangle", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Rectangle Selected");
                myMouseListener.rectangle = true;
                resetToolsExcept("rectangle");
            }
        });

        keyBinding.bindKeyToAction("T", "Triangle", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Triangle Selected");
                myMouseListener.triangle = true;
                resetToolsExcept("triangle");
            }
        });
    }

    public void resetToolsExcept(String tool) {
        myMouseListener.rectEraser = tool.equals("rectEraser");
        myMouseListener.pen = tool.equals("pen");
        myMouseListener.line = tool.equals("line");
        myMouseListener.circle = tool.equals("circle");
        myMouseListener.rectangle = tool.equals("rectangle");
        myMouseListener.triangle = tool.equals("triangle");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        g.drawImage(canvasImage, 0, 0, null);
        g.setColor(currentColor);
        g.fillOval(pointer.pointerX - (pointer.POINTER_WIDTH / 2), pointer.pointerY - (pointer.POINTER_HEIGHT / 2), pointer.POINTER_WIDTH, pointer.POINTER_HEIGHT);

        if (myMouseListener.rectEraser) {
                g.setColor(Color.LIGHT_GRAY);  // Eraser outline color
                g.fillRect(myMouseListener.mouseCurrentX - eraser.ERASER_WIDTH / 2,
                        myMouseListener.mouseCurrentY - eraser.ERASER_HEIGHT / 2,
                        eraser.ERASER_WIDTH, eraser.ERASER_HEIGHT);
        }
        if (myMouseListener.line) {
            line.drawDummyLine(g, myMouseListener.mousePressedX, myMouseListener.mousePressedY, myMouseListener.mouseCurrentX, myMouseListener.mouseCurrentY);
        }
        if (myMouseListener.circle) {
            circle.dummyCircle(g, myMouseListener.mousePressedX, myMouseListener.mousePressedY, myMouseListener.mouseCurrentX, myMouseListener.mouseCurrentY);
        }
        if (myMouseListener.rectangle) {
            rectangle.dummyRect(g, myMouseListener.mousePressedX, myMouseListener.mousePressedY, myMouseListener.mouseCurrentX, myMouseListener.mouseCurrentY);
        }
    }

    public int selectRandomNumber() {
        random = new Random();
        return random.nextInt(colorSelector.inkColors.length - 1);
    }

    public Color getBackgroundColor() {
        return new Color(19, 23, 28);
    }

    public Graphics2D cast(Graphics g) {
        return (Graphics2D) g;
    }
}
