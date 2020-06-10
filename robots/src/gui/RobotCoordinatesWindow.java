package gui;

import fileWork.ConfigurationDataRecover;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.Map;

class RobotCoordinatesWindow extends JInternalFrame implements Observer, ProcessStatement {
    private final TextArea textArea;
    private final GameModel gameModel;
    private final Map<String, Integer> previousStatement;


    RobotCoordinatesWindow(GameModel gameModel, ConfigurationDataRecover recover) {
        super("Координаты робота", true, true, true, true);
        previousStatement = recoverStatement("coordinates", recover);
        setSize(200, 200);
        setLocation(5, 10);
        setVisible(true);
        textArea = new TextArea();
        this.add(textArea);
        this.gameModel = gameModel;
        this.gameModel.attach(this);
    }


    public void setSize(int width, int height) {
        if (previousStatement == null)
            super.setSize(200, 200);
        else {
            int previousWidth = previousStatement.get("width");
            int previousHeight = previousStatement.get("height");
            boolean isClosed = previousStatement.get("isClosed") == 1;
            try {
                this.setIcon(isClosed);
            } catch (PropertyVetoException ignored) {
            }
            super.setVisible(!isClosed);
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

    @Override
    public void update() {
        GameStatement gameStatement = gameModel.getState();
        textArea.append("X: " + gameStatement.m_robotPositionX + "\n"
                + "Y: " + gameStatement.m_robotPositionY + "\n");
    }
}
