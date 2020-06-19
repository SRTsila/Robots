package fileWork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fileWork.ConfigurationFile.ABSOLUTE_PATH;


/**
 * Класс запоминания состояния. Запоминает состояние окошек и записывает в файл configuration.txt
 */

public class ConfigurationDataSaver {
    private Map<String, String> commonData;

    public void saveData(List<Tuple<String, Map<String, String>>> data, String location) {
        createMapFromMapsWithPrefixKeys(data);
        writeData(location);
    }


    private void writeData(String location) {
        File configureFile = new File(ABSOLUTE_PATH);
        try (FileWriter writer = new FileWriter(configureFile, false)) {
            for (Map.Entry<String, String> pair : commonData.entrySet()) {
                writer.write(pair.getKey() + " -> " + pair.getValue() + "\n");
            }
            writer.write("location -> " + location);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Impossible to create configuration file");
        }
    }

    private void createMapFromMapsWithPrefixKeys(List<Tuple<String, Map<String, String>>> maps) {
        commonData = new HashMap<>();
        maps.forEach(pair -> pair.getSecond().forEach((key, value) -> commonData.put(pair.getFirst() + "." + key, value)));
    }
}

