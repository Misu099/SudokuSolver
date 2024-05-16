import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI extends JFrame {

    private static final int GRID_SIZE = 9;
    private static final int BOX_SIZE = 3;
    private JTextField[][] textFields = new JTextField[GRID_SIZE][GRID_SIZE];
    private JButton solveButton;
    private JButton resetButton;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JTextField textField = new JTextField(2);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setFont(new Font("Arial", Font.PLAIN, 20));
                textFields[i][j] = textField;
                gridPanel.add(textField);
            }
        }

        JPanel[][] boxPanels = new JPanel[BOX_SIZE][BOX_SIZE];
        for (int i = 0; i < BOX_SIZE; i++) {
            for (int j = 0; j < BOX_SIZE; j++) {
                boxPanels[i][j] = new JPanel(new GridLayout(BOX_SIZE, BOX_SIZE));
                boxPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }

        JPanel sudokuPanel = new JPanel(new GridLayout(BOX_SIZE, BOX_SIZE));
        for (int i = 0; i < BOX_SIZE; i++) {
            for (int j = 0; j < BOX_SIZE; j++) {
                sudokuPanel.add(boxPanels[i][j]);
            }
        }

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int boxRow = i / BOX_SIZE;
                int boxCol = j / BOX_SIZE;
                boxPanels[boxRow][boxCol].add(textFields[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        solveButton = new JButton("Solve");

        //solveButton.addActionListener(e -> solve());

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });

        resetButton = new JButton("Reset");

        // resetButton.addActionListener(e -> reset());

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sudokuPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void solve() {
        int[][] board = getBoardState();
        if (SudokuSolver.solveBoard(board)) {
            updateUI(board);
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists.");
        }
    }

    private void reset() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                textFields[i][j].setText("");
            }
        }
    }

    private void updateUI(int[][] board) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                textFields[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    private int[][] getBoardState() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String value = textFields[i][j].getText();
                if (!value.isEmpty()) {
                    board[i][j] = Integer.parseInt(value);
                }
            }
        }
        return board;
    }
}

