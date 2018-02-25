package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 3. Генератор псевдовипадкових двійкових послідовностей L20
 * Задається рекурентною формулою x(t) = x(t - 3) + x(t - 5) + x(t - 9) + x(t =20)
 */
public class L20Generator {

    protected ArrayList<Integer> list = new ArrayList<>();

    public L20Generator(String startValue) {
        for (int i = 0, n = startValue.length(); i < n; i++) {
            list.add(Character.getNumericValue(startValue.charAt(i)));
        }
    }

    protected int nextIteration() {
        list.add(list.get(0) ^ list.get(11) ^ list.get(15) ^ list.get(17));
        return list.remove(0);
    }

    public void toFile(String fileName, int length) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < length; i++) {
                writer.write(Integer.toString(nextIteration()));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String generate(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(nextIteration());
        }
        return result.toString();
    }

}
