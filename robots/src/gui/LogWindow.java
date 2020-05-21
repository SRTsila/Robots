package gui;

import fileWork.ConfigurationDataRecoverer;
import fileWork.Tuple;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogWindow extends JInternalFrame implements LogChangeListener, ProcessStatement {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    private final Map<String, Integer> previousStatement;

    LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        previousStatement = recoverStatement();
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
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
            int previousY = previousStatement.get("y");
            super.setLocation(previousX, previousY);
        }
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public Tuple<String, Map<String, String>> saveStatement() {
        Point position = this.getLocation();
        Dimension size = this.getSize();
        Boolean isClosed = this.isClosed();
        Map<String, String> statement = createStatementMap(position, size, isClosed);
        return new Tuple<>("log", statement);
    }

    @Override
    public Map<String, Integer> recoverStatement() {
        try {
            ConfigurationDataRecoverer recoverer = new ConfigurationDataRecoverer();
            return recoverer.getStatement("log");
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
