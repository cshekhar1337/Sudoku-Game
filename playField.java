package sudokuproject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * playField.java
 *
 * 
 */
/**
 *
 * @author Chandra Shekhar
 */
public class playField extends javax.swing.JPanel implements KeyListener {

    /** Creates new form playField */
    int hour = 0;
    int min = 0;
    int sec = 0;
    boolean gameEnd = false;
    puzzleChooser backFrame;
    int keyCode = -1;
    int[][] puzzle;
    int[][] solution;
    int[][] status;
    int selected = 1;//goes from 1 to 81
    static int unit = 30;
    boolean inputTapAllowed = true;
    Graphics2D g2;
    navigator n;
    counter c;

    public playField(puzzleChooser bf) {
        initComponents();
        backFrame = bf;
        addKeyListener(this);
        n = new navigator();
        c = new counter();
        Thread t = new Thread(n, "navigator");
        Thread ct = new Thread(c, "counter");
        t.start();
        ct.start();
//        setFocusable(true);  
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        sudokuOutlay(80, 80);
//        System.out.println(" " + puzzle[0][0]);
        if (!gameEnd) {
            sudokuFill(80, 80, puzzle);
        } else {
            showSolution(80, 80);
        }
        requestFocus();//does work
//        setFocusable(true);//doesnt work
    }

