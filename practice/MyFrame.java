package practice;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.*;

public class MyFrame extends JFrame {
    
    GridBagConstraints gbc;

    JPanel panel1, panel2;
    JTextField tf;
    String buttonStr[] = {" % ", " / ", "DEL", "AC", "7", "8", "9", " * ", "4", "5", "6", " - ", "1", "2", "3", " + ", "", "0", ".", "="};
    JButton buttonList[] = new JButton[buttonStr.length];

    public void run() {
        setSize(400, 600);              //창 크기 설정
        setTitle("Java Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);     //X버튼 클릭 시 종료 방법 설정
        setLocationRelativeTo(null);               //창 표시 위치 설정

        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        setPanel();
        setVisible(true);                          //창 표시 유무 설정
    }

    void setPanel() {
        //상단 TextField 추가
        panel1 = new JPanel(new GridLayout(1, 0));
        tf = new JTextField(30);
        tf.setEditable(false);               //TextField 사용 방지 (setEnabled()와 다름)
        tf.setFont(new Font("Sans-serif", Font.BOLD, 50));
        panel1.add(tf);
        
        //하단 버튼 추가
        panel2 = new JPanel(new GridLayout(5, 4, 10, 10));
        
        for(int i = 0; i < buttonStr.length; i++) {
            buttonList[i] = new JButton(buttonStr[i]);
            buttonList[i].addActionListener(new BtnActionListener());
            buttonList[i].setFont(new Font("Sans-serif", Font.PLAIN, 15));

            if(i == 2 || i == 3) {
                buttonList[i].setBackground(new Color(255,111,105));
            }
            else if(i == 19) {
                buttonList[i].setBackground(new Color(255,204,92));
            }
            else if(0 <= i && i <= 3 || i % 4 == 3) {
                buttonList[i].setBackground(new Color(150,206,180));
            }
            else 
                buttonList[i].setBackground(new Color(255,238,173));

            panel2.add(buttonList[i]);
        }

        //레이아웃 지정
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        setPanelLayout(panel1, 0, 0, 1, 1);

        gbc.weightx=1.0;
        gbc.weighty=1.0;
        setPanelLayout(panel2, 0, 1, 1, 1);

    }

    class BtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String op = e.getActionCommand();
            if(op.equals("AC")) {
                tf.setText("");
            } else if(op.equals("DEL")) {
                String str = tf.getText();
                if(!str.isEmpty()) 
                    tf.setText(str.substring(0, str.length() - 1));
            } else if(op.equals("=")) {
                tf.setText(getResult(tf.getText()));
            } else {
                tf.setText(tf.getText() + e.getActionCommand());
            }
        }
    }

    String getResult(String str) {
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

    ArrayList<String> changeInFixToPostFix(String[] strArr) {
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

    public String calculatePostFix(ArrayList<String> postFix) {
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

    public String calculateEquation(String symbol, String stackNum1, String stackNum2) {
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

    void setPanelLayout(Component obj, int x, int y, int w, int h) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        add(obj, gbc);
    }
}