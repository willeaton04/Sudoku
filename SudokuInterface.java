import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuInterface extends JFrame {
    private JTextField[][] cells;
    private JButton solveButton;
    private JButton clearButton;
    private JLabel statusLabel;
    private Color originalTextColor;
    private boolean[][] userEnteredCells;

    public SudokuInterface() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        userEnteredCells = new boolean[9][9];
        originalTextColor = Color.BLACK;
        
        // Create main panel with larger padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));  // Increased from 10,10 to 15,15
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));  // Increased from 10 to 15

        // Create grid panel with thicker borders between 3x3 boxes
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        cells = new JTextField[9][9];

        // Create and add text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = createSudokuCell(i, j);
                gridPanel.add(cells[i][j]);
            }
        }

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        solveButton = new JButton("Solve Puzzle");
        clearButton = new JButton("Clear Board");
        
        solveButton.addActionListener(e -> solvePuzzle());
        clearButton.addActionListener(e -> clearBoard());
        
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        // Create status label
        statusLabel = new JLabel("Enter numbers (1-9) and click Solve Puzzle");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Add components to main panel
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        // Add main panel to frame
        add(mainPanel);

        // Set frame properties
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JTextField createSudokuCell(int row, int col) {
        JTextField cell = new JTextField(2);  // Changed from 1 to 2 for wider cells
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Arial", Font.BOLD, 24));  // Increased font size from 20 to 24
        cell.setPreferredSize(new Dimension(45, 45));  // Added fixed size for cells
        
        // Add borders to separate 3x3 boxes
        int top = row % 3 == 0 ? 2 : 1;
        int left = col % 3 == 0 ? 2 : 1;
        int bottom = row == 8 ? 2 : 1;
        int right = col == 8 ? 2 : 1;
        
        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

        // Add input validation
        cell.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '1' && c <= '9') || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
                if (cell.getText().length() >= 1 && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }
        });

        // Track user-entered cells
        cell.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                userEnteredCells[row][col] = !cell.getText().trim().isEmpty();
            }
        });

        return cell;
    }

    private void solvePuzzle() {
        try {
            int[][] board = new int[9][9];
            
            // Read values from text fields
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String value = cells[i][j].getText().trim();
                    board[i][j] = value.isEmpty() ? 0 : Integer.parseInt(value);
                }
            }

            SudokuPuzzle puzzle = new SudokuPuzzle(board);
            if (!puzzle.isValid()) {
                statusLabel.setText("Invalid puzzle configuration!");
                return;
            }

            SudokuSolver solver = new SudokuSolver();
            boolean solved = solver.solveForGUI(puzzle);

            if (solved) {
                board = puzzle.getBoard();
                updateBoard(board);
                statusLabel.setText(String.format("Puzzle solved! Checks: %d, Backtracks: %d", 
                    solver.getNumChecks(), solver.getNumBacktracks()));
            } else {
                statusLabel.setText("No solution exists for this puzzle!");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid input! Please enter numbers 1-9 only.");
        }
    }

    private void updateBoard(int[][] solution) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!userEnteredCells[i][j]) {
                    cells[i][j].setText(String.valueOf(solution[i][j]));
                    cells[i][j].setForeground(Color.BLUE);
                }
            }
        }
    }

    private void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
                cells[i][j].setForeground(originalTextColor);
                userEnteredCells[i][j] = false;
            }
        }
        statusLabel.setText("Board cleared. Enter numbers and click Solve Puzzle");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuInterface().setVisible(true);
        });
    }
} 