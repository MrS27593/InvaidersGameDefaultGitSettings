package SpaceInvadersGame.pres;
import SpaceInvadersGame.game.Board;
import SpaceInvadersGame.game.GameThread;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class GameFrame extends JFrame
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(GameFrame::new);
    }
    private static int width = 800;
    private static int height = 600;

    public GameFrame()
    {
        setTitle("Projekt02 - Autoslalom");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board();
        GameThread gameThread = new GameThread(board);
        board.add(gameThread);

        Border leftRightBorder = BorderFactory.createMatteBorder(0, 25, 0, 25, Color.BLACK);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        GameTable gameTable = new GameTable(board);
        gameTable.setBorder(leftRightBorder);
        gameTable.setDefaultRenderer(Object.class, centerRenderer);

        SevenSegmentDigit units = new SevenSegmentDigit(SevenSegmentType.Units);
        SevenSegmentDigit tens = new SevenSegmentDigit(SevenSegmentType.Tens);
        SevenSegmentDigit hundreds = new SevenSegmentDigit(SevenSegmentType.Hundreds);

        gameThread.addEventListener(units);
        units.addEventListener(tens);
        tens.addEventListener(hundreds);
        hundreds.addEventListener(gameThread);

        JPanel displayPanel = new JPanel(new FlowLayout(15));
        displayPanel.add(hundreds);
        displayPanel.add(tens);
        displayPanel.add(units);

        add(displayPanel, BorderLayout.NORTH);

        add(gameTable);
        setVisible(true);

        gameThread.start();
    }
}
