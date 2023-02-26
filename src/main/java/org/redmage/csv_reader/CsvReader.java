package org.redmage.csv_reader;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CsvReader {

    public static Map<String, String> read(String file) {
        String row;
        Map<String, String> map = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            map = new LinkedHashMap<>();
            while ((row = reader.readLine()) != null) {
                map.put(getKey(row), getValue(row));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private static String getKey(String line) {
        if (line == null || !line.contains(",")) {
            return null;
        }
        return line.substring(0, line.indexOf(","));
    }

    private static String getValue(String line) {
        if (line == null || !line.contains(",")) {
            return null;
        }

        return line.substring(line.indexOf(",") + 1);
    }

}
