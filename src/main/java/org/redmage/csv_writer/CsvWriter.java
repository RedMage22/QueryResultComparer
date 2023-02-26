package org.redmage.csv_writer;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class CsvWriter {
    private Logger logger = Logger.getLogger(CsvWriter.class.getName());

    public static void write(String filePath, Map<String, String> resultsMap) {
        if (filePath == null || resultsMap == null) {
            return;
        }
        try {

            File csvFile = new File(filePath);
            csvFile.setWritable(true);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvFile));
            System.out.println("Buffered writer starts writing :");

            resultsMap.forEach((k,v) -> {
                String resultStr;
                if (!k.contains("Records summary")) {
                    resultStr = k.concat(",").concat(v).concat("\n");
                } else {
                    resultStr = "\n".concat(k.concat("\n").concat(v).concat("\n"));
                }
                try {
                    bufferedWriter.write(resultStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
