package fileWork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс запоминания состояния. Запоминает состояние окошек и записывает в файл configuration.txt
 */

public class ConfigurationDataSaver {
    private Map<String, String> commonData;
    private final String separator = System.getProperty("file.separator");
    private final String absPath = System.getProperty("user.dir") + separator + "robots" + separator + "src" + separator + "configuration.txt";

    public void saveData(List<Tuple<String, Map<String, String>>> data) {
        createMapFromMapsWithPrefixKeys(data);
        writeData();
    }


    private void writeData() {
        File configureFile = new File(absPath);
        try (FileWriter writer = new FileWriter(configureFile, false)) {
            for (String key : commonData.keySet()) {
                writer.write(key + " -> " + commonData.get(key) + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMapFromMapsWithPrefixKeys(List<Tuple<String, Map<String, String>>> maps) {
        commonData = new HashMap<>();
        maps.forEach(pair -> pair.getSecond().forEach((key, value) -> commonData.put(pair.getFirst() + "." + key, value)));
    }
}
