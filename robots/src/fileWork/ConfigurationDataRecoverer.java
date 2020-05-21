package fileWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/**
 * Класс Восстановителя состояния, который восстанавливает геометрию окошек.
 */
public class ConfigurationDataRecoverer {

    private final String separator = System.getProperty("file.separator");
    private final String absPath = System.getProperty("user.dir") + separator + "robots" + separator + "src" + separator + "configuration.txt";
    private static Map<String, Map<String, Integer>> recoveredData;

    public ConfigurationDataRecoverer() throws IOException {
        if (recoveredData == null) {
            List<String> inputData = readFileData();
            List<String> splitData = new ArrayList<>();
            for (String line : inputData) {
                splitData.addAll(Arrays.asList(line.split(" -> ")));
            }
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
    }

    public Map<String, Integer> getStatement(String windowName) {
        return recoveredData.getOrDefault(windowName, null);
    }


    private List<String> readFileData() throws IOException {
        return Files.readAllLines(Paths.get(absPath));
    }

}
