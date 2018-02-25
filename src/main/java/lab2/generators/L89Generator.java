package lab2.generators;

import java.util.Random;

/**
 * 4. L89 Generator.
 *
 * Same as L20 Generator.
 */
public class L89Generator extends L20Generator {

    public L89Generator(String startValue) {
        super(startValue);
    }

    public L89Generator() {
        Random random = new Random();
        for (int i = 0; i < 89; i++) {
            list.add(random.nextInt(2));
        }
    }

    @Override
    public int getNext() {
        list.add(list.get(0) ^ list.get(51));
        return list.remove(0);
    }

}
