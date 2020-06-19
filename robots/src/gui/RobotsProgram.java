package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowClosingAdapter());
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            Runtime.getRuntime().addShutdownHook(new Thread(frame::saveStatement));
        });
    }

    private static class WindowClosingAdapter extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            Object[] options = {"Yes", "No"};
            int code = JOptionPane.showOptionDialog(e.getWindow(),
                    "Close app?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (code == 0) {
                e.getWindow().setVisible(false);
                System.exit(0);
            }
        }
    }
}

