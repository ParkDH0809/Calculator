package practice;

import java.math.*;
import java.util.*;

/**
 * 
 * 1. 중위를 후위로 변경
 * 2. 후위 표기식 계산
 * 입력값이 올바르지 않으면 "재작성 요망" 반환
 * 
 */
class Calculator {
    private final static HashMap<String, Integer> symbolPriority = new HashMap<>();

    static {
        symbolPriority.put("+", 0);
        symbolPriority.put("-", 0);
        symbolPriority.put("*", 1);
        symbolPriority.put("/", 1);
        symbolPriority.put("%", 1);
    }
    
    static String getResult(String str) {
        String result;
        try {
            String[] strArr = str.split(" ");
            result = calculatePostFix(changeInFixToPostFix(strArr));
        } catch(Exception e) {
            return "재작성 요망";
        }
        return result;
    }

    public static ArrayList<String> changeInFixToPostFix(String[] strArr) {
        Stack<String> stack = new Stack<>();
        ArrayList<String> postFix = new ArrayList<>();

        for(String s : strArr) {
            if(s.equals("+") || s.equals("-") || s.equals("*") 
            || s.equals("/") || s.equals("%")) {
                makePostfixArrayList(postFix, stack, s);
                continue;
            }
            postFix.add(s);
        }
        
        while(!stack.isEmpty()) {
            postFix.add(stack.pop());
        }
        
        return postFix;
    }

    private static void makePostfixArrayList(ArrayList<String> postFix, Stack<String> stack, String s) {
        if(stack.isEmpty()) {
            stack.push(s);
            return;
        }

        if(symbolPriority.get(stack.peek()) < symbolPriority.get(s)) {
            stack.push(s);
            return;
        }

        while(true) {
            if(stack.isEmpty() || symbolPriority.get(stack.peek()) < symbolPriority.get(s)) {
                stack.push(s);
                break;
            }
            postFix.add(stack.pop());
        }
    }


    public static String calculatePostFix(ArrayList<String> postFix) {
        Stack<String> stack = new Stack<>();
        for(String s : postFix) {
            if(!(s.equals("+") || s.equals("-") || s.equals("*") 
              || s.equals("/") || s.equals("%"))) {
                stack.add(s);
                continue;
            }
            stack.add(calculateEquation(s, stack.pop(), stack.pop()));
        }
        
        return stack.pop();
    }


    public static String calculateEquation(String symbol, String stackNum1, String stackNum2) {
        BigDecimal num2 = new BigDecimal(stackNum1);
        BigDecimal num1 = new BigDecimal(stackNum2);
        
        if(symbol.equals("+"))
            return (num1.add(num2).toString());

        if(symbol.equals("-"))
            return (num1.subtract(num2).toString());

        if(symbol.equals("*"))
            return (num1.multiply(num2).toString());

        if(symbol.equals("/"))
            return (num1.divide(num2, 4, RoundingMode.HALF_UP).toString());

        return (num1.remainder(num2).toString());
    }
}