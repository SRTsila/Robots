package gui;

import fileWork.ConfigurationDataRecover;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.ResourceBundle;

public class LogWindow extends JInternalFrame implements LogChangeListener, ProcessStatement {
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;
    private final Map<String, Integer> previousStatement;

    LogWindow(LogWindowSource logSource, ConfigurationDataRecover recover, ResourceBundle res) {

        super(res.getString("logWindowName"), true, true, true, true);
        previousStatement = recoverStatement("log", recover);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setLocation(0, 215);
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

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

}
