package practice;

import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame {
    
    Container c;
    GridBagConstraints gbc;

    JPanel panel1, panel2;
    JTextField tf;
    String buttonStr[] = {"%", "/", "DEL", "AC", "7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "+/-", "0", ".", "="};
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
        panel1.add(tf);
        
        //하단 버튼 추가
        panel2 = new JPanel(new GridLayout(5, 4));
        for(int i = 0; i < buttonStr.length; i++) {
            buttonList[i] = new JButton(buttonStr[i]);
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