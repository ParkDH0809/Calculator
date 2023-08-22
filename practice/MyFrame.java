package practice;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.*;

public class MyFrame extends JFrame {
    
    Container c;
    GridBagConstraints gbc;

    JPanel panel1, panel2;
    JTextField tf;
    String buttonStr[] = {" % ", " / ", "DEL", "AC", "7", "8", "9", " * ", "4", "5", "6", " - ", "1", "2", "3", " + ", "", "0", ".", "="};
    JButton buttonList[] = new JButton[buttonStr.length];

    public MyFrame() {
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
        tf.setFont(new Font("Sans-serif", Font.BOLD, 50));
        panel1.add(tf);
        
        //하단 버튼 추가
        panel2 = new JPanel(new GridLayout(5, 4, 10, 10));
        for(int i = 0; i < buttonStr.length; i++) {
            buttonList[i] = new JButton(buttonStr[i]);
            buttonList[i].addActionListener(new BtnActionListener());
            if(i == 2 || i == 3)
                buttonList[i].setBackground(new Color(255,111,105));
            else if(i == 19)
                buttonList[i].setBackground(new Color(255,204,92));
            else if(0 <= i && i <= 3 || i % 4 == 3)
                buttonList[i].setBackground(new Color(150,206,180));
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
        String[] strArr = str.split(" ");
        String result;
        try {
            BigDecimal sum = new BigDecimal(strArr[0]);
            for(int i = 1; i < strArr.length; i+=2) {
                if(i % 2 == 1) {
                    System.out.println(strArr[i]);
                    if(strArr[i].equals("+"))
                        sum = sum.add(new BigDecimal(strArr[i+1]));
                    else if(strArr[i].equals("-"))
                        sum = sum.subtract(new BigDecimal(strArr[i+1]));
                    else if(strArr[i].equals("*"))
                        sum = sum.multiply(new BigDecimal(strArr[i+1]));
                    else if(strArr[i].equals("/"))
                        sum = sum.divide(new BigDecimal(strArr[i+1]), 4, RoundingMode.HALF_UP);
                    else if(strArr[i].equals("%"))
                        sum = sum.remainder(new BigDecimal(strArr[i+1]));
                    else
                        throw new Exception("Error");
                    
                } else {
                    throw new Exception("Error");
                }
            }
            result = sum.toString();
        } catch (Exception e) {
            result = "올바른 공식을 적어주세요.";
        }
        return result;
    }

    void setPanelLayout(Component obj, int x, int y, int w, int h) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        add(obj, gbc);
    }

    public static void main(String[] args) {
        new MyFrame();
    }    
}