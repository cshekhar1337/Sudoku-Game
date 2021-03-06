package sudokuproject;

import java.awt.*;
import javax.swing.*;
import java.util.logging.Logger;
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
public class puzzleChooser extends javax.swing.JPanel {

    welcome backFrame;
    playField pf;
    int speed = 1000;//in milliseconds
    int running;//start=1,stop=2,next=3
    int difficulty=1;//1=easy,2=medium,3=hard
    boolean inThisWindow = true;//inside this window flag
    puzzleSpawn ps;
    int unit = 30;
    int matrix[][] = {
        {7, 8, 1, 5, 6, 3, 9, 4, 2},
        {2, 3, 4, 8, 7, 9, 1, 5, 6},
        {9, 5, 6, 1, 4, 2, 3, 8, 7},
        {4, 2, 3, 7, 5, 6, 8, 1, 9},
        {6, 7, 5, 9, 8, 1, 4, 2, 3},
        {8, 1, 9, 2, 3, 4, 6, 7, 5},
        {1, 4, 2, 3, 9, 7, 5, 6, 8},
        {3, 6, 8, 4, 2, 5, 7, 9, 1},
        {5, 9, 7, 6, 1, 8, 2, 3, 4}
    };
    Thread runner;
    Graphics2D g2;

    /** Creates new form playField */
    public puzzleChooser(welcome bf) {
        initComponents();        
        backFrame = bf;
        running = 2;
        ps=new puzzleSpawn();
        runner=new Thread(ps,"puzzle Spawn");
        runner.start();
        easyRButton.setEnabled(true);
        backFrame.sud.makePuzzle(matrix, difficulty);
        stopButton.setEnabled(false);
    }

    public void paintComponent(Graphics gt) {
        super.paintComponent(gt);
        g2 = (Graphics2D) gt;
        sudokuOutlay(80, 170);
        sudokuFill(80, 170, matrix);
    }

    class puzzleSpawn implements Runnable {
        
        public void run() {
            int i,j,v;
            while (inThisWindow) {
//                difficultyText.setText("changed");
//                System.out.println("inside while"+ running);
                v=speedSlider.getValue();
                speed=(100-v)*50;
                System.out.println("value="+v);
                if (running==1) {
                    backFrame.sud.makePuzzle(matrix, difficulty);
                    repaint();
                    delay(speed);
                }else if(running==3){
                    backFrame.sud.makePuzzle(matrix, difficulty);
                    running=2;
                    repaint();

                }
            }
        }
    }

    public static void delay(int ms) {//whatever is the value of speed variable ,depending on that ,the delay will be produced

        try {
            //give a very small delay
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException ex) {
            System.out.println("Delay Problems");
            //Logger.getLogger(puzzleChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }

    void sudokuFill(int sx, int sy, int mat[][]) {
        int i, j;
        Font f = new Font("Arial", Font.BOLD, 30);
        g2.setColor(Color.BLACK);
        g2.setFont(f);
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if (mat[i][j] != 0) {
                    g2.drawString(String.format("%d", mat[i][j]), sx + j * unit + unit / 4, sy + (i + 1) * unit - unit / 8);
                }
            }
        }
    }

