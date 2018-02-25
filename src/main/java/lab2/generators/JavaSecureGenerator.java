package lab2.generators;

import java.security.SecureRandom;

/**
 * Maryna, add please some details here:)
 * */
public class JavaSecureGenerator extends Generator {

    private SecureRandom random;

    public JavaSecureGenerator() {
        this.random = new SecureRandom();
    }

    @Override
    public int getNext() {
        return random.nextInt(2);
    }
}
