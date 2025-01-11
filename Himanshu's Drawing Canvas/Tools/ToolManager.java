package Tools;
import UICanvas.CanvasPanel;

public class ToolManager {
   private Tools selectedTool;
   public CanvasPanel panel;

   public ToolManager(CanvasPanel panel){
       this.panel = panel;
   }

    public void selectTool(Tools tool){
       this.selectedTool = tool;
   }

   public Tools getSelectedTool(){
       return selectedTool;
   }
}
