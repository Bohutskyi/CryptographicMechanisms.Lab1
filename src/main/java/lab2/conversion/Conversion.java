package lab2.conversion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Клас створений для розбивання послідовності бітів по 8 та перетворення у байти
 * */

public class Conversion {

    public static void conversion(String source, String destination) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(source));
            FileWriter writer = new FileWriter(destination);
            String temp;
            while ((temp = reader.readLine()) != null) {
                for (int i = 0, n = temp.length(); i <= n - 8; i += 8) {
                    StringBuilder result = new StringBuilder();
                    for (int j = 0; j < 8; j++) {
                        result.append(temp.charAt(i + j));
                    }
                    writer.write(Integer.parseInt(result.toString(), 2) + " ");
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*public static String conversion(String source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, n = source.length(); i <= n - 8; i += 8) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                temp.append(source.charAt(i + j));
            }
            result.append(Integer.parseInt(temp.toString(), 2));
        }
        return result.toString();
    }*/

}