    public void keyTyped(KeyEvent e) {
        keyCode = e.getKeyCode();
//        System.out.println(" keyTyped" + keyCode);
    }

    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
//        System.out.println(" key pressed " + e.getKeyCode());
//        inputTapAllowed=false;
    }

    public void keyReleased(KeyEvent e) {
//        keyCode = 0;
        inputTapAllowed = true;
//        System.out.println(" key has been released");
    }

    void sudokuOutlay(int sx, int sy) {
        int i;
        BasicStroke thick = new BasicStroke(4);
        BasicStroke thin = new BasicStroke(1);
        g2.setColor(Color.WHITE);
        g2.fillRect(sx, sy, 9 * unit, 9 * unit);
        g2.setColor(Color.BLACK);
        for (i = 0; i < 10; i++) {//first 10 horizontal lines
            if (i % 3 == 0) {
                g2.setStroke(thick);
            } else {
                g2.setStroke(thin);
            }
            g2.drawLine(sx, sy + i * unit, sx + 9 * unit, sy + i * unit);
            g2.drawLine(sx + i * unit, sy, sx + i * unit, sy + 9 * unit);
        }
        if (!gameEnd) {
            g2.setColor(Color.BLUE);
            int r, c;
            r = (selected - 1) / 9;
            c = (selected - 1) % 9;
            g2.drawRect(sx + c * unit, sy + r * unit, unit, unit);
        }
    }

    void sudokuFill(int sx, int sy, int mat[][]) {
        int i, j;
        Font f = new Font("Arial", Font.BOLD, 30);
        g2.setColor(Color.BLACK);
        g2.setFont(f);
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if (status[i][j] == 2) {
                    g2.setColor(Color.BLACK);
                } else if (status[i][j] == 1) {
                    g2.setColor(Color.BLUE);
                }
                if (mat[i][j] != 0) {
                    g2.drawString(String.format("%d", mat[i][j]), sx + j * unit + unit / 4, sy + (i + 1) * unit - unit / 8);
                }
            }
        }
    }

    void showSolution(int sx, int sy) {
        int i, j;
        Font f = new Font("Arial", Font.BOLD, 30);
        g2.setColor(Color.BLACK);
        g2.setFont(f);
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                System.out.println("working");
                if (status[i][j] == 2) {
                    g2.setColor(Color.BLACK);
                } else if (puzzle[i][j] == solution[i][j]) {
                    g2.setColor(Color.GREEN);
                } else {
                    g2.setColor(Color.RED);
                }
                g2.drawString(String.format("%d", solution[i][j]), sx + j * unit + unit / 4, sy + (i + 1) * unit - unit / 8);

            }
        }
    }

    class counter implements Runnable {

        public void run() {
            while (!gameEnd) {
                sec++;
                if (sec >= 60) {
                    min++;
                    sec = 0;
                }
                if (min >= 60) {
                    hour++;
                    min = 0;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("timer problems");
                }
                if (hour == 0) {
                    timerText.setText(String.format("%d:%d", min, sec));
                } else {
                    timerText.setText(String.format("%d:%d:%d", hour, min, sec));
                }
            }
        }
    }

    class navigator implements Runnable {

        public void run() {
            int r, c;
            int caseMaker = -1;
            while (!gameEnd) {
//                if (caseMaker != -1) {
                repaint();
//                }
                caseMaker = processInput();
                if ((caseMaker >= 0) && (caseMaker <= 9)) {
                    r = (selected - 1) / 9;
                    c = (selected - 1) % 9;
                    if (status[r][c] != 2) {//CHANGE!!! STATUS CHECK
                        puzzle[r][c] = caseMaker;
                    }
                } else {
                    switch (caseMaker) {
                        case 10://up
                            if (selected - 9 >= 1) {
                                selected -= 9;
                            } else {
                                selected += 72;
                            }
                            break;
                        case 11://right
                            if (selected + 1 > 81) {
                                selected = 1;
                            } else {
                                selected++;
                            }
                            break;
                        case 12://down
                            if (selected + 9 <= 81) {
                                selected += 9;
                            } else {
                                selected -= 72;
                            }
                            break;
                        case 13://left
                            if (selected - 1 < 1) {
                                selected = 81;
                            } else {
                                selected--;
                            }
                            break;
                    }
                }
            }

        }
    }

    private int processInput() {
        int result = 0;
        if (keyCode >= 48 && keyCode <= 57) {
            result = keyCode - 48;
        } else if (keyCode >= 96 && keyCode <= 105) {
            result = keyCode - 96;
        } else if (keyCode == 38 || keyCode == 87) {//up
            result = 10;
        } else if (keyCode == 39 || keyCode == 68) {//right
            result = 11;
        } else if (keyCode == 40 || keyCode == 83) {//down
            result = 12;
        } else if (keyCode == 37 || keyCode == 65) {//left
            result = 13;
        } else if (keyCode == 8) {//backSpace or 0
            result = 0;
        } else {
            result = -1;//no reponse
        }
        if (!inputTapAllowed) {
            result = -1;
        }
        inputTapAllowed = result != -1 ? false : inputTapAllowed;
        return result;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timeElapsedText = new javax.swing.JLabel();
        timerText = new javax.swing.JLabel();
        timeSeparator = new javax.swing.JSeparator();
        finalSeparator = new javax.swing.JSeparator();
        backButton = new javax.swing.JButton();
        giveUpButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 204, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 5));

        timeElapsedText.setFont(new java.awt.Font("Tahoma", 1, 18));
        timeElapsedText.setText("Time Elapsed:");

        timerText.setFont(new java.awt.Font("Tahoma", 1, 18));
        timerText.setText("0:0");

        timeSeparator.setBackground(new java.awt.Color(0, 0, 204));

        finalSeparator.setBackground(new java.awt.Color(0, 0, 204));

        backButton.setText("Back");
        backButton.setToolTipText("Back to Puzzle Selection.All game progress will be lost.");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        giveUpButton.setText("Give Up!");
        giveUpButton.setToolTipText("Give up and show solution.");
        giveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveUpButtonActionPerformed(evt);
            }
        });

        doneButton.setText("Done");
        doneButton.setToolTipText("Submit the solution.");
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timeElapsedText, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(timerText, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(timeSeparator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(66, 66, 66)
                        .addComponent(giveUpButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(doneButton)
                        .addGap(31, 31, 31))
                    .addComponent(finalSeparator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeElapsedText, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timerText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timeSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                .addComponent(finalSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(doneButton)
                    .addComponent(giveUpButton))
                .addGap(29, 29, 29))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        gameEnd = true;//terminates the threads too
        //stop the timer
        backFrame.setVisible(true);
        backFrame.reload();
        backFrame.backFrame.contentPane.remove(this);
    }//GEN-LAST:event_backButtonActionPerformed

    private void giveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giveUpButtonActionPerformed
        // TODO add your handling code here:
        doneButton.setEnabled(false);
        //show the solution in the matrix
        giveUpButton.setEnabled(false);

        backButton.setToolTipText("Back to Puzzle Selection.");
        doneButton.setToolTipText("Click back to go to the Puzzle Selection.");
        giveUpButton.setToolTipText("Click back to go to the Puzzle Selection.");
        gameEnd = true;//this will also terminate the threads
        requestFocus();
    }//GEN-LAST:event_giveUpButtonActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:        
        int i, j;
        gameEnd = true;
        doneButton.setEnabled(false);
        giveUpButton.setEnabled(false);
        backButton.setToolTipText("Back to Puzzle Selection.");
        doneButton.setToolTipText("Click back to go to the Puzzle Selection.");
        giveUpButton.setToolTipText("Click back to go to the Puzzle Selection.");
        int totalIncompleteCells = 81;
        int correct = 0;
        int incorrect = 0;
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if (status[i][j] == 2) {
                    totalIncompleteCells--;
                } else if (puzzle[i][j] == solution[i][j]) {
                    correct++;
                } else {
                    incorrect++;
                }
            }
        }
        JOptionPane.showMessageDialog(null, String.format("Completed Time: %d:%d:%d\nTotal Incomplete Cells: %d\n "
                + "Correct: %d \n Incorrect: %d ",hour, min, sec, totalIncompleteCells, correct, incorrect));
    }//GEN-LAST:event_doneButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton doneButton;
    private javax.swing.JSeparator finalSeparator;
    private javax.swing.JButton giveUpButton;
    private javax.swing.JLabel timeElapsedText;
    private javax.swing.JSeparator timeSeparator;
    private javax.swing.JLabel timerText;
    // End of variables declaration//GEN-END:variables
}
