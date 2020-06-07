package fileWork;

class ConfigurationFile {
    private static final String SEPARATOR = System.getProperty("file.separator");
    static final String ABSOLUTE_PATH = System.getProperty("user.dir") + SEPARATOR + "robots" + SEPARATOR + "src" + SEPARATOR + "configuration.txt";
}
