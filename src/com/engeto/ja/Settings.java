package com.engeto.ja;

public class Settings {

    private static final String FILE_NAME = "resources/kvetiny.txt";
    private static final String DELIMITER = "\t";

    public static String getFileName() {
        return FILE_NAME;
    }

    public static String getDelimiter() {
        return DELIMITER;
    }

}
