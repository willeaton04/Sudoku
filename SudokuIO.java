import java.util.Scanner;

public class SudokuIO {


    // public static int[][] constructBoard() {
    //     // TODO: impliment using scanner
    // }

    public static void displayBoard(SudokuPuzzle puzzle, int numChecks, int numBacktracks) {
        int[][] board = puzzle.getBoard();

        System.out.println("---Solution---");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(String.format(" %d ", board[i][j]));
            }
            System.out.println();
        }
        System.out.println(String.format("Checks: %d, Backtracks: %d", numChecks, numBacktracks));


    }

    public static int[] positionToIndex(int pos) throws IllegalArgumentException {
        if (pos < 0 || pos > 80) {
            throw new IllegalArgumentException("Position must be between 0 and 80");
        }
        int row = pos / 9;
        int col = pos % 9;
        return new int[]{row, col};
    }





}