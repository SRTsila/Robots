package gui;

import fileWork.ConfigurationDataRecover;
import fileWork.Tuple;
import fileWork.ConfigurationDataSaver;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;


class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane;
    private final LogWindow logWindow;
    private final GameWindow gameWindow;
    private final RobotCoordinatesWindow robotCoordinatesWindow;
    private final GameModel gameModel;
    private final ConfigurationDataRecover recover;
    private String location;


    MainApplicationFrame() {
        recover = new ConfigurationDataRecover();
        location = recover.getLocation();
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);
        logWindow = createLogWindow();
        addWindow(logWindow);
        gameModel = new GameModel();
        gameWindow = new GameWindow(gameModel, recover);
        gameWindow.setLocation(1, 1);
        gameWindow.setSize(1200, 1200);
        addWindow(gameWindow);
        robotCoordinatesWindow = new RobotCoordinatesWindow(gameModel, recover);
        addWindow(robotCoordinatesWindow);
        setJMenuBar(generateMenuBar());
    }

    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), recover);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }


    private static JMenu createSubMenu(String name, int key, String... subMenuNames) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(key);
        for (String menuName : subMenuNames) {
            menu.getAccessibleContext().setAccessibleDescription(menuName);
        }
        return menu;
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = createLookAndFeelMenu();
        JMenu testMenu = createTestMenu();
        JMenu exitMenu = createExitMenu();
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenu);
        return menuBar;
    }

    private JMenu createExitMenu() {
        JMenu exitMenu = createSubMenu("Выход", KeyEvent.VK_E, "Закрытие приложения");

        {
            JMenuItem exitItem = new JMenuItem(res.getString("ExitMenuSubMenuText"), KeyEvent.VK_X | KeyEvent.VK_ALT);
            exitItem.addActionListener((event) -> {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                        new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            });

            exitMenu.add(exitItem);
        }
        return exitMenu;
    }

    private JMenu createTestMenu() {
        JMenu testMenu = createSubMenu("Тесты", KeyEvent.VK_T, "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem(res.getString("TestMenuSubMenuText"), KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
    }



    private JMenu createLookAndFeelMenu() {
        JMenu lookAndFeelMenu = createSubMenu(res.getString("LookAndFeelMenuName"), KeyEvent.VK_V,
                res.getString("LookAndFeelMenuSubMenuName"));

        {
            JMenuItem systemLookAndFeel = new JMenuItem(res.getString("LookAndFeelMenuSubMenuType1"), KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem(res.getString("LookAndFeelMenuSubMenuType2"), KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }
        return lookAndFeelMenu;
    }


    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }

    void saveStatement() {
        List<Tuple<String, Map<String, String>>> allWindowsConfigs = new ArrayList<>();
        allWindowsConfigs.add(this.logWindow.saveStatement("log", this.logWindow));
        allWindowsConfigs.add(this.gameWindow.saveStatement("model", this.gameWindow));
        allWindowsConfigs.add(this.robotCoordinatesWindow.saveStatement("coordinates", this.robotCoordinatesWindow));
        ConfigurationDataSaver conf = new ConfigurationDataSaver();
        conf.saveData(allWindowsConfigs, location);
    }
}
