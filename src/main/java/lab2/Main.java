package lab2;

import lab2.conversion.Conversion;
import lab2.criterion.Test1;
import lab2.criterion.Test2;
import lab2.criterion.Test3;
import lab2.generators.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private static TreeMap<String, String> map = new TreeMap<>();

    public static void main(String[] args) {
//        JavaGenerator.toFile("data/1.txt", 1000000);

//        LehmerLowGenerator l = new LehmerLowGenerator();

//        L20Generator l20Generator = new L20Generator("10010110101110101010");
//        l20Generator.toFile("data/3.txt", 1000000);

//        L89Generator.toFile("data/4.txt", 1000000);
//        L89Generator l89Generator = new L89Generator("01010101101010101001001011010110111110000101010010101010100101010101010100101010101001010");
//        l89Generator.toFile("data/4.txt", 1000000);

//        GeffeGenerator geffeGenerator = new GeffeGenerator(1234, 5243432, 16789);
//        geffeGenerator.toFile("data/temp.txt", 1000000);
//        Conversion.conversion("data/temp.txt", "data/temp1.txt");

//        LibraryGenerator.toFile("data/text.txt", "data/7.txt", 1000000);

//        BMGenerator.toFile("data/8.txt", 1000000);
//        BMGenerator.nextIteration();

//        BBSGenerator.toFile("data/9.txt", 1000000);

//        BBSGeneratorByBytes.toFile("data/9.2.txt", 1000000);

//        BMBytesGenerator bmBytesGenerator = new BMBytesGenerator();
//        bmBytesGenerator.toFile("data/8.2.txt", 234758934, 125000);

//        Conversion.conversion("data/9.txt", "data/test/9.txt");
//        SecureRandom random = new SecureRandom();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(random.nextInt());
//        }
//
//        LehmerLowGenerator lehmerLowGenerator = new LehmerLowGenerator(27438234);
//        lehmerLowGenerator.toFile("data/2.1.txt", 125000);


//        LehmerHighGenerator lehmerHighGenerator = new LehmerHighGenerator(8649705);
//        lehmerHighGenerator.toFile("data/2.2.txt", 125000);

//        Test1 test1 = new Test1("data/test/1.txt");
//        test1.check();

//        Test2 test2 = new Test2("data/test/1.txt");
//        test2.check();

//        Test3 test3 = new Test3("data/test/1.txt", 40);
//        test3.check();

//        Conversion.conversion("data/temp/6.txt", "data/test/6.txt");

//        String[] temp = {"1", "2.1", "2.2", "3", "4", "5", "6", "7", "8", "8.2", "9", "9.2"};
//        System.out.println("Test1");
//        for (String s : temp) {
//            System.out.println(s);
//            StringBuilder result = new StringBuilder("data/test/");
//            result.append(s);
//            result.append(".txt");
//            new Test1(result.toString()).check();
//        }

//        BMGenerator wolframGenerator = new BMGenerator(31);
//        GeffeGenerator wolframGenerator = new GeffeGenerator(27, 11, 31);

//        BMGenerator wolframGenerator = new BMGenerator(943251);
//        L20Generator wolframGenerator = new L20Generator("01010111001100101110");
        LibraryGenerator wolframGenerator = new LibraryGenerator();
//        LehmerLowGenerator wolframGenerator = new LehmerLowGenerator(41);
        wolframGenerator.toFile("data/rowling.txt", "data/temp.txt", 1000000);
        Conversion.conversion("data/temp.txt", "data/temp1.txt");

        System.out.println("Test1");
        new Test1("data/temp1.txt").check();
        System.out.println("=============================");
        System.out.println("Test2");
        new Test2("data/temp1.txt").check();
        System.out.println("=============================");
        System.out.println("Test3");
        new Test3("data/temp1.txt", 30).check();
//


    }

}
