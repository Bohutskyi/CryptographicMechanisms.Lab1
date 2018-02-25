package lab2.criterion;

import lab2.CONST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * 3. Критерій перевірки однорідності двійкової послідовності
 */
public class Test3 {

    private ArrayList<TreeMap<String, Integer>> mapSet;
    private int r, m, divider;

    public Test3(String fileName, int r) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String temp;
            while ((temp = reader.readLine()) != null) {
                m += temp.split(" ").length;
            }
            reader = new BufferedReader(new FileReader(fileName));
            this.r = r;
            this.divider = m / r;
            this.mapSet = new ArrayList<>();


            int count1 = 0, count2 = 0;
            while ((temp = reader.readLine()) != null) {
                String[] t = temp.split(" ");
                for (int i = 0, n = t.length; i < n; i++) {
                    if (count2 == 0) {
                        mapSet.add(new TreeMap<>());
                    }
                    if (mapSet.get(count1).containsKey(t[i])) {
                        mapSet.get(count1).put(t[i], mapSet.get(count1).get(t[i]) + 1);
                    } else {
                        mapSet.get(count1).put(t[i], 1);
                    }

                    count2++;
                    if (count2 == divider) {
                        count2 = 0;
                        count1++;
                    }
                    if (count1 == r) {
                        break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void check() {
        double x = 0.;
        int n = 0;
        for (TreeMap<String, Integer> map : mapSet) {
            for (Map.Entry<String, Integer> pair : map.entrySet()) {
                n += pair.getValue();
                x += (Math.pow(pair.getValue(), 2) / (sumOnAllMaps(pair.getKey()) * divider));
            }
        }
        x -= 1;
        x *= n;
        System.out.println("x = " + x);
        compare(x, calculateQuantile(CONST.TEST_THREE_QUANTILE_FOR_ONE));
        compare(x, calculateQuantile(CONST.TEST_THREE_QUANTILE_FOR_TWO));
        compare(x, calculateQuantile(CONST.TEST_THREE_QUANTILE_FOR_THREE));

    }

    private int sumOnAllMaps(String i) {
        int result = 0;
        for (TreeMap<String, Integer> map : mapSet) {
            if (map.containsKey(i))
                result += map.get(i);
        }
        return result;
    }

    private double calculateQuantile(double d) {
        return  (Math.sqrt(2 * 255 * (r - 1)) * d) + (255 * (r - 1));
    }

    private void compare(double x, double c) {
        if (x <= c) {
            System.out.println(x + " < " + c);
            System.out.println("Nice and cool");
        } else {
            System.out.println(x + " > " + c);
            System.out.println("Fuck");
        }
    }

}
