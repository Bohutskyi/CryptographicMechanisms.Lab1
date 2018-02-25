package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 4. Генератор псевдовипадкових двійкових послідовностей L89
 */
public class L89Generator extends L20Generator {

    public L89Generator(String startValue) {
        super(startValue);
    }

    protected int nextIteration() {
        list.add(list.get(0) ^ list.get(51));
        return list.remove(0);
    }

}
