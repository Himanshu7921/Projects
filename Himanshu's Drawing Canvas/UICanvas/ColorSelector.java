package UICanvas;
import Pointer.*;
import java.awt.*;

public class ColorSelector {

    CanvasPanel panel;
    Pointer pointer;
    ColorSelector(CanvasPanel panel){
        this.panel = panel;
        pointer = new Pointer(panel);
    }
    String [] inkColors = {"Red", "Blue", "Green", "Dark Grey", "Purple", "Skin", "Sky Blue"};

    public Color colorSelector(int randomNum) {
        String currentColor = inkColors[randomNum];
        if(currentColor.equalsIgnoreCase("Red")){
            return new Color(255, 139, 148);
        }
        else if(currentColor.equalsIgnoreCase("Blue")){
            return new Color(168, 230, 207);
        }
        else if(currentColor.equalsIgnoreCase("Green")){
            return new Color(220,237, 193);
        }
        else if(currentColor.equalsIgnoreCase("Dark Grey")){
            return new Color(163, 145, 147);
        }
        else if(currentColor.equalsIgnoreCase("Purple")){
            return new Color(160, 32, 240);
        }
        else if(currentColor.equalsIgnoreCase("Skin")){
            return new Color(255, 211, 182);
        }
        else if(currentColor.equalsIgnoreCase("Sky Blue")){
            return new Color(171, 174, 198);
        }
        panel.repaint();
        return Color.BLACK;
    }

}
