package fileWork;

public interface FileWorker {
    String SEPARATOR = System.getProperty("file.separator");
    String ABSOLUTE_PATH = System.getProperty("user.dir") + SEPARATOR + "robots" + SEPARATOR + "src" + SEPARATOR + "configuration.txt";
}
