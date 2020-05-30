package gui;

import fileWork.ConfigurationDataRecoverer;
import fileWork.Tuple;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class RobotCoordinatesWindow extends JInternalFrame implements Observer, ProcessStatement {
    private final TextArea textArea;
    private final GameModel gameModel;
    private final Map<String, Integer> previousStatement;


    RobotCoordinatesWindow(GameModel gameModel) {
        super("Координаты робота", true, true, true, true);
        previousStatement = recoverStatement();
        setSize(200, 200);
        setLocation(5, 10);
        setVisible(true);
        textArea = new TextArea();
        this.add(textArea);
        this.gameModel = gameModel;
        this.gameModel.attach(this);
    }

    public void setVisible(boolean visible){
        if (previousStatement == null)
            super.setVisible(visible);
        else {
            boolean isClosed = previousStatement.get("isClosed") == 1;
            try {
                super.setClosed(isClosed);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSize(int width, int height) {
        if (previousStatement == null)
            super.setSize(200, 200);
        else {
            int previousWidth = previousStatement.get("width");
            int previousHeight = previousStatement.get("height");
            boolean isClosed = previousStatement.get("isClosed") == 1;
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

    @Override
    public Tuple<String, Map<String, String>> saveStatement() {
        Point position = this.getLocation();
        Dimension size = this.getSize();
        Boolean isClosed = this.isClosed();
        Map<String, String> statement = createStatementMap(position, size, isClosed);
        return new Tuple<>("coordinates", statement);
    }

    @Override
    public Map<String, Integer> recoverStatement() {
        try {
            ConfigurationDataRecoverer recoverer = new ConfigurationDataRecoverer();
            return recoverer.getStatement("coordinates");
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public Map<String, String> createStatementMap(Point position, Dimension size, Boolean isClosed) {
        Map<String, String> result = new HashMap<>();
        result.put("x", String.valueOf(position.x));
        result.put("y", String.valueOf(position.y));
        result.put("width", String.valueOf(size.width));
        result.put("height", String.valueOf(size.height));
        result.put("isClosed", String.valueOf(isClosed));
        return result;
    }
}
