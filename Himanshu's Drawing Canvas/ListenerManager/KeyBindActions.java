package ListenerManager;

import UICanvas.CanvasPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyBindActions {
    CanvasPanel panel;

    public KeyBindActions(CanvasPanel panel) {
        this.panel = panel;
    }

    public KeyBindActions() {}

    public void bindKeyToAction(String keyStroke, String name, Action action) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(keyStroke), name);
        actionMap.put(name, action);
    }
}
