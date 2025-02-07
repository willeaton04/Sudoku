public class SudokuSolver {

    private static int numBacktracks;
    private static int numChecks;


    public SudokuSolver() {
    }
 
    public static void main(String[] var0) {
   
       int[][] board = new int[][]{{1, 0, 0, 0, 0, 3, 0, 0, 0},
                                  {0, 3, 0, 0, 0, 0, 0, 0, 5},
                                  {0, 0, 4, 0, 0, 0, 0, 0, 0},
                                  {0, 0, 0, 2, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                                  {0, 0, 6, 0, 0, 0, 0, 9, 0}, 
                                  {0, 0, 0, 0, 8, 0, 0, 0, 0}, 
                                  {0, 0, 0, 0, 0, 0, 0, 0, 0}};
       SudokuPuzzle puzzle = new SudokuPuzzle(board);
       solve(puzzle, 0);
    }
 
    private static void solve(SudokuPuzzle puzzle, int cellID) {
        
        int[][] board = puzzle.getBoard();
        int row = cellID / 9;
        int col = cellID % 9;
        
        // Base case - reached end of board
        if (cellID >= 81) {
            numChecks++;
            if (puzzle.isValid()) {
                SudokuIO.displayBoard(puzzle, numChecks, numBacktracks);
                System.exit(0);
            }
            return;
        }
        
        // Skip filled cells
        if (board[row][col] != 0) {
            solve(puzzle, cellID + 1);
            return;
        }
        
        // Try values 1-9
        for (int i = 1; i <= 9; i++) {
            board[row][col] = i;
            numChecks++;
            if (puzzle.isValid()) {
                solve(puzzle, cellID + 1);
            }
        }
        
        // Backtrack
        numBacktracks++;
        board[row][col] = 0;
    }
}
 