package dartimfont.code;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    private String line;
    private Map<String, Integer> mapRimToArab = new HashMap<>();

    int[] key = {100, 90, 50, 40, 10, 9, 5, 4, 3, 2, 1};
    String[] value = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "III", "II", "I"};

    public Calculator() {
        mapRimToArab.put("I", 1);
        mapRimToArab.put("V", 5);
        mapRimToArab.put("X", 10);
        mapRimToArab.put("L", 50);
        mapRimToArab.put("C", 100);
    }

    public void inputLine() {
        Scanner scanner = new Scanner(System.in);
        line = scanner.nextLine();
    }

    public void run() throws Exception {

        String[] parts = parseLineToParts(line);
        System.out.println(line);

        if (parts.length > 3) {
            throw new Exception("Неверное количество чисел и операций");
        }

        if (isArabic(parts[0]) != isArabic(parts[2])) {
            throw new Exception("Числа должны быть либо арабскими, либо римскими");
        }

        if (isArabic(parts[0])) {

            int first = Integer.parseInt(parts[0]);
            String operand = parts[1];
            int second = Integer.parseInt(parts[2]);

            checkRangeNum(first, "Первое");
            checkRangeNum(second, "Второе");

            int answer = calculate(first, second, operand);
            System.out.println(answer);

        } else {

            checkRimNum(parts[0], "Первое");
            checkRimNum(parts[2], "Второе");

            int first = rimToArab(parts[0]);
            String operand = parts[1];
            int second = rimToArab(parts[2]);

            checkRangeNum(first, "Первое");
            checkRangeNum(second, "Второе");

            int answer = calculate(first, second, operand);
            if (answer >= 1) {
                System.out.println(arabToRim(answer));
            } else {
                throw new Exception("Римское число может быть больше или равно 1");
            }
        }
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void checkRimNum(String rimNum, String name) throws Exception {
        for (int i = 0; i < rimNum.length(); i++) {
            String ch = Character.toString(rimNum.charAt(i));
            if (!mapRimToArab.containsKey(ch)) {
                throw new Exception(name + " число не явл римским");
            }
        }
    }

    public int rimToArab(String numRim) {

        int len = numRim.length();
        String prev = Character.toString(numRim.charAt(len - 1));
        int answer = mapRimToArab.get(prev);
        for (int i = len - 2; i >= 0 ; i--) {
            String curr = Character.toString(numRim.charAt(i));
            if (mapRimToArab.get(prev) <= mapRimToArab.get(curr)) {
                answer += mapRimToArab.get(curr);
            } else {
                answer -= mapRimToArab.get(curr);
            }
            prev = curr;
        }

        return answer;
    }

    public String arabToRim(int numArab) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < key.length; i++) {
            while (numArab / key[i] >= 1) {
                numArab -= key[i];
                sb.append(value[i]);
            }
        }

        return sb.toString();
    }

    public void checkRangeNum(int num, String name) throws Exception {
        if (num < 1 || num > 10) {
            throw new Exception(name + " число должно быть от 1 до 10");
        }
    }

    public int calculate(int first, int second, String operand) throws Exception {
        int answer;
        switch (operand) {
            case "+":
                answer = first + second;
                break;
            case "-":
                answer = first - second;
                break;
            case "*":
                answer = first * second;
                break;
            case "/":
                answer = first / second;
                break;

            default:
                throw new Exception("Неверная операция");
        }
        return answer;
    }

    String[] parseLineToParts(String line) {
        return line.split(" ");
    }

    public boolean isArabic(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
