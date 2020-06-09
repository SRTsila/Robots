package gui;

import fileWork.ConfigurationDataRecover;
import fileWork.Tuple;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public interface ProcessStatement {
    default Tuple<String, Map<String, String>> saveStatement(String windowName, JInternalFrame window) {
        Point position = window.getLocation();
        Dimension size = window.getSize();
        Boolean isClosed = window.isClosed();
        Map<String, String> statement = createStatementMap(position, size, isClosed);
        return new Tuple<>(windowName, statement);
    }

    default Map<String, Integer> recoverStatement(String windowName, ConfigurationDataRecover recover) {
        return recover.getStatement(windowName);
    }

    default Map<String, String> createStatementMap(Point position, Dimension size, Boolean isClosed) {
        Map<String, String> result = new HashMap<>();
        result.put("x", String.valueOf(position.x));
        result.put("y", String.valueOf(position.y));
        result.put("width", String.valueOf(size.width));
        result.put("height", String.valueOf(size.height));
        result.put("isClosed", String.valueOf(isClosed));
        return result;
    }
}
