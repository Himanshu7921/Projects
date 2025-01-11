package Shapes;

import Calculations.DistanceCalculation;
import UICanvas.CanvasPanel;

public class Triangle implements Shapes {

    public CanvasPanel panel;
    public DistanceCalculation distanceCalculation;

    public Triangle(CanvasPanel panel){
        this.panel = panel;
        distanceCalculation = new DistanceCalculation();
    }

    @Override
    public void draw() {

    }
}
