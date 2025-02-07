
public class SudokuSolver {
    public SudokuSolver() {
    }
 
    public static void main(String[] var0) {
       int[][] board = new int[][]{{1, 2, 3, 0, 0, 0, 0, 0, 0},
                                  {4, 5, 6, 0, 0, 0, 0, 0, 0},
                                  {7, 8, 8, 0, 0, 0, 0, 0, 0},
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}};
       SudokuPuzzle puzzle = new SudokuPuzzle(board);
       SudokuIO.displayBoard(puzzle);
       System.out.println("Expected: false");
       System.out.println(puzzle.isValid());
    }
 
    public static void solveSudoku(int[][] board) {

    }
 }