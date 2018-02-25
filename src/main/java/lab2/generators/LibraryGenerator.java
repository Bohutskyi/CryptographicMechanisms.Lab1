package lab2.generators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 7. Генератор «Бібліотекар»
 */
public class LibraryGenerator {

    public void toFile(String source, String destination, int length) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(source));
            FileWriter writer = new FileWriter(destination);
            String temp;
            int count = 0;
            label: while ((temp = reader.readLine()) != null) {
                for (int i = 0, n = temp.length(); i < n; i++) {
                    StringBuilder tmp = new StringBuilder(Integer.toBinaryString(temp.charAt(i)));
                    while (tmp.length() % 4 != 0) {
                        tmp.insert(0, "0");
                    }
                    count += tmp.length();
                    writer.write(tmp.toString());
                    if (count > length) {
                        writer.close();
                        break label;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
