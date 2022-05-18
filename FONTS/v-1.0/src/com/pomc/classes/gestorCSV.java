package com.pomc.classes;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

public class gestorCSV {

    public void writeCSV(String location, Vector<Vector<String>> sheet, String tempName) throws IOException {
        File check = new File(location);
        if (check.isDirectory()) {
            location = location + "/" + tempName + ".csv";
        }
        BufferedWriter file = new BufferedWriter(new FileWriter(location));
        for (Vector<String> row : sheet) {
            boolean first = true;
            for (String data : row) {
                if (!first) file.write(',');
                file.write(data);
                first = false;
            }
            file.write('\n');
        }
        file.close();
    }

    public Vector<Vector<String>> readCSV(String location) throws IOException {   //not sure
        Vector<Vector<String>> sheet = new Vector<>();

        BufferedReader file = new BufferedReader(new FileReader(location));
        String row;

        Vector<String> atributs = new Vector<>();
        if ((row = file.readLine()) != null) {
            atributs.addAll(Arrays.asList(row.split(",")));
            sheet.add(new Vector<>(atributs));
        }

        Vector<String> valors = new Vector<>();
        while ((row = file.readLine()) != null) {
            row = row + ' ';
            char[] characters = row.toCharArray();
            boolean quotMark = true;
            boolean betweenQuotMarks = false;

            Vector<Integer> posQuotMarks = new Vector<>();   //cambiar variables
            Vector<Integer> posComa = new Vector<>();

            for (int i = 1; i < characters.length; ++i) {
                if (betweenQuotMarks && quotMark && characters[i - 1] == '\"' && characters[i] == ',') {
                    characters[i - 1] = ' ';
                    posQuotMarks.add(i - 1);
                    betweenQuotMarks = false;
                }

                else if (!betweenQuotMarks && characters[i - 1] == ',' && characters[i] == '\"') {
                    quotMark = true;
                    characters[i] = ' ';
                    posQuotMarks.add(i);
                    betweenQuotMarks = true;
                }

                if (betweenQuotMarks && characters[i - 1] == '\"') {
                    quotMark = !quotMark;
                    posQuotMarks.add(i - 1);
                    characters[i - 1] = '\'';
                }

                if (betweenQuotMarks && characters[i] == ',') {
                    posComa.add(i);
                    characters[i] = ';';
                }
            }

            String actual = String.valueOf(characters);
            String[] temp = actual.split(",");

            valors.addAll(Arrays.asList(temp));

            Vector<String> temporal = new Vector<>();
            Integer index = 0;
            for (String valor : valors) {
                char[] lletres = valor.toCharArray();
                for (int i = 0; i < lletres.length; ++i) {
                    if (posComa.contains(index)) lletres[i] = ',';
                    if (posQuotMarks.contains(index)) lletres[i] = '\"';
                    ++index;
                }
                temporal.add(String.valueOf(lletres).trim());
                ++index;
            }

            sheet.add(temporal);
            valors.clear();
        }

        atributs.clear();
        file.close();
        return sheet;
    }

    public boolean exists(String fileName) {
        return new File("./Data/" + fileName + ".txt").canRead();
    }

    public boolean deleteCSV(String fileName) {
        if (exists(fileName)) {
            return new File("./Data/" + fileName + ".txt").delete();
        } else {
            return false;
        }
    }
}

}
