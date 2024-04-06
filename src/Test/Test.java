package Test;

import javax.swing.UIManager;

import View.DangNhap;
public class Test {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new DangNhap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// ádjjfhsjdjd
}
