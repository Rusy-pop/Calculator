
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static final Map<Character, Integer> romanInt = Map.of('I',1,'V', 5, 'X', 10);
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputString = reader.readLine();


        try {
            System.out.println(calc(inputString));
        } catch (Exception e) {
            System.out.println("throws Exception");
        }
    }

    public static String calc (String expression) throws Exception {

        String [] arr = parser(expression);
        if (arr.length > 3){
            throw new Exception();
        }

        String first = arr[0];
        String expr = arr[1];
        String second = arr[2];

        int a;
        int b;

        boolean romanCalc = false;

        if (isRoman(first) && isRoman(second)) {
            a = convertRomeToArab(first);
            b = convertRomeToArab(second);
            romanCalc = true;
        } else {
            a = Integer.parseInt(first);
            b = Integer.parseInt(second);
        }

        if ((a < 1 || a > 10) && (b < 1 || b > 10)){
            throw new Exception();
        }
        int res = 0;

        System.out.printf("a = %s, b = %s\n", a, b);

        switch (expr) {
            case "+" -> res = a + b;
            case "-" -> res = a - b;
            case "*" -> res = a * b;
            case "/" -> res = a / b;
        }

        String resultString;
        if (romanCalc){
            if (res < 1) throw new Exception();
            resultString = convertArabToRome(res);
        } else resultString = String.valueOf(res);
        return resultString;
    }

    static String[] parser (String expr){
        String[] arr = expr.split(" ");
        return arr;
    }

    static boolean isRoman (String s){
        boolean isRoman = s.contains("I") || s.contains("V") || s.contains("X");
        return isRoman;
    }

    static int convertRomeToArab (String input) throws Exception {
        char[] chars = input.toCharArray();

        System.out.println(chars[0]);

        int res = romanInt.get(chars[0]);
        int prev;
        int equalsSimbolCount = 1;

        if (chars.length > 1) {
            for (int i = 1; i < chars.length; i++) {
                int next = romanInt.get(chars[i]);
                prev = romanInt.get(chars[i - 1]);

                if (next < prev) {
                    res += next;
                }
                if (next == prev && equalsSimbolCount < 3) {
                    res += next;
                    equalsSimbolCount++;
                } else if (equalsSimbolCount >= 3){
                    throw new Exception();
                }
                if (next > prev) {
                    res -= prev;
                    res += next - prev;
                }
            }
        }
        return res;
    }

    static String convertArabToRome (int arabianVal){
        Map<Integer, String > map = Map.of(1, "I", 2, "II", 3, "III", 4, "IV", 5,"V",
                6, "VI", 7, "VII", 8, "VIII", 9, "IX", 10, "X");


        List<RomanInt> romanIntList = new ArrayList<>();

        Collections.addAll(romanIntList, RomanInt.values());

        romanIntList.sort(Comparator.comparing((RomanInt::getValue)).reversed());

        for (RomanInt ri: romanIntList  ) {
            System.out.println("NAME: " + ri.name() + "| VAL: " + ri.getValue());
        }

        StringBuilder s = new StringBuilder();
        int i = 0;
        while (arabianVal > 0 && i < romanIntList.size()){
            RomanInt number = romanIntList.get(i);
            if (arabianVal >= number.getValue()){
                s.append(number.name());
                arabianVal -= number.getValue();
            } else {
                i++;
            }
        }
        return s.toString();
    }
}
