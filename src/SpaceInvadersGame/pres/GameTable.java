package SpaceInvadersGame.pres;
import SpaceInvadersGame.game.Board;
import javax.swing.*;
public class GameTable extends JTable
{
    private Board board;
    public GameTable(Board board)
    {
        this.board = board;
        setRowHeight(60);
        setModel(board);
        addKeyListener(board);
    }


}
