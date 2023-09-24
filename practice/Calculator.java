package practice;

import java.math.*;
import java.util.*;

class Calculator {
    static String getResult(String str) {
        String result;
        try {
            String[] strArr = str.split(" ");

            //1. 중위를 후위로 변경
            ArrayList<String> postFix = changeInFixToPostFix(strArr);
            
            //2. 후위 표기식 계산
            result = calculatePostFix(postFix);
            
        } catch(Exception e) {
            return "재작성 요망";
        }

        return result;
    }

    public static ArrayList<String> changeInFixToPostFix(String[] strArr) {
        HashMap<String, Integer> pri = new HashMap<>();
        pri.put("+", 0);
        pri.put("-", 0);
        pri.put("*", 1);
        pri.put("/", 1);
        pri.put("%", 1);

        Stack<String> stack = new Stack<>();
        ArrayList<String> reverseStrArr = new ArrayList<>();
        for(String s : strArr) {
            if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%")) {
                if(stack.isEmpty())
                    stack.push(s);
                else { 
                    System.out.println("Test: " + pri.get(stack.peek()) + " " + stack.peek());
                    if(pri.get(stack.peek()) < pri.get(s)) 
                        stack.push(s);
                    else {
                        while(true) {
                            if(stack.isEmpty() || pri.get(stack.peek()) < pri.get(s)) {
                                stack.push(s);
                                break;
                            }
                            reverseStrArr.add(stack.pop());
                        }
                    }
                }
            } else {
                reverseStrArr.add(s);
            }

        }
        while(!stack.isEmpty()) {
            reverseStrArr.add(stack.pop());
        }

        return reverseStrArr;
    }

    public static String calculatePostFix(ArrayList<String> postFix) {
        Stack<String> stack = new Stack<>();
        for(String s : postFix) {
            if(!(s.equals("+") 
            || s.equals("-") 
            || s.equals("*") 
            || s.equals("/") 
            || s.equals("%"))) {
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