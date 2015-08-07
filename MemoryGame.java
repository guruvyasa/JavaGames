    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.awt.BorderLayout;
    import java.io.IOException;

    public class MemoryGame{
        private JFrame mainPanel  = new JFrame("Memory Game");
        private Board board = null;
        public MemoryGame() throws IOException{
            board = new Board(4,2);
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(board, BorderLayout.CENTER);

            mainPanel.setSize(800,800);
            mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainPanel.pack();
            mainPanel.setVisible(true);
        }
        public static void main(String[] args) throws IOException{

            new MemoryGame();

        }

    }
