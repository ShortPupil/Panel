package paneltools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldHintListener implements FocusListener {
    private String mHintText;
    private JTextField mTextField;

    public JTextFieldHintListener(String hintText, JTextField textField) {
        this.mHintText = hintText;
        this.mTextField = textField;
        textField.setForeground(Color.GRAY);
        Font font = new Font("楷体", Font.ROMAN_BASELINE, 15);//创建1个字体实例
        textField.setFont(font);
    }
    @Override
    public void focusGained(FocusEvent e) {
        String temp = mTextField.getText();
        if(temp.equals(mHintText)){
            mTextField.setText("");
            mTextField.setForeground(Color.BLACK);
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        String temp = mTextField.getText();
        if(temp.equals("")){
            mTextField.setForeground(Color.GRAY);
            mTextField.setText(mHintText);
        }
    }
}
