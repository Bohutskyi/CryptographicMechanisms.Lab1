package lab2.generators;

import java.util.ArrayList;
import java.util.Random;

/**
 * 3. L20 Generator.
 *
 * It is given by the recurrent formula: x(t) = x(t - 3) + x(t - 5) + x(t - 9) + x(t =20).
 * First 20 elements is chosen randomly and not all are equivalent zero.
 *
 * L20 generates sequence with the maximum possible period (M-sequence) - (2^20 - 1).
 */
public class L20Generator extends Generator {

    protected ArrayList<Integer> list = new ArrayList<>();

    /**
     * Constructor that takes String as a start value. String must contain only "0" and "1" and has size = 20.
     *
     * @param startValue String which contains stat values for generator.
     */
    public L20Generator(String startValue) {
        for (int i = 0, n = startValue.length(); i < n; i++) {
            list.add(Character.getNumericValue(startValue.charAt(i)));
        }
    }

    public L20Generator() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            list.add(random.nextInt(2));
        }
    }

    @Override
    public int getNext() {
        list.add(list.get(0) ^ list.get(11) ^ list.get(15) ^ list.get(17));
        return list.remove(0);
    }

//    public String generate(int length) {
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            result.append(nextIteration());
//        }
//        return result.toString();
//    }

}
