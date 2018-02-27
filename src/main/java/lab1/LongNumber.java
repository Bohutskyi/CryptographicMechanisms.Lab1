package lab1;

import java.util.ArrayList;
import java.util.Random;

public class LongNumber {

    private ArrayList<Integer> array;

    public LongNumber() {}

    /**
     * Constructor
     * Create new LongNumber with specified size
     *
     * @param size specified size
     */
    public LongNumber(int size) {
        this.array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(0);
        }
    }

    /**
     * Constructor
     * Create new LongNumber from specified ArrayList
     *
     * @param array copy numbers from array to new LongNumber
     * */
    public LongNumber(ArrayList<Integer> array) {
        this.array = new ArrayList<>();
        this.array.addAll(array);
    }

    /**
     * Constructor
     * Create new LongNumber coping numbers from specified array
     *
     * @param array copy numbers from array to LongNumber
     */
    public LongNumber(int [] array) {
        this.array = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            this.array.add(i);
        }
    }

    /**
     * Constructor
     * Create new LongNumber coping numbers from specified String array
     *
     * @param array copy numbers from String array to LongNumber
     */
    public LongNumber(String [] array) {
        this.array = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            this.array.add(Integer.parseInt(array[i], 16));
        }
    }

    /**
     * Constructor
     * Create new LongNumber coping numbers from specified String
     *
     * @param s copy numbers from String to LongNumber
     */
    public LongNumber(String s) {
        this(s.split(""));
//        this.array = new ArrayList<>();
//        for (int i = 0; i < s.length(); i++) {
//            this.array.add(Character.getNumericValue(s.charAt(i)));
//        }
    }

    /**
     * Replaces all of the elements from this LongNumber to @value.
     *
     * @param value replaces all elements to this specified value
     */
    public void clear(int value) {
        for (int i = 0; i < this.array.size(); i++) {
            this.array.set(i, value);
        }
    }

    /**
     * Sets random values to this LongNumber.
     */
    public void setRandomValues() {
        Random random = new Random();
        for (int i = 0; i < array.size(); i++) {
            array.set(i, random.nextInt(16));
        }
    }

    @Override
    public String toString() {
        return "LongNumber{" +
                ", array=" + array +
                '}';
    }

    /**
     * Creates copy of this LongNumber.
     *
     * @return copy of current LongNumber
     */
    public LongNumber copy() {
        return new LongNumber(this.array);
    }

    /**
     * Prints this LongNumber.
     */
    public void print() {
        for (Integer element : this.array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    /**
     * Prints current LongNumber in hex.
     */
    public String hex() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.array.size(); i++) {
            stringBuilder.append(Integer.toHexString(array.get(i)));
        }
        String s = stringBuilder.toString();
        return s.toUpperCase();
    }

    /**
     * Gets array size of this LongNumber.
     */
    public int getSize() {
        return this.array.size();
    }

    /**
     * Gets number at specified position.
     *
     * @param i sets specified position to get value
     */
    public int getNumber(int i) {
        return this.array.get(i);
    }

    /**
     * Sets specified value to specified position.
     */
    public void setNumber(int i, int value) {
        this.array.set(i, value);
    }

    /**
     * Gets binary of this LongNumber.
     */
    public String toBinaryString() {
        StringBuilder s = new StringBuilder();
        for (Integer element : array) {
            switch (element) {
                case 0:
                    s.append("0000");
                    break;
                case 1:
                    s.append("0001");
                    break;
                case 2:
                    s.append("0010");
                    break;
                case 3:
                    s.append("0011");
                    break;
                case 4:
                    s.append("0100");
                    break;
                case 5:
                    s.append("0101");
                    break;
                case 6:
                    s.append("0110");
                    break;
                case 7:
                    s.append("0111");
                    break;
                case 8:
                    s.append("1000");
                    break;
                case 9:
                    s.append("1001");
                    break;
                case 10:
                    s.append("1010");
                    break;
                case 11:
                    s.append("1011");
                    break;
                case 12:
                    s.append("1100");
                    break;
                case 13:
                    s.append("1101");
                    break;
                case 14:
                    s.append("1110");
                    break;
                case 15:
                    s.append("1111");
                    break;
            }
        }
        return s.toString();
    }

    /*----------------Main operations----------------*/

    /**
     * Addition operation.
     *
     * @param A first conjunction
     * @param B second conjunction
     * @return LongNumber which is addition of A and B
     */
    public static LongNumber LongAdd(LongNumber A, LongNumber B) {
        LongNumber C;
        int carry = 0;
        if (A.getSize() > B.getSize()) {
            C = new LongNumber(A.getSize());
            int difference = A.getSize() - B.getSize();
            for (int i = B.getSize() - 1; i >= 0; i--) {
                int temp = A.getNumber(difference + i) + B.getNumber(i) + carry;
                C.setNumber(difference + i, temp & 15);
                carry = temp >> 4;
            }
            for (int i = difference - 1; i >= 0; i--) {
                int temp = A.getNumber(i) + carry;
                C.setNumber(i, temp & 15);
                carry = temp >> 4;
            }
        } else {
            C = new LongNumber(B.getSize());
            int difference = B.getSize() - A.getSize();
            for (int i = A.getSize() - 1; i >= 0; i--) {
                int temp = B.getNumber(difference + i) + A.getNumber(i) + carry;
                C.setNumber(difference + i, temp & 15);
                carry = temp >> 4;
            }
            for (int i = difference - 1; i >= 0; i--) {
                int temp = B.getNumber(i) + carry;
                C.setNumber(i, temp & 15);
                carry = temp >> 4;
            }
        }

        while (carry != 0) {
            C.array.add(0, carry & 15);
            carry = carry >> 4;
        }
        C.removeZerosFromBegin();
        return C;
    }

    /**
     * Subtraction operation.
     *
     * @param A first operand
     * @param B subtrahend
     * @return difference A and B if A>B
     */
    public static Pair LongSub(LongNumber A, LongNumber B) throws SubtractionException {
        if (LongNumber.LongCmp(A, B) == -1) {
//            return null;
            throw new SubtractionException(A, B);
        }
        int borrow = 0;
        while (A.getSize() > B.getSize()) {
            B.array.add(0, 0);
        }
        LongNumber C = new LongNumber(A.getSize());
        for (int i = B.getSize() - 1; i >= 0; i--) {
            int temp = A.getNumber(i) - B.getNumber(i) - borrow;
            if (temp >= 0) {
                C.setNumber(i, temp);
                borrow = 0;
            } else {
                C.setNumber(i, 16 + temp);
                borrow = 1;
            }
        }
        while (C.getNumber(0) == 0 && C.getSize() != 1) {
            C.array.remove(0);
        }
        return new Pair(C, borrow);
    }

    /**
     * Comparison operation.
     *
     * @param A first operand to compare
     * @param B second operand to compare
     * @return 0 if A=D, 1 if A>B and -1 if A<B
     */
    public static int LongCmp(LongNumber A, LongNumber B) {

        while (A.getNumber(0) == 0 && A.getSize() != 1) {
            A.array.remove(0);
        }
        while (B.getNumber(0) == 0 && B.getSize() != 1) {
            B.array.remove(0);
        }

        if (A.getSize() > B.getSize()) {
            return 1;
        } else if (A.getSize() < B.getSize()) {
            return -1;
        } else {
            int i = 0;
            while (A.getNumber(i) == B.getNumber(i)) {
                i++;
                if (i == A.getSize()) {
                    break;
                }
            }
            if (i == A.getSize()) {
                return 0;
            } else if (A.getNumber(i) > B.getNumber(i)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * Multiplication operation on one number.
     *
     * @param A LongNumber which should be multiplied
     * @param b int on which LongNumber would be multiplied
     * @return multiplication A and b
     */
    public static LongNumber LongMulOneDigit(LongNumber A, int b) {
        LongNumber C = new LongNumber(A.getSize() + 1);
        int carry = 0;
        for (int i = A.getSize() - 1; i >= 0; i--) {
            int temp = A.getNumber(i) * b + carry;
            C.setNumber(i + 1, temp & 15);
            carry = temp >> 4;
        }
        C.setNumber(0, carry);

        int k = 0;
        while (carry != 0) {
            if (k == 0) {
                C.setNumber(0, carry & 15);
                carry = carry >> 4;
                k++;
            } else {
                C.array.add(0, carry & 15);
                carry = carry >> 4;
            }
        }
        C.removeZerosFromBegin();
        return C;
    }

    /**
     * Multiplication on two LongNumbers.
     *
     * @param A first multiplier
     * @param B second multiplier
     * @return A multiplied B
     */
    public static LongNumber LongMul(LongNumber A, LongNumber B) {
        LongNumber C = new LongNumber(A.getSize() * 2);
        C.clear(0);

        if (B.getSize() < A.getSize()) {
            for (int i = B.getSize() - 1; i >= 0; i--) {
                LongNumber temp = LongMulOneDigit(A, B.getNumber(i));
                LongShiftDigitsToHigh(temp, B.getSize() - i - 1);//A.getSize() - i - 1);
                C = LongAdd(C, temp);
            }
        } else {
            for (int i = A.getSize() - 1; i >= 0; i--) {
                LongNumber temp = LongMulOneDigit(B, A.getNumber(i));
                LongShiftDigitsToHigh(temp, A.getSize() - i - 1);
                C = LongAdd(C, temp);
            }
        }

        while (C.getNumber(0) == 0 && C.array.size() != 1) {
            C.array.remove(0);
        }

        return C;
    }

    /**
     * Shifts digits to high.
     *
     * @param temp LongNumber that should be shifted
     * @param i number of shifts
     */
    public static void LongShiftDigitsToHigh(LongNumber temp, int i) {
        for (int j = 0; j < i; j++) {
            temp.array.add(0);
        }
    }

    /**
     * Division operation.
     *
     * @param A value which would be divided
     * @param B divider
     * @return LongNumberPair which contains A/B and A%B
     */
    //Ділення
    public static LongNumberPair LongDivMod(LongNumber A, LongNumber B) throws SubtractionException {
        StringBuilder s = new StringBuilder(B.toBinaryString());
        while (s.charAt(0) == '0' && s.length() != 1) {
            s.replace(0, 1, "");
        }
        int k = s.toString().length();

        LongNumber R = A.copy();
        LongNumber Q = new LongNumber(1);
        int t;
        LongNumber C;

        while (LongNumber.LongCmp(R, B) != -1) {
            s = null;
            s = new StringBuilder(R.toBinaryString());
            while (s.charAt(0) == '0' && s.length() != 1) {
                s.replace(0, 1, "");
            }
            t = s.toString().length();

            C = LongShiftBitsToHigh(B, t - k);

            if (LongCmp(R, C) == -1) {
                t--;
                C = LongShiftBitsToHigh(B, t - k);
            }

            if (LongCmp(R, C) != -1) {
                R = LongNumber.LongSub(R, C).getLongNumber();
            } else {
                R.clear(0);
            }
            Q = LongNumber.setBit(Q, t - k, '1');
        }

        while (Q.array.get(0) == 0 && Q.array.size() != 1) {
            Q.array.remove(0);
        }
        while (R.array.get(0) == 0 && R.array.size() != 1) {
            R.array.remove(0);
        }
        return new LongNumberPair(Q, R);
    }

    public static LongNumber setBit(LongNumber Q, int n, char i) {
        String[] temp = Q.toBinaryString().trim().split("");
        try {
            StringBuilder s = new StringBuilder(Q.toBinaryString());
            while (s.charAt(0) == 0 && s.length() != 1) {
                s.replace(0, 1, "");
            }
            temp[s.toString().length() - n - 1] = Character.toString(i);
        }
        catch (Exception e) {
            if (n >= 0) {
                String[] r = new String[n + 1];
                for (int j = 0; j < temp.length; j++) {
                    r[n + 1 - temp.length + j] = temp[j];
                }
                for (int j = (n - temp.length); j >= 0; j--) {
                    r[j] = "0";
                }
                r[0] = "1";
                StringBuilder result = new StringBuilder();
                for (int j = 0; j < r.length; j++) {
                    result.append(r[j]);
                }
                return new LongNumber(LongNumber.FromBinaryToHex(result.toString()));
            }
        }

        StringBuilder result = new StringBuilder();
        for (int j = 0; j < temp.length; j++) {
            result.append(temp[j]);
        }
        return new LongNumber(LongNumber.FromBinaryToHex(result.toString()));
    }

    public static LongNumber LongShiftBitsToHigh(LongNumber A, int n) {
        StringBuilder s = new StringBuilder();
        s.append(A.toBinaryString());
        for (int i = 0; i < n; i++) {
            s.append(0);
        }
        while ((s.length() % 4) != 0)  {
            s.reverse();
            s.append(0);
            s.reverse();
        }
        LongNumber temp = new LongNumber(s.length() / 4);
        int k = temp.getSize() - 1;
        for (int i = s.length() - 1; i >= 0; i = i - 4) {
            StringBuilder t = new StringBuilder();
            t.append(s.charAt(i - 3));
            t.append(s.charAt(i - 2));
            t.append(s.charAt(i - 1));
            t.append(s.charAt(i));

            switch (t.toString()) {
                case "0000": temp.setNumber(k, 0);
                    break;
                case "0001": temp.setNumber(k, 1);
                    break;
                case "0010": temp.setNumber(k, 2);
                    break;
                case "0011": temp.setNumber(k, 3);
                    break;
                case "0100": temp.setNumber(k, 4);
                    break;
                case "0101": temp.setNumber(k, 5);
                    break;
                case "0110": temp.setNumber(k, 6);
                    break;
                case "0111": temp.setNumber(k, 7);
                    break;
                case "1000": temp.setNumber(k, 8);
                    break;
                case "1001": temp.setNumber(k, 9);
                    break;
                case "1010" : temp.setNumber(k, 10);
                    break;
                case "1011" : temp.setNumber(k, 11);
                    break;
                case "1100" : temp.setNumber(k, 12);
                    break;
                case "1101" : temp.setNumber(k, 13);
                    break;
                case "1110" : temp.setNumber(k, 14);
                    break;
                case "1111" : temp.setNumber(k, 15);
                    break;
            }
            k--;

        }
        return temp;
    }

    //Піднесення до степеня
    public static LongNumber LongPower1(LongNumber A, LongNumber B) {
//        if (B.hex().equals("0")) {
//            return new LongNumber("1");
//        }
        LongNumber C = new LongNumber(1);
        C.clear(1);
        StringBuilder b = new StringBuilder(B.toBinaryString());
        while (b.charAt(0) == '0' && b.length() != 1) {
            b.replace(0, 1, "");
        }

        for (int i = b.length() - 1; i >= 0; i--) {
            if (b.charAt(i) == '1') {
                C = LongNumber.LongMul(C, A);
            }
            A = LongNumber.LongMul(A, A);

            while (A.getNumber(0) == 0 && A.getSize() != 1) {
                A.array.remove(0);
            }
        }
        return C;
    }



    //--------------------------------------------------------------------Part 2--------------------------------------------------------------------

    //Визначення парності
    public  boolean isEven() {
        if (this.toBinaryString().charAt(this.toBinaryString().length() - 1) == '0') {
            return true;
        } else {
            return false;
        }
    }

    public static String FromBinaryToHex(String source) {
        StringBuilder s = new StringBuilder(source);
        while ((s.length() % 4) != 0)  {
            s.reverse();
            s.append(0);
            s.reverse();
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i = i + 4) {
            StringBuilder t = new StringBuilder();
            t.append(s.charAt(i));
            t.append(s.charAt(i + 1));
            t.append(s.charAt(i + 2));
            t.append(s.charAt(i + 3));

            switch (t.toString()) {
                case "0000": result.append(0);
                    break;
                case "0001": result.append(1);
                    break;
                case "0010": result.append(2);
                    break;
                case "0011": result.append(3);
                    break;
                case "0100": result.append(4);
                    break;
                case "0101": result.append(5);
                    break;
                case "0110": result.append(6);
                    break;
                case "0111": result.append(7);
                    break;
                case "1000": result.append(8);
                    break;
                case "1001": result.append(9);
                    break;
                case "1010" : result.append('A');
                    break;
                case "1011" : result.append('B');
                    break;
                case "1100" : result.append('C');
                    break;
                case "1101" : result.append("D");
                    break;
                case "1110" : result.append('E');
                    break;
                case "1111" : result.append('F');
                    break;
            }
        }
        return result.toString();
    }

    /**
     * Right shift.
     * Also quick divide on 2.
     */
    public static LongNumber RightMove(LongNumber A) {
        String result;
        result = A.toBinaryString().substring(0, A.toBinaryString().length() - 1);
        return new LongNumber(LongNumber.FromBinaryToHex(result));
    }

    /**
     * Left shift.
     * Also quick multiply on 2.
     */
    public static LongNumber LeftMove(LongNumber A) {
        StringBuilder result = new StringBuilder(A.toBinaryString());
        result.append(0);
        return new LongNumber(LongNumber.FromBinaryToHex(result.toString()));
    }

    public static LongNumber LeftMove(LongNumber A, int n) {
        StringBuilder result = new StringBuilder(A.toBinaryString());
        for (int i = 0; i < n; i++) {
            result.append(0);
        }
        LongNumber res =  new LongNumber(LongNumber.FromBinaryToHex(result.toString()));
        return res;
    }

    public boolean isNotNull() {
        for (int i : this.array) {
            if (i != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Greatest common divisor.
     */
    public static LongNumber GCD(LongNumber a, LongNumber b) throws SubtractionException {
//        LongNumber d = new LongNumber("1");
//
//        while (a.isEven() & b.isEven()) {
//            a = LongNumber.RightMove(a);
//            b = LongNumber.RightMove(b);
//            d = LongNumber.LeftMove(d);
//        }
//
//        while (a.isEven()) {
//            a = LongNumber.RightMove(a);
//        }
//
//        while (b.isNotNull()) {
//            while (b.isEven()) {
//                b = LongNumber.RightMove(b);
//            }
//            if (LongNumber.LongCmp(a, b) == 1) {
//                LongNumber temp1 = a.copy();
//                a = b.copy();
//                b = LongNumber.LongSub(temp1, b).getLongNumber();
//            } else {
//                b = LongNumber.LongSub(b, a).getLongNumber();
//            }
//        }
//        d = LongNumber.LongMul(d, a);
//        return d;

        if (!b.isNotNull()) {
            return a;
        }
        if (!a.isNotNull()) {
            return b;
        }
        return GCD(b, LongDivMod(a, b).getSecond());
    }

    /**
     * Least common multiple.
     */
    public static LongNumber LCM(LongNumber a, LongNumber b) throws SubtractionException {
        LongNumber temp = LongNumber.GCD(a, b);
        LongNumber temp2 = LongNumber.LongMul(a ,b);
        temp = LongNumber.LongDivMod(temp2, temp).getFirst();
        return temp;
    }

    //Відкидання останніх n цифр
    public static LongNumber KillLastDigits(LongNumber A, int n) {
        LongNumber temp = A.copy();
        for (int i = 0; i < n; i++) {
            try {
                temp.array.remove(temp.array.size() - 1);
            }
            catch (Exception e) {
                return new LongNumber("0");
            }
        }
        return temp;
    }

//    public static LongNumber getM(LongNumber n) throws SubtractionException {
//        String s = Integer.toHexString(2 * n.getSize());
//        LongNumber m = LongNumber.LongPower1(new LongNumber("10"), new LongNumber(s));
//        m = LongNumber.LongDivMod(m ,n).getFirst();
//        return m;
//    }

    /**
     * Power by module operation.
     *
     * @param A first operand
     * @param B second operand
     * @param N module
     * @return A^B mon N
     * @throws SubtractionException method uses BarrettReduction which may throw this exception
     */
//    public static LongNumber LongModPowerBarrett(LongNumber A, LongNumber B, LongNumber N) throws SubtractionException {
//        LongNumber C = new LongNumber(1);
//        C.clear(1);
//
//        LongNumber mu = LongNumber.getM(N);
//
//        StringBuilder s = new StringBuilder(B.toBinaryString());
//        while (s.charAt(0) == 0 && s.length() != 1) {
//            s.replace(0, 1, "");
//        }
//        int m = s.toString().length();
//
//        for (int i = m - 1; i >= 0; i--) {
//            if (s.charAt(i) == '1') {
//                C = LongNumber.BarrettReduction(LongNumber.LongMul(C, A), N, mu);
//            }
//            A = LongNumber.BarrettReduction(LongNumber.LongMul(A, A), N, mu);
//        }
//        return C;
//    }

    /**
     * Barrett reduction.
     *
     * r = x mod n.
     * |n| = k, |x| = 2*k.
     *
     * @param x first operand
     * @param n module
     * @param m additional factor
     * @throws SubtractionException method uses LongSub method which may throw this exception
     */
//    public static LongNumber BarrettReduction(LongNumber x, LongNumber n, LongNumber m) throws SubtractionException {
//        int k = n.getSize();
//
//        LongNumber q = LongNumber.KillLastDigits(x, k - 1);
//        if (q.array.size() == 0) {
//            q.array.add(0);
//        }
//
//        q = LongNumber.LongMul(q, m);
//        q = LongNumber.KillLastDigits(q, k + 1);
//
//        if (q.array.size() == 0) {
//            q.array.add(0);
//        }
//
//        LongNumber temp = LongNumber.LongMul(q, n);
//        LongNumber r = LongNumber.LongSub(x, temp).getLongNumber();
//        while (LongNumber.LongCmp(r, n) != -1) {
//            r = LongNumber.LongSub(r, n).getLongNumber();
//        }
//        return r;
//    }

    /**
     * Addition by module.
     *
     * @param A first conjunction
     * @param B second conjunction
     * @return (A + B) mod N
     * @throws SubtractionException method uses BarrettReduction which may throw this exception
     */
//    public static LongNumber LongAddMod(LongNumber A, LongNumber B, LongNumber N) throws SubtractionException {
//        LongNumber result = LongNumber.LongAdd(A, B);
//        result = LongNumber.BarrettReduction(result, N, LongNumber.getM(N));
//        return result;
//    }

    /**
     * Subtraction by module.
     *
     * @param A first operand
     * @param B subtrahend
     * @param N module
     * @return (A - B) mod N
     * @throws SubtractionException method uses BarrettReduction which may throw this exception
     */
//    public static LongNumber LongSubMod(LongNumber A, LongNumber B, LongNumber N) throws SubtractionException {
//        if (LongNumber.LongCmp(A, B) == 0) {
//            return new LongNumber("0");
//        }
//        LongNumber result;
//        if (LongNumber.LongCmp(A, B) == 1) {
//            result = LongNumber.LongSub(A, B).getLongNumber();
//            result = LongNumber.BarrettReduction(result, N, LongNumber.getM(N));
//        } else {
//            result = LongNumber.LongSub(B, A).getLongNumber();
//            result = LongNumber.BarrettReduction(result, N, LongNumber.getM(N));
//            result = LongNumber.LongSub(N, result).getLongNumber();
//        }
//
//        if (LongNumber.LongCmp(result, N) == 1) {
//            result = LongNumber.LongSub(result, N).getLongNumber();
//        }
//        return result;
//    }

    /**
     * Multiplication by module.
     *
     * @param A first multiplier
     * @param B second multiplier
     * @param N module
     * @return (A*B) mod N
     * @throws SubtractionException method uses BarrettReduction which may throw this exception
     */
//    public static LongNumber LongMulMod(LongNumber A, LongNumber B, LongNumber N) throws SubtractionException {
//        String s = B.toBinaryString();
//        LongNumber m = LongNumber.getM(N);
//        LongNumber power = new LongNumber("1");
//        LongNumber result = new LongNumber(1);
//        for (int i = s.length() - 1; i >= 0; i--) {
//            if (s.charAt(i) == '1') {
//                LongNumber temp = LongNumber.BarrettReduction(LongNumber.LongMul(A, power), N, m);
//                result = LongNumber.LongAdd(result, temp);
////                result = LongNumber.BarrettReduction(result, N, m);
//            }
//            power = LongNumber.LeftMove(power);
//            while (power.array.get(0) == 0 && power.getSize() != 1) {
//                power.array.remove(0);
//            }
//        }
//
//        result = LongNumber.BarrettReduction(result, N, m);
//        return result;
//    }

    //--------------Karatsuba---------------

    /**
     * Gets first part of LongNumber.
     * Need it for Karatsuba multiplication.
     *
     * @param a LongNumber which first part need to get
     * @return LongNumber which if first part of a
     */
    private static LongNumber getFirstPart(LongNumber a) {
        int k = a.getSize() / 2;
        LongNumber result = new LongNumber(k);
        for (int i = 0; i < k; i++) {
            result.array.set(i, a.array.get(i));
        }
        return result;
    }

    /**
     * Gets second part of LongNumber.
     * Need it for Karatsuba multiplication.
     *
     * @param a LongNumber which second part need to get
     * @return LongNumber which if second part of a
     */
    private static LongNumber getSecondPart(LongNumber a) {
        int k = a.getSize() / 2;
        LongNumber result = new LongNumber(k);
        for (int i = 0; i < k; i++) {
            result.array.set(i, a.array.get(i + k));
        }
        return result;
    }

    /**
     * Karatsuba multiplication.
     *
     * @param X first multiplier
     * @param Y second multiplier
     * @return X * Y
     */
    public static LongNumber Karatsuba(LongNumber X, LongNumber Y) {
        while (X.getSize() > Y.getSize()) {
            Y.array.add(0, 0);
        }

        while (Y.getSize() > X.getSize()) {
            X.array.add(0, 0);
        }

        if (X.getSize() % 2 == 1) {
            X.array.add(0, 0);
            Y.array.add(0, 0);
        }

        int n = X.getSize();

        LongNumber X1 = LongNumber.getFirstPart(X);
        LongNumber X0 = LongNumber.getSecondPart(X);
        LongNumber Y1 = LongNumber.getFirstPart(Y);
        LongNumber Y0 = LongNumber.getSecondPart(Y);

        LongNumber temp1 = LongNumber.LongMul(X1, Y1);
        LongNumber temp3 = LongNumber.LongMul(X0, Y0);
        LongNumber temp2 = LongNumber.LongAdd(LongNumber.LongMul(X0, Y1), LongNumber.LongMul(X1, Y0));
        LongNumber.LongShiftDigitsToHigh(temp1, n);
        LongNumber.LongShiftDigitsToHigh(temp2, n / 2);

        LongNumber result = LongNumber.LongAdd(temp1, temp2);
        result = LongNumber.LongAdd(result, temp3);
        return result;
    }

    /**
     * Schönhage–Strassen algorithm.
     *
     * https://en.wikipedia.org/wiki/Schönhage–Strassen_algorithm
     *
     * @param A first multiplier
     * @param B second multiplier
     * @return a * b
     */
    public static LongNumber SchonhageStrassenMul(LongNumber A, LongNumber B) {
        LongNumber a = A.copy(), b = B.copy();
        int[] linearConvolution = new int[a.getSize() + b.getSize() - 1];
        for (int i = 0, n = linearConvolution.length; i < n; ++i) {
            linearConvolution[i] = 0;
        }

        LongNumber temp = a.copy();
        for (int i = 0, n = b.getSize(); i < n; ++i) {
            a = temp.copy();
            for (int j = 0, m = a.getSize(); j < m; ++j) {
                linearConvolution[i + j] += a.getNumber(a.getSize() - 1) * b.getNumber(b.getSize() - 1);
//                a = quickDivideOn16(a);
                a.array.remove(a.array.size() - 1);
            }
//            b = quickDivideOn16(b);
            b.array.remove(b.array.size() - 1);
        }

        //merge
        LongNumber
                result = new LongNumber("0"),
                base = new LongNumber("1");
        int carry = 0;
        for (int i = 0, n = linearConvolution.length; i < n; ++i) {
            linearConvolution[i] += carry;
            result = LongAdd(result, LongMulOneDigit(base, linearConvolution[i] % 16));
            carry = linearConvolution[i] / 16;
//            base = quickMultiplyOn16(base);
            base.array.add(base.array.size(), 0);
        }
        if (carry != 0) {
            result = LongAdd(result, LongMulOneDigit(base, carry));
        }
//        while (result.array.get(0) == 0) {
//            result.array.remove(0);
//        }
        result.removeZerosFromBegin();

        return result;
    }

    /**
     * Quick divide on 16.
     * Just remove last digit from number.
     *
     * @param source LongNumber which would be divided
     * @return source / 16
     */
    private static LongNumber quickDivideOn16(LongNumber source) {
        LongNumber temp = source.copy();
        temp.array.remove(source.getSize() - 1);
        return temp;
    }

    /**
     * Quick multiply on 16.
     * Just add zero to the end.
     *
     * @param source LongNumber which would be multiplied
     * @return source * 16
     */
    private static LongNumber quickMultiplyOn16(LongNumber source) {
        LongNumber temp = source.copy();
        temp.array.add(source.array.size(), 0);
        return temp;
    }

    private void removeZerosFromBegin() {
        while (this.getSize() > 1 && this.array.get(0) == 0) {
            this.array.remove(0);
        }
    }


}
