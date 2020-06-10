package gui;
import javax.swing.*;
import java.awt.*;

import fileWork.ConfigurationDataRecover;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.Map;

public class GameWindow extends JInternalFrame implements ProcessStatement {
    private final GameVisualizer m_visualizer;
    private final Map<String, Integer> previousStatement;


    GameWindow(GameModel gameModel, ConfigurationDataRecover recover) {
        super("Игровое поле", true, true, true, true);
        previousStatement = recoverStatement("model", recover);
        m_visualizer = new GameVisualizer(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void setSize(int width, int height) {
        if (previousStatement == null)
            super.setSize(width, height);
        else {
            boolean isClosed = previousStatement.get("isClosed") == 1;
            int previousWidth = previousStatement.get("width");
            int previousHeight = previousStatement.get("height");
            try {
                this.setIcon(isClosed);
            } catch (PropertyVetoException ignored) {
            }
            super.setSize(previousWidth, previousHeight);
        }
    }

    public void setLocation(int x, int y) {
        if (previousStatement == null)
            super.setLocation(x, y);
        else {
            int previousX = previousStatement.get("x");
            int previousY = previousStatement.get("y");
            super.setLocation(previousX, previousY);
        }
    }
}
