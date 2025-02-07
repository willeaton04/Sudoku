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
        System.out.println("New Sudoku Interface");
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        userEnteredCells = new boolean[9][9];
        originalTextColor = Color.BLACK;
        
        // Create main panel with larger padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

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
        JTextField cell = new JTextField(2);
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Arial", Font.BOLD, 24));
        cell.setPreferredSize(new Dimension(45, 45));
        
        // Add borders to separate 3x3 boxes
        int top = row % 3 == 0 ? 2 : 1;
        int left = col % 3 == 0 ? 2 : 1;
        int bottom = row == 8 ? 2 : 1;
        int right = col == 8 ? 2 : 1;
        
        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

        // Add input validation and immediate feedback
        cell.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validateCell(cell, row, col); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validateCell(cell, row, col); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validateCell(cell, row, col); }
        });

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

    private void validateCell(JTextField cell, int row, int col) {
        String value = cell.getText().trim();
        
        // Reset background if empty
        if (value.isEmpty()) {
            cell.setBackground(Color.WHITE);
            return;
        }

        try {
            int[][] currentBoard = new int[9][9];
            
            // Get current board state
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String cellValue = cells[i][j].getText().trim();
                    currentBoard[i][j] = cellValue.isEmpty() ? 0 : Integer.parseInt(cellValue);
                }
            }

            // Create temporary puzzle to validate
            SudokuPuzzle tempPuzzle = new SudokuPuzzle(currentBoard);
            
            // Check if the current number makes the puzzle invalid
            if (!tempPuzzle.isValid()) {
                cell.setBackground(new Color(255, 200, 200)); // Light red
            } else {
                cell.setBackground(Color.WHITE);
            }
            
        } catch (NumberFormatException e) {
            cell.setBackground(new Color(255, 200, 200)); // Light red
        }
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
                cells[i][j].setBackground(Color.WHITE); // Reset background color
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