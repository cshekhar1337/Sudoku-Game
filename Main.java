/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuproject;

import java.awt.*;
import javax.swing.*;
import java.util.Locale;

/**
 *
 * @author Chandra Shekhar
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame j = new JFrame("Sudoku All in One");
        welcome wel;
        wel = new welcome(j);
        wel.setVisible(true);
        j.setVisible(true);
        j.setLocationRelativeTo(null);
        j.setFocusable(true);
        j.setSize(440, 600);
        j.add(wel);
        System.out.println(" x= " + wel.getSize().width + "  y=" + wel.getSize().height);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
