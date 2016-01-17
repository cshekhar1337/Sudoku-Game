package sudokuproject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * sudokuSolver.java
 *
 *  */
/**
 *
 * @author Chandra Shekhar
 */
public class sudokuSolver extends javax.swing.JPanel implements KeyListener {

    /** Creates new form sudokuSolver */
    welcome backFrame;
    Graphics2D g2;
    boolean inputTapAllowed;
    boolean inScreen = true;
    int keyCode;
    static int unit = 30;
    int selected=1;
    navigator n;

    public sudokuSolver(welcome bf) {
        initComponents();
        backFrame = bf;
        refresh();
        n=new navigator();
        Thread runner=new Thread(n,"navigator");
        runner.start();
        addKeyListener(this);
    }

    void refresh() {
        int i, j;
        backFrame.sud.howManyLeft = 9;
        for (i = 0; i < 9; i++) {
            backFrame.sud.remaining[i] = i + 1;
            for (j = 0; j < 9; j++) {
                backFrame.sud.grid[i][j] = 0;
                backFrame.sud.status[i][j] = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        sudokuOutlay(80, 80);
//        System.out.println(" " + puzzle[0][0]);
        sudokuFill(80, 80, backFrame.sud.grid);
        requestFocus();//does work
//        setFocusable(true);//doesnt work
    }

    public void keyTyped(KeyEvent e) {
        keyCode = e.getKeyCode();
        System.out.println(" keyTyped" + keyCode);
    }

    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        System.out.println(" key pressed " + e.getKeyCode());
//        inputTapAllowed=false;
    }

    public void keyReleased(KeyEvent e) {
//        keyCode = 0;
        inputTapAllowed = true;
        System.out.println(" key has been released");
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
        g2.setColor(Color.BLUE);
        int r, c;
        r = (selected - 1) / 9;
        c = (selected - 1) % 9;
        g2.drawRect(sx + c * unit, sy + r * unit, unit, unit);
    }

    void sudokuFill(int sx, int sy, int mat[][]) {
        int i, j;
        Font f = new Font("Arial", Font.BOLD, 30);
        g2.setColor(Color.BLACK);
        g2.setFont(f);
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if(backFrame.sud.status[i][j]==2){
                    g2.setColor(Color.RED);
                }else{
                    g2.setColor(Color.BLUE);
                }
                if (mat[i][j] != 0) {
                    g2.drawString(String.format("%d", mat[i][j]), sx + j * unit + unit / 4, sy + (i + 1) * unit - unit / 8);
                }
            }
        }
    }

    class navigator implements Runnable {

        public void run() {
            int r, c;
            int caseMaker;            
            while (inScreen) {
                repaint();
                caseMaker = processInput();
                System.out.println(" working");
                if ((caseMaker >= 0) && (caseMaker <= 9)) {
                    r = (selected - 1) / 9;
                    c = (selected - 1) % 9;
                    backFrame.sud.grid[r][c] = caseMaker;
                    if (caseMaker != 0) {
                        backFrame.sud.status[r][c] = 2;
                    } else {
                        backFrame.sud.status[r][c] = 0;
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

        fillGridText = new javax.swing.JLabel();
        fillSeparator = new javax.swing.JSeparator();
        backButton = new javax.swing.JButton();
        solveButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        clearAllButton = new javax.swing.JButton();
        unsolveButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 204, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 5));

        fillGridText.setText("Fill in the Sudoku grid:");

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        solveButton.setText("Solve");
        solveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveButtonActionPerformed(evt);
            }
        });

        clearAllButton.setText("Clear All");
        clearAllButton.setToolTipText("Clear all the numbers.");
        clearAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllButtonActionPerformed(evt);
            }
        });

        unsolveButton.setText("Unsolve");
        unsolveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unsolveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(unsolveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(solveButton))
                    .addComponent(fillSeparator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(fillGridText, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fillGridText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fillSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(solveButton)
                    .addComponent(backButton)
                    .addComponent(clearAllButton)
                    .addComponent(unsolveButton))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        backFrame.setVisible(true);
        backFrame.contentPane.remove(this);
    }//GEN-LAST:event_backButtonActionPerformed

    private void solveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveButtonActionPerformed
        // TODO add your handling code here:
        //show the solution.
        boolean solvable;        
        //only back button is allowed now.
        solvable=backFrame.sud.makeFitting();
        if(!solvable){
            JOptionPane.showMessageDialog(null, "This is not a valid puzzle.", "Invalid Puzzle", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_solveButtonActionPerformed

    private void clearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllButtonActionPerformed
        // TODO add your handling code here:
        refresh();
        repaint();
    }//GEN-LAST:event_clearAllButtonActionPerformed

    private void unsolveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unsolveButtonActionPerformed
        // TODO add your handling code here:
        int i,j;
        backFrame.sud.howManyLeft=9;
        for(i=0;i<9;i++){
            backFrame.sud.remaining[i]=i+1;
            for(j=0;j<9;j++){
                if(backFrame.sud.status[i][j]!=2){
                    backFrame.sud.status[i][j]=0;
                    backFrame.sud.grid[i][j]=0;
                }
            }
        }
        repaint();
    }//GEN-LAST:event_unsolveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton clearAllButton;
    private javax.swing.JLabel fillGridText;
    private javax.swing.JSeparator fillSeparator;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton solveButton;
    private javax.swing.JButton unsolveButton;
    // End of variables declaration//GEN-END:variables
}
