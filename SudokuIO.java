import java.util.Scanner;

public class SudokuIO {


    // public static int[][] constructBoard() {
    //     // TODO: impliment using scanner
    // }

    public static void displayBoard(SudokuPuzzle puzzle) {
        int[][] board = puzzle.getBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(String.format(" %d ", board[i][j]));
            }
            System.out.println();
        }
    }





}