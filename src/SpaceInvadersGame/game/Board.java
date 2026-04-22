package SpaceInvadersGame.game;
import SpaceInvadersGame.game.events.EventListener;
import SpaceInvadersGame.game.events.GameEvent;
import SpaceInvadersGame.game.events.ResetEvent;
import SpaceInvadersGame.game.events.TickEvent;
import javax.swing.table.AbstractTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends AbstractTableModel implements KeyListener, EventListener
{
    private int board[] = new int[7];
    private boolean isGameActive = false;
    private List<EventListener> eventListeners = new ArrayList<>();
    private Random random = new Random();

    public Board()
    {
        board[0] = 2;
    }

    void moveCarRight()
    {
        if (board[0] < 4 && isGameActive)
            board[0] = board[0] << 1 ;
        fireTableDataChanged();
    }

    void sendEventToListeners(GameEvent gameEvent)
    {
        for (EventListener eventListener : eventListeners)
            eventListener.onEvent(gameEvent);
    }

    void moveCarLeft()
    {
        if (board[0] > 1 && isGameActive)
            board[0] = board[0] >> 1;
        fireTableDataChanged();
    }

    void generateObstacle()
    {
        if (board[6] != 0 )
            board[6] = ~board[6] & random.nextInt(6);
        else
            board[6] = random.nextInt(6);
    }

    void checkColision()
    {
        if ((board[0] & board[1]) == board[0])
            sendEventToListeners(new ResetEvent(this));
    }

    void moveRowLower()
    {
        if (isGameActive)
        {
            checkColision();
            for (int a = 1; a <= 5; a++)
                board[a] = board[a + 1];
            generateObstacle();
            fireTableDataChanged();
        }
    }

    void pauseOrReturnEvent()
    {
        isGameActive = !isGameActive;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_A -> moveCarLeft();
            case KeyEvent.VK_D -> moveCarRight();
            case KeyEvent.VK_S -> pauseOrReturnEvent();
        }
    }

    void notificateListeners(GameEvent event)
    {
        for (EventListener eventListener : eventListeners)
            eventListener.onEvent(event);
    }

    public void add(EventListener gameEventListener)
    {
        eventListeners.add(gameEventListener);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public int getRowCount()
    {
        return 7;
    }

    @Override
    public int getColumnCount()
    {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        int columnDigit = (int)Math.pow(2d, columnIndex);

        if (rowIndex == 6)
            if (columnDigit == board[0])
                return 0;

        int currentRow = board[6-rowIndex];
        if ((columnDigit & currentRow) == columnDigit)
            return "|_|";

        return null;
    }

    void resetBoard()
    {
        board[0] = 2;
        for (int a = 1; a < board.length; a++)
            board[a] = 0;
    }

    public boolean isGameActive()
    {
        return isGameActive;
    }

    @Override
    public void onEvent(GameEvent gameEvent)
    {
        if (gameEvent instanceof TickEvent)
            moveRowLower();
    }
}
