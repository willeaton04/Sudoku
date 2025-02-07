import javax.swing.SwingUtilities;

public class SudokuSolver {

    private static int numBacktracks;
    private static int numChecks;

    public SudokuSolver() {
        numBacktracks = 0;
        numChecks = 0;
    }

    public static void main(String[] var0) {
        // Launch the GUI interface
        SwingUtilities.invokeLater(() -> {
            new SudokuInterface().setVisible(true);
        });
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

    public boolean solveForGUI(SudokuPuzzle puzzle) {
        return solveHelper(puzzle, 0);
    }

    private boolean solveHelper(SudokuPuzzle puzzle, int cellID) {
        int[][] board = puzzle.getBoard();
        int row = cellID / 9;
        int col = cellID % 9;
        
        // Base case - reached end of board
        if (cellID >= 81) {
            numChecks++;
            if (puzzle.isValid()) {
                return true;
            }
            return false;
        }
        
        // Skip filled cells
        if (board[row][col] != 0) {
            return solveHelper(puzzle, cellID + 1);
        }
        
        // Try values 1-9
        for (int i = 1; i <= 9; i++) {
            board[row][col] = i;
            numChecks++;
            if (puzzle.isValid()) {
                if (solveHelper(puzzle, cellID + 1)) {
                    return true;
                }
            }
        }
        
        // Backtrack
        numBacktracks++;
        board[row][col] = 0;
        return false;
    }

    public int getNumChecks() {
        return numChecks;
    }

    public int getNumBacktracks() {
        return numBacktracks;
    }
}
 