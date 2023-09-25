package practice;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MyFrame extends JFrame {
    
    GridBagConstraints gbc;

    JPanel panel1, panel2;
    JTextField tf;


    public void run() {
        setWindow();
        setGrigBagConstraints();
        setPanel();
        setVisible(true);                          //창 표시 유무 설정
    }

    void setWindow() {
        setSize(400, 600);             //창 크기 설정
        setTitle("Java Calculator");          //창 이름 설정
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //X버튼 클릭 시 종료 방법 설정
        setLocationRelativeTo(null);              //창 표시 위치 설정
        setLayout(new GridBagLayout());             //레이아웃 설정
    }

    void setGrigBagConstraints() {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
    }

    void setPanel() {
        //상단 TextField 추가
        initPanel();
        initTextField();
        panel1.add(tf);
        
        //하단 버튼 추가
        setPanel2Button();
        
        //레이아웃 지정
        setLayout();
    }

    void initPanel() {
        panel1 = new JPanel(new GridLayout(1, 0));
        panel2 = new JPanel(new GridLayout(5, 4, 10, 10));    
    }

    void setPanel2Button() {
        String[] buttonStr = {
            " % ", " / ", "DEL", "AC", 
            "7", "8", "9", " * ", 
            "4", "5", "6", " - ", 
            "1", "2", "3", " + ", 
            "", "0", ".", "="
        };
        JButton[] buttonList = new JButton[buttonStr.length];

        for(int i = 0; i < buttonStr.length; i++) {
            buttonList[i] = new JButton(buttonStr[i]);

            setButton(buttonList[i], i, buttonStr);
            setButtonColor(buttonList[i], i);
            panel2.add(buttonList[i]);
        }
    }

    void setButton(JButton btn, int i, String[] buttonStr) {
        btn.addActionListener(new BtnActionListener());
        btn.setFont(new Font("Sans-serif", Font.PLAIN, 15));
    }

    void setButtonColor(JButton btn, int i) {
        if(i == 2 || i == 3) {
            setRed(btn);
            return;
        }
        if(i == 19) {
            setOrange(btn);
            return;
        }
        if(0 <= i && i <= 3 || i % 4 == 3) {
            setGreen(btn);
            return;
        }
        setYellow(btn);
    }

    void setRed(JButton btn) {
        btn.setBackground(new Color(255,111,105));
    }

    void setOrange(JButton btn) {
        btn.setBackground(new Color(255,204,92));
    }

    void setYellow(JButton btn) {
        btn.setBackground(new Color(255,238,173));
    }

    void setGreen(JButton btn) {
        btn.setBackground(new Color(150,206,180));
    }

    void initTextField() {
        tf = new JTextField(30);
        tf.setEditable(false);               //TextField 사용 방지 (setEnabled()와 다름)
        tf.setFont(new Font("Sans-serif", Font.BOLD, 50));
    }

    void setLayout() {
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

    class BtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String op = e.getActionCommand();
            setActionEvent(e, op);

        }

        void setActionEvent(ActionEvent e, String op) {
            if(op.equals("AC")) {
                tf.setText("");
                return;
            }
            
            if(op.equals("DEL")) {
                String str = tf.getText();
                if(!str.isEmpty()) 
                    tf.setText(str.substring(0, str.length() - 1));
                return;
            }
            
            if(op.equals("=")) {
                tf.setText(Calculator.getResult(tf.getText()));
                return;
            }

            tf.setText(tf.getText() + e.getActionCommand());
        }
    }
}