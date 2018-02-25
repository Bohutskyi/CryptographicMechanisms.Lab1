package lab2.criterion;

import lab2.CONST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 2. Критерій перевірки незалежності знаків
 * */

public class Test2 {

    private static final int N = 256;

    private int[][] table = new int[N][N];

    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                table[i][j] = 0;
            }
        }
    }

    public Test2(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                String[] t = temp.split(" ");
                for (int i = 0, n = t.length - 1; i < n; i += 2) {
                    table[Integer.parseInt(t[i])][Integer.parseInt(t[i + 1])] += 1;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void check() {
        int sum = 0;
        for (int[] array : table) {
            for (int j : array) {
                sum += j;
            }
        }
        double x = 0.;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int temp;
                if ((temp = sumOnRow(i) * sumOnColumn(j)) != 0){
                    x += (Math.pow(table[i][j], 2) / (temp));
                }
            }
        }
        x -= 1;
        x *= sum;
        System.out.println("x = " + x);
        if (x < CONST.TEST_TWO_QUANTILE_ONE) {
            System.out.println(x + " < " + CONST.TEST_TWO_QUANTILE_ONE);
            System.out.println("Nice and cool");
        } else {
            System.out.println(x + " > " + CONST.TEST_TWO_QUANTILE_ONE);
            System.out.println("Fuck");
        }
        if (x < CONST.TEST_TWO_QUANTILE_TWO) {
            System.out.println(x + " < " + CONST.TEST_TWO_QUANTILE_TWO);
            System.out.println("Nice and cool");
        } else {
            System.out.println(x + " > " + CONST.TEST_TWO_QUANTILE_TWO);
            System.out.println("Fuck");
        }
        if (x < CONST.TEST_TWO_QUANTILE_THREE) {
            System.out.println(x + " < " + CONST.TEST_TWO_QUANTILE_THREE);
            System.out.println("Nice and cool");
        } else {
            System.out.println(x + " > " + CONST.TEST_TWO_QUANTILE_THREE);
            System.out.println("Fuck");
        }
    }

    private int sumOnRow(int row) {
        int result = 0;
        for (int i = 0; i < N; i++) {
            result += table[row][i];
        }
        return result;
    }

    private int sumOnColumn(int column) {
        int result = 0;
        for (int i = 0; i < N; i++) {
            result += table[i][column];
        }
        return result;
    }

}
