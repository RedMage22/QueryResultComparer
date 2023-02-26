package org.redmage.map_matcher;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapMatcher {

    public static Map<String, String> match(Map<String, String> mapA, Map<String, String> mapB) throws IllegalArgumentException {
        if (mapA == null || mapA.isEmpty() || mapB == null || mapB.isEmpty()) {
            return null;
        }

        Map<String, String> resultsMap = new LinkedHashMap<>();

        Map.Entry<String, String> firstEntry = mapA.entrySet().iterator().next();

        String firstKey = firstEntry.getKey();
        String firstValue = firstEntry.getValue();

        if (!firstValue.contains(",")) {
            throw new IllegalArgumentException("String was not comma delimited");
        }

        String[] firstValueArray = firstValue.split(",");
        String abColumns = "";

        for (String column : firstValueArray) {
            if (abColumns.isEmpty()) {
                abColumns = abColumns.concat(column).concat("_a,").concat(column).concat("_b");
            } else {
                abColumns = abColumns.concat(",").concat(column).concat("_a,").concat(column).concat("_b");

            }
        }

        abColumns = abColumns.concat(",Is equal?");
        resultsMap.put(firstKey, abColumns);

        Map<String, String> sortedMap = new TreeMap<>();

        mapA.forEach((k, v) -> {
            String newColumns = "";
            Boolean isEqual = false;
            String noValue = "no value";
            String[] mapAValues = v.split(",");

            if (mapB.containsKey(k) && !resultsMap.containsKey(k)) {
                String[] mapBValues = mapB.get(k).split(",");

                for(int i = 0; i < mapAValues.length; i++) {
                    if (i == 0) {
                        isEqual = mapAValues[i].equals(mapBValues[i]);
                    } else {
                        isEqual = isEqual && (mapAValues[i].equals(mapBValues[i]));
                    }
                    newColumns = newColumns.concat(mapAValues[i]).concat(",").concat(mapBValues[i]).concat(",");
                }
                newColumns = newColumns.concat(isEqual.toString());
            } else {
                for(int i = 0; i < mapAValues.length; i++) {
                    newColumns = newColumns.concat(mapAValues[i]).concat(",").concat(noValue).concat(",");
                }
                newColumns = newColumns.concat(isEqual.toString());
            }

            if (!resultsMap.containsKey(k)) {
                sortedMap.put(k, newColumns);
            }

        });

        resultsMap.putAll(sortedMap);

        addResultSummary(resultsMap, mapA.size(), mapB.size());

        return resultsMap;
    }

    private static void addResultSummary(Map<String, String> resultsMap, long recordTotalA,long recordTotalB) {
        long falseTotal = resultsMap.entrySet().stream().filter(k -> k.getValue().contains("false")).count();
        long noValueTotal = resultsMap.entrySet().stream().filter(k -> k.getValue().contains("no value")).count();

        String summaryKey = "----------------------Records summary-------------------------";
        String summaryValue = "Total rows in a =  " + (recordTotalA - 1) + "\n" +
                "Total rows in b = " + (recordTotalB - 1) + "\n" +
                "Total unequal (FALSE) data rows = " + falseTotal + "\n" +
                "Total 'no value' data rows in b = " + noValueTotal;

        resultsMap.put(summaryKey, summaryValue);
    }
}
