package com.engeto.ja;

public class Settings {

    private static final String FILE_NAME = "resources/kvetiny.txt";
    private static final String DELIMITER = "\t";
    private static final String FILE_NAME_OUT = "resources/output.txt";
    private static final String FILE_NAME_TEST1 = "resources/kvetiny-spatne-datum.txt";
    private static final String FILE_NAME_TEST2 = "resources/kvetiny-spatne-frekvence.txt";
    private static final String FILE_NAME_TEST3 = "resources/neexistujici-soubor.txt";

    public static String getFileName() {
        return FILE_NAME;
    }

    public static String getDelimiter() {
        return DELIMITER;
    }

    public static String getFileNameOut() {
        return FILE_NAME_OUT;
    }

    public static String getFileNameTest1() {
        return FILE_NAME_TEST1;
    }

    public static String getFileNameTest2() {
        return FILE_NAME_TEST2;
    }

    public static String getFileNameTest3() {
        return FILE_NAME_TEST3;
    }

}
