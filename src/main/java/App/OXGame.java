/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package App;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author nrnbt
 */
public class OXGame extends javax.swing.JFrame {
    private char[][] board;
    private char humanPlayer;
    private char aiPlayer;
    
    public boolean isGameOver() {
        return isBoardFull() || getWinner() != '-';
    }
    
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public char getWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
                return board[i][0];
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j] != '-') {
                return board[0][j];
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
            return board[0][2];
        }

        return '-';
    }
    
    public List<int[]> getEmptyCells() {
        List<int[]> emptyCells = new ArrayList<int[]>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        return emptyCells;
    }


    private int[] minimax(int depth, boolean isMaximizing) {
        // Base cases: game is over or maximum depth is reached
        char winner = getWinner();
        if (winner == aiPlayer) {
            return new int[]{-1, -1, 10};
        } else if (winner == humanPlayer) {
            return new int[]{-1, -1, -10};
        } else if (depth == 0 || getEmptyCells().isEmpty()) {
            return new int[]{-1, -1, 0};
        }

        // Recursive cases: find the best move
        List<int[]> emptyCells = getEmptyCells();
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            int[] bestMove = new int[]{-1, -1, bestScore};
            for (int[] cell : emptyCells) {
                board[cell[0]][cell[1]] = aiPlayer;
                int[] move = minimax(depth - 1, false);
                board[cell[0]][cell[1]] = '-';
                move[0] = cell[0];
                move[1] = cell[1];
                if (move[2] > bestScore) {
                    bestScore = move[2];
                    bestMove = move;
                }
            }
            return bestMove;
        } else {
            int bestScore = Integer.MAX_VALUE;
            int[] bestMove = new int[]{-1, -1, bestScore};
            for (int[] cell : emptyCells) {
                board[cell[0]][cell[1]] = humanPlayer;
                int[] move = minimax(depth - 1, true);
                board[cell[0]][cell[1]] = '-';
                move[0] = cell[0];
                move[1] = cell[1];
                if (move[2] < bestScore) {
                    bestScore = move[2];
                    bestMove = move;
                }
            }
            return bestMove;
        }
    }
    
    public void aiMove() {
        if(!isGameOver()){
            int[] move = minimax(5, true);
            board[move[0]][move[1]] = aiPlayer;
            if(move[0] == 0 && move[1] == 0){
                hole1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 0 && move[1] == 1){
                hole2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 0 && move[1] == 2){
                hole3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 1 && move[1] == 0){
                hole4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 1 && move[1] == 1){
                hole5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 1 && move[1] == 2){
                hole6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 2 && move[1] == 0){
                hole7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 2 && move[1] == 1){
                hole8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            } else if (move[0] == 2 && move[1] == 2){
                hole9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/o.jpg"))); 
            }
        }
        if(isGameOver()){
            char winner = getWinner();
            if (winner == '-') {
                Integer score = Integer.parseInt(drawScore.getText()) + 1;
                drawScore.setText(score.toString());
                JOptionPane.showMessageDialog(null, "Та тэнцлээ.");
            } else if (winner == humanPlayer) {
                Integer score = Integer.parseInt(playerScore.getText()) + 1;
                playerScore.setText(score.toString());
                JOptionPane.showMessageDialog(null, "Та яллаа.");
            } else if (winner == aiPlayer) { 
                Integer score = Integer.parseInt(computerScore.getText()) + 1;
                computerScore.setText(score.toString());
                JOptionPane.showMessageDialog(null, "Та хожигдлоо.");
            }
        }
    }
    
    private void restart() {
        hole1.setIcon(null);
        hole2.setIcon(null);
        hole3.setIcon(null);
        hole4.setIcon(null);
        hole5.setIcon(null);
        hole6.setIcon(null);
        hole7.setIcon(null);
        hole8.setIcon(null);
        hole9.setIcon(null);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '-';
            }
        }
    }

    public OXGame() {
        board = new char[3][3];
        humanPlayer = 'X';
        aiPlayer = 'O';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        restartButton = new javax.swing.JButton();
        computerScore = new javax.swing.JLabel();
        drawScore = new javax.swing.JLabel();
        playerScore = new javax.swing.JLabel();
        hole1 = new javax.swing.JLabel();
        hole2 = new javax.swing.JLabel();
        hole3 = new javax.swing.JLabel();
        hole4 = new javax.swing.JLabel();
        hole5 = new javax.swing.JLabel();
        hole6 = new javax.swing.JLabel();
        hole7 = new javax.swing.JLabel();
        hole8 = new javax.swing.JLabel();
        hole9 = new javax.swing.JLabel();
        backgroundImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        restartButton.setText("Дахин тоглох");
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });
        mainPanel.add(restartButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 340, 130, -1));

        computerScore.setBackground(new java.awt.Color(204, 204, 204));
        computerScore.setText("0");
        mainPanel.add(computerScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 20, 20));

        drawScore.setText("0");
        mainPanel.add(drawScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 20, 20));

        playerScore.setText("0");
        mainPanel.add(playerScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 20, 20));

        hole1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole1MouseExited(evt);
            }
        });
        mainPanel.add(hole1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 85, 85));

        hole2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole2MouseExited(evt);
            }
        });
        mainPanel.add(hole2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 85, 85));

        hole3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole3MouseExited(evt);
            }
        });
        mainPanel.add(hole3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 85, 85));

        hole4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole4MouseExited(evt);
            }
        });
        mainPanel.add(hole4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 85, 85));

        hole5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole5MouseExited(evt);
            }
        });
        mainPanel.add(hole5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 85, 85));

        hole6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole6MouseExited(evt);
            }
        });
        mainPanel.add(hole6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 85, 85));

        hole7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole7MouseExited(evt);
            }
        });
        mainPanel.add(hole7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 85, 85));

        hole8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole8MouseExited(evt);
            }
        });
        mainPanel.add(hole8, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 85, 85));

        hole9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hole9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hole9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hole9MouseExited(evt);
            }
        });
        mainPanel.add(hole9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 85, 85));

        backgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background.jpg"))); // NOI18N
        mainPanel.add(backgroundImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 470));

        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 585, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        restart();
    }//GEN-LAST:event_restartButtonActionPerformed

    private void hole1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole1MouseClicked
        if(board[0][0] == '-' && !isGameOver()){
            hole1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[0][0] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole1MouseClicked

    private void hole2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole2MouseClicked
        if(board[0][1] == '-' && !isGameOver()){
            hole2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[0][1] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole2MouseClicked

    private void hole3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole3MouseClicked
        if(board[0][2] == '-' && !isGameOver()){
            hole3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[0][2] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole3MouseClicked

    private void hole4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole4MouseClicked
        if(board[1][0] == '-' && !isGameOver()){
            hole4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[1][0] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole4MouseClicked

    private void hole5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole5MouseClicked
        if(board[1][1] == '-' && !isGameOver()){
            hole5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[1][1] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole5MouseClicked

    private void hole6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole6MouseClicked
        if(board[1][2] == '-' && !isGameOver()){
            hole6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[1][2] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole6MouseClicked

    private void hole7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole7MouseClicked
        if(board[2][0] == '-' && !isGameOver()){
            hole7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[2][0] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole7MouseClicked

    private void hole8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole8MouseClicked
        if(board[2][1] == '-' && !isGameOver()){
            hole8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[2][1] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole8MouseClicked

    private void hole9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole9MouseClicked
        if(board[2][2] == '-' && !isGameOver()){
            hole9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/x.jpg"))); 
            board[2][2] = humanPlayer;
            aiMove();
        }
    }//GEN-LAST:event_hole9MouseClicked

    private void hole1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole1MouseEntered
        if(board[0][0] == '-'){
            hole1.setBackground(new Color(230, 230, 230));
            hole1.setOpaque(true);
        }
    }//GEN-LAST:event_hole1MouseEntered

    private void hole1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole1MouseExited
        if(board[0][0] == '-'){
            hole1.setBackground(Color.WHITE);
            hole1.setOpaque(true);
        }
    }//GEN-LAST:event_hole1MouseExited

    private void hole2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole2MouseEntered
        if(board[0][1] == '-'){
            hole2.setBackground(new Color(230, 230, 230));
            hole2.setOpaque(true);
        }
    }//GEN-LAST:event_hole2MouseEntered

    private void hole2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole2MouseExited
        if(board[0][1] == '-'){
            hole2.setBackground(Color.WHITE);
            hole2.setOpaque(true);
        }
    }//GEN-LAST:event_hole2MouseExited

    private void hole3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole3MouseEntered
        if(board[0][2] == '-'){
            hole3.setBackground(new Color(230, 230, 230));
            hole3.setOpaque(true);
        }
    }//GEN-LAST:event_hole3MouseEntered

    private void hole3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole3MouseExited
        if(board[0][2] == '-'){
            hole3.setBackground(Color.WHITE);
            hole3.setOpaque(true);
        }
    }//GEN-LAST:event_hole3MouseExited

    private void hole4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole4MouseEntered
        if(board[1][0] == '-'){
            hole4.setBackground(new Color(230, 230, 230));
            hole4.setOpaque(true);
        }
    }//GEN-LAST:event_hole4MouseEntered

    private void hole4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole4MouseExited
        if(board[1][0] == '-'){
            hole4.setBackground(Color.WHITE);
            hole4.setOpaque(true);
        }
    }//GEN-LAST:event_hole4MouseExited

    private void hole5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole5MouseEntered
        if(board[1][1] == '-'){
            hole5.setBackground(new Color(230, 230, 230));
            hole5.setOpaque(true);
        }
    }//GEN-LAST:event_hole5MouseEntered

    private void hole5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole5MouseExited
        if(board[1][1] == '-'){
            hole5.setBackground(Color.WHITE);
            hole5.setOpaque(true);
        }
    }//GEN-LAST:event_hole5MouseExited

    private void hole6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole6MouseEntered
        if(board[1][2] == '-'){
            hole6.setBackground(new Color(230, 230, 230));
            hole6.setOpaque(true);
        }
    }//GEN-LAST:event_hole6MouseEntered

    private void hole6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole6MouseExited
        if(board[1][2] == '-'){
            hole6.setBackground(Color.WHITE);
            hole6.setOpaque(true);
        }
    }//GEN-LAST:event_hole6MouseExited

    private void hole7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole7MouseEntered
        if(board[2][0] == '-'){
            hole7.setBackground(new Color(230, 230, 230));
            hole7.setOpaque(true);
        }
    }//GEN-LAST:event_hole7MouseEntered

    private void hole7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole7MouseExited
        if(board[2][0] == '-'){
            hole7.setBackground(Color.WHITE);
            hole7.setOpaque(true);
        }
    }//GEN-LAST:event_hole7MouseExited

    private void hole8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole8MouseEntered
        if(board[2][1] == '-'){
            hole8.setBackground(new Color(230, 230, 230));
            hole8.setOpaque(true);
        }
    }//GEN-LAST:event_hole8MouseEntered

    private void hole8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole8MouseExited
        if(board[2][1] == '-'){
            hole8.setBackground(Color.WHITE);
            hole8.setOpaque(true);
        }
    }//GEN-LAST:event_hole8MouseExited

    private void hole9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole9MouseEntered
        if(board[2][2] == '-'){
            hole9.setBackground(new Color(230, 230, 230));
            hole9.setOpaque(true);
        }
    }//GEN-LAST:event_hole9MouseEntered

    private void hole9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hole9MouseExited
        if(board[2][2] == '-'){
            hole9.setBackground(Color.WHITE);
            hole9.setOpaque(true);
        }
    }//GEN-LAST:event_hole9MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OXGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OXGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OXGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OXGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OXGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundImage;
    private javax.swing.JLabel computerScore;
    private javax.swing.JLabel drawScore;
    private javax.swing.JLabel hole1;
    private javax.swing.JLabel hole2;
    private javax.swing.JLabel hole3;
    private javax.swing.JLabel hole4;
    private javax.swing.JLabel hole5;
    private javax.swing.JLabel hole6;
    private javax.swing.JLabel hole7;
    private javax.swing.JLabel hole8;
    private javax.swing.JLabel hole9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel playerScore;
    private javax.swing.JButton restartButton;
    // End of variables declaration//GEN-END:variables
}
