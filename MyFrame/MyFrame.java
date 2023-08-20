package practice;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        setSize(400, 600);              //창 크기 설정
        setTitle("Java Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);     //X버튼 클릭 시 종료 방법 설정
        setLocationRelativeTo(null);               //창 표시 위치 설정
        setVisible(true);                          //창 표시 유무 설정
    }

    public static void main(String[] args) {
        new MyFrame();
    }    
}