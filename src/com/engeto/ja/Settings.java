package com.engeto.ja;

public class Settings {

    private static final String FILE_NAME_IN = "resources/kvetiny.txt";
    private static final String FILE_NAME_OUT = "resources/kvetiny_out.txt";
    private static final String DELIMITER = "\t";

    public static String getFileNameIn() {
        return FILE_NAME_IN;
    }

    public static String getFileNameOut() {
        return FILE_NAME_OUT;
    }

    public static String getDelimiter() {
        return DELIMITER;
    }

}
