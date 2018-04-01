package lab3;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) {

        String message = "some text";
        String hash = Hashing.sha256().hashString(message, StandardCharsets.UTF_8).toString();
        System.out.println(hash);
        byte[] buffer = message.getBytes();
        buffer = Sha256.getInstance().digest(buffer);
        for (byte b : buffer) {
            System.out.print(Integer.toHexString(b));
        }



    }

}
