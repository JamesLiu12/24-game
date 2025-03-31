package math;

import java.util.*;
import java.util.regex.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class ExpressionEvaluator {

    public static Integer eval(String expr) {
        expr = parse(expr);

        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            Object result = engine.eval(expr);
            if (result instanceof Number) {
                double value = ((Number) result).doubleValue();
                int rounded = (int)Math.round(value);
                if (Math.abs(value - rounded) < 1e-6) {
                    return rounded;
                } else {
                    return null;
                }
            }
        } catch (ScriptException e) {
            return null;
        }

        return null;
    }

    public static boolean isAllUsedOnce(String expr, List<Integer> inputNumbers) {
        expr = parse(expr);

        List<Integer> usedNumbers = extractNumbers(expr);

        return sameFrequency(inputNumbers, usedNumbers);
    }

    public static String parse(String expr) {
        return expr.replaceAll("\\bJ\\b", "11")
                .replaceAll("\\bQ\\b", "12")
                .replaceAll("\\bK\\b", "13")
                .replaceAll("\\bA\\b", "1");
    }

    private static List<Integer> extractNumbers(String expr) {
        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = Pattern.compile("(?<!\\w)-?\\d+").matcher(expr);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers;
    }

    private static boolean sameFrequency(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) return false;
        Map<Integer, Integer> countA = countFreq(a);
        Map<Integer, Integer> countB = countFreq(b);
        System.out.println(countA);
        System.out.println(countB);
        return countA.equals(countB);
    }

    private static Map<Integer, Integer> countFreq(List<Integer> list) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : list) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }
        return freq;
    }
}