    void reload(){
      inThisWindow=true;
      speedSlider.setValue(50);
      startButton.setEnabled(true);
      stopButton.setEnabled(false);
      nextButton.setEnabled(true);
      difficulty=1;
      running=2;
      runner=new Thread(ps,"puzzle Spawn");
      runner.start();
      easyRButton.setEnabled(true);
      backFrame.sud.makePuzzle(matrix, difficulty);
      repaint();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        difficultyRGroup = new javax.swing.ButtonGroup();
        difficultyText = new javax.swing.JLabel();
        easyRButton = new javax.swing.JRadioButton();
        mediumRButton = new javax.swing.JRadioButton();
        hardRButton = new javax.swing.JRadioButton();
        difficultySeparator = new javax.swing.JSeparator();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        shuffleText = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        speedText = new javax.swing.JLabel();
        shuffleSeparator = new javax.swing.JSeparator();
        finalSeparator = new javax.swing.JSeparator();
        backButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 204, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 5));

        difficultyText.setText("Difficulty:");

        easyRButton.setBackground(new java.awt.Color(153, 204, 255));
        difficultyRGroup.add(easyRButton);
        easyRButton.setText("Easy");
        easyRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                easyRButtonActionPerformed(evt);
            }
        });

        mediumRButton.setBackground(new java.awt.Color(153, 204, 255));
        difficultyRGroup.add(mediumRButton);
        mediumRButton.setText("Medium");
        mediumRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumRButtonActionPerformed(evt);
            }
        });

        hardRButton.setBackground(new java.awt.Color(153, 204, 255));
        difficultyRGroup.add(hardRButton);
        hardRButton.setText("Hard");
        hardRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardRButtonActionPerformed(evt);
            }
        });

        difficultySeparator.setBackground(new java.awt.Color(0, 0, 204));

        startButton.setText("Start");
        startButton.setToolTipText("Start the Shuffle");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.setToolTipText("Stop the Shuffle");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        nextButton.setText("Next");
        nextButton.setToolTipText("Next Puzzle");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        shuffleText.setText("Shuffle:");
        shuffleText.setToolTipText("Shuffle Controls");

        speedSlider.setBackground(new java.awt.Color(153, 204, 255));
        speedSlider.setPaintTicks(true);

        speedText.setText("Speed:");
        speedText.setToolTipText("Set speed of shuffle");

        shuffleSeparator.setBackground(new java.awt.Color(0, 0, 204));

        finalSeparator.setBackground(new java.awt.Color(0, 0, 204));

        backButton.setText("Back");
        backButton.setToolTipText("Back to Main Menu");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        playButton.setText("Play");
        playButton.setToolTipText("Play this Puzzle");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(difficultyText)
                .addGap(18, 18, 18)
                .addComponent(easyRButton)
                .addGap(18, 18, 18)
                .addComponent(mediumRButton)
                .addGap(18, 18, 18)
                .addComponent(hardRButton)
                .addContainerGap(39, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(playButton)
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shuffleText)
                    .addComponent(speedText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addGap(18, 18, 18)
                        .addComponent(stopButton)
                        .addGap(18, 18, 18)
                        .addComponent(nextButton))
                    .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(shuffleSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(difficultySeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(finalSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(difficultyText)
                    .addComponent(easyRButton)
                    .addComponent(mediumRButton)
                    .addComponent(hardRButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(difficultySeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(startButton)
                        .addComponent(stopButton)
                        .addComponent(nextButton))
                    .addComponent(shuffleText, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(speedText)
                    .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(shuffleSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addComponent(finalSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(playButton))
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        inThisWindow=false;
        running = 2;
        setVisible(false);
        backFrame.setVisible(true);
        backFrame.contentPane.remove(this);
    }//GEN-LAST:event_backButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        // TODO add your handling code here:
        inThisWindow=false;
        setVisible(false);
        pf = new playField(this);
        pf.puzzle=matrix;
        pf.solution=backFrame.sud.grid;
        pf.status=backFrame.sud.status;
        backFrame.contentPane.add(pf);
        pf.setVisible(true);

    }//GEN-LAST:event_playButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        running = 1;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        nextButton.setEnabled(false);
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
        running = 2;
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        nextButton.setEnabled(true);
    }//GEN-LAST:event_stopButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:
        running=3;
    }//GEN-LAST:event_nextButtonActionPerformed

    private void easyRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_easyRButtonActionPerformed
        // TODO add your handling code here:
        difficulty=1;
    }//GEN-LAST:event_easyRButtonActionPerformed

    private void mediumRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediumRButtonActionPerformed
        // TODO add your handling code here:
        difficulty=2;
    }//GEN-LAST:event_mediumRButtonActionPerformed

    private void hardRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardRButtonActionPerformed
        // TODO add your handling code here:
        difficulty=3;
    }//GEN-LAST:event_hardRButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.ButtonGroup difficultyRGroup;
    private javax.swing.JSeparator difficultySeparator;
    private javax.swing.JLabel difficultyText;
    private javax.swing.JRadioButton easyRButton;
    private javax.swing.JSeparator finalSeparator;
    private javax.swing.JRadioButton hardRButton;
    private javax.swing.JRadioButton mediumRButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton playButton;
    private javax.swing.JSeparator shuffleSeparator;
    private javax.swing.JLabel shuffleText;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JLabel speedText;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
