package gui;

import fileWork.ConfigurationDataRecoverer;
import fileWork.Tuple;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JInternalFrame implements ProcessStatement {
    private final GameVisualizer m_visualizer;
    private final Map<String, Integer> previousStatement;

    GameWindow(GameModel gameModel)
    {
        super("Игровое поле", true, true, true, true);
        previousStatement = recoverStatement();

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
            int previousY= previousStatement.get("y");
            super.setLocation(previousX, previousY);
        }
    }

    @Override
    public Tuple<String, Map<String, String>> saveStatement() {
        Point position = this.getLocation();
        Dimension size = this.getSize();
        Boolean isClosed = this.isClosed();
        Map<String,String> statement = createStatementMap(position,size,isClosed);
        return new Tuple<>("model", statement);
    }

    @Override
    public Map<String,Integer> recoverStatement() {
        try {
            ConfigurationDataRecoverer recoverer = new ConfigurationDataRecoverer();
            return recoverer.getStatement("model");
        }
        catch (IOException ex){
            return null;
        }
    }

    @Override
    public Map<String, String> createStatementMap(Point position, Dimension size, Boolean isClosed) {
        Map<String,String> result = new HashMap<>();
        result.put("x", String.valueOf(position.x));
        result.put("y", String.valueOf(position.y));
        result.put("width",String.valueOf(size.width));
        result.put("height",String.valueOf(size.height));
        result.put("isClosed",String.valueOf(isClosed));
        return result;
    }
}
