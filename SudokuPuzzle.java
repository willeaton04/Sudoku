

import java.util.HashSet;
import java.util.ArrayList;

public class SudokuPuzzle {

    private int[][] board;
    
    // public SudokuPuzzle() {
    //     board = SudokuIO.constructBoard();
    // }
    public SudokuPuzzle(int[][] board) {
        this.board = board;
    }

    public boolean isValid() {
        return checkRows() && checkColumns() && checkCages();
    }

    public int[][] getBoard() {
        return board;
    }

    private boolean checkRows() {

        for (int i = 0; i < 9; i++) {
            HashSet<Integer> values = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (!values.contains(board[i][j])) {
                    if (board[i][j] == 0) {continue;}
                    values.add(board[i][j]);
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean checkColumns() {

        for (int i = 0; i < 9; i++) {
            HashSet<Integer> values = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (!values.contains(board[j][i])) {
                    if (board[j][i] == 0) {continue;}
                    values.add(board[j][i]);
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkCages() {

        // 9 total cages
        ArrayList<int[]> cages = new ArrayList<>();

        int[] cage1 = {board[0][0], board[1][0], board[2][0],
                       board[0][1], board[1][1], board[2][1],
                       board[0][2], board[1][2], board[2][2]};
        cages.add(cage1);
        int[] cage2 = {board[3][0], board[4][0], board[5][0],
                       board[3][1], board[4][1], board[5][1],
                       board[3][2], board[4][2], board[5][2]};
        cages.add(cage2);
        int[] cage3 = {board[6][0], board[7][0], board[8][0],
                        board[6][1], board[7][1], board[8][1],
                        board[6][2], board[7][2], board[8][2]};
        cages.add(cage3);
        int[] cage4 =  {board[0][3], board[1][3], board[2][3],
                        board[0][4], board[1][4], board[2][4],
                        board[0][5], board[1][5], board[2][5]};
        cages.add(cage4);
        int[] cage5 = {board[3][3], board[4][3], board[5][3],
                       board[3][4], board[4][4], board[5][4],
                       board[3][5], board[4][5], board[5][5]};
        cages.add(cage5);
        int[] cage6 =  {board[6][3], board[7][3], board[8][3],
                        board[6][4], board[7][4], board[8][4],
                        board[6][5], board[7][5], board[8][5]};
        cages.add(cage6);
        int[] cage7 =  {board[0][6], board[1][6], board[2][6],
                        board[0][7], board[1][7], board[2][7],
                        board[0][8], board[1][8], board[2][8]};
        cages.add(cage7);
        int[] cage8 =  {board[3][6], board[4][6], board[5][6],
                        board[3][7], board[4][7], board[5][7],
                        board[3][8], board[4][8], board[5][8]};
        cages.add(cage8);
        int[] cage9 =  {board[6][6], board[7][6], board[8][6],
                        board[6][7], board[7][7], board[8][7],
                        board[6][8], board[7][8], board[8][8]}; 
        cages.add(cage9);

        for (int[] cage : cages) {
            HashSet<Integer> values = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                if (!values.contains(cage[i])) {
                    if (cage[i] == 0) {continue;}
                    values.add(cage[i]);
                }
                else {
                    return false;
                }
            }
        }
        return true;
    } 


}