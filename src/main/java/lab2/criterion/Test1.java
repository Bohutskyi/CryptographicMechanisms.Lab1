package lab2.criterion;

import lab2.CONST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 1. Критерій перевірки рівноімовірності знаків
 */
public class Test1 {

    private TreeMap<Integer, Integer> map = new TreeMap<>();

    public Test1(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String result;
            while ((result = reader.readLine()) != null) {
                for (String s : result.split(" ")) {
                    int temp = Integer.parseInt(s);
                    if (map.containsKey(temp)) {
                        map.put(temp, map.get(temp) + 1);
                    } else {
                        map.put(temp, 1);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void check() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
            sum += pair.getValue();
        }
        double n = 1. * sum / 256;
        double x = 0.;
        for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
            x += Math.pow(pair.getValue() - n, 2) / n;
        }
        System.out.println("x = " + x);
        if (x < CONST.TEST_ONE_QUANTILE_ONE) {
            System.out.println(x + " < " + CONST.TEST_ONE_QUANTILE_ONE);
            System.out.println("Nice and cool");
        } else {
            System.out.println(x + " > " + CONST.TEST_ONE_QUANTILE_ONE);
            System.out.println("Fuck");
        }
        if (x < CONST.TEST_ONE_QUANTILE_TWO) {
            System.out.println(x + " < " + CONST.TEST_ONE_QUANTILE_TWO);
            System.out.println("Nice and cool");
        } else {
            System.out.println(x + " > " + CONST.TEST_ONE_QUANTILE_TWO);
            System.out.println("Fuck");
        }
        if (x < CONST.TEST_ONE_QUANTILE_THREE) {
            System.out.println(x + " < " + CONST.TEST_ONE_QUANTILE_THREE);
            System.out.println("Nice and cool");
        } else {
            System.out.println(x + " > " + CONST.TEST_ONE_QUANTILE_THREE);
            System.out.println("Fuck");
        }
    }

}
