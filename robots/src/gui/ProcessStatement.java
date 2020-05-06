package gui;

import fileWork.Tuple;

import java.awt.*;
import java.util.Map;

public interface ProcessStatement {
    Tuple<String,Map<String,String>> saveStatement();
    Map<String,Integer> recoverStatement();
    Map<String,String> createStatementMap(Point position,Dimension size, Boolean isClosed);
}
