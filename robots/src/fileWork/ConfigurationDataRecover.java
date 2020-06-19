package fileWork;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static fileWork.ConfigurationFile.ABSOLUTE_PATH;


/**
 * Класс Восстановителя состояния, который восстанавливает геометрию окошек.
 */
public class ConfigurationDataRecover {
    private final Map<String, Map<String, Integer>> recoveredData;
    private String location = "ru";


    public ConfigurationDataRecover() {

        List<String> splitData = readFileData();
        recoveredData = new HashMap<>();
        for (int i = 0; i < splitData.size(); i += 2) {
            String prefix = splitData.get(i).split("\\.")[0];
            String key = splitData.get(i).split("\\.")[1];
            String strValue = splitData.get(i + 1);
            int value;

            if (!key.startsWith("isClosed"))
                value = Integer.parseInt(strValue);
            else
                value = strValue.startsWith("true") ? 1 : 0;

            if (recoveredData.getOrDefault(prefix, null) != null)
                recoveredData.get(prefix).put(key, value);
            else {
                Map<String, Integer> map = new HashMap<>();
                map.put(key, value);
                recoveredData.put(prefix, map);
            }
        }
    }


    public Map<String, Integer> getStatement(String windowName) {
        return recoveredData.getOrDefault(windowName, null);
    }

    public String getLocation() {
        return location;
    }

    private List<String> readFileData() {
        try {
            List<String> inputData = Files.readAllLines(Paths.get(ABSOLUTE_PATH));
            List<String> splitData = new ArrayList<>();
            for (String line : inputData) {
                if (line.startsWith("location")) {
                    location = line.substring(12);
                    continue;
                }
                splitData.addAll(Arrays.asList(line.split(" -> ")));
            }
            return splitData;
        } catch (IOException ex) {
            System.out.println("Didn't find configuration file");
            return Collections.emptyList();
        }
    }
}