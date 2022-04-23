
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

        String [] arr = expression.split(" ");
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

    static boolean isRoman (String s){
        boolean isRoman = s.contains("I") || s.contains("V") || s.contains("X");
        return isRoman;
    }

    static int convertRomeToArab (String input) throws Exception {
        List<RomanInt> romanIntList = new ArrayList<>();

        Collections.addAll(romanIntList, RomanInt.values());

        romanIntList.sort(Comparator.comparing((RomanInt::getValue)).reversed());

        int res = 0;
        int i = 0;
        while (i < romanIntList.size() && res < 4000){
            String romanValue = romanIntList.get(i).name();
            if (input.startsWith(romanValue)){
                res += romanIntList.get(i).getValue();
                input = input.substring(romanValue.length());
            } else i++;
        }

        if (input.length() > 0 || res == 0) throw new Exception();

        return res;
    }

    static String convertArabToRome (int arabianVal){
        List<RomanInt> romanIntList = new ArrayList<>();

        Collections.addAll(romanIntList, RomanInt.values());

        romanIntList.sort(Comparator.comparing((RomanInt::getValue)).reversed());

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
