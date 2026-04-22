package SpaceInvadersGame.game;
import SpaceInvadersGame.game.events.*;
import SpaceInvadersGame.pres.SevenSegmentDigit;
import SpaceInvadersGame.pres.SevenSegmentType;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class GameThread extends Thread implements EventListener
{
    private Board board;
    private int currentTickInterval = 500; // ta wartość będzie się zmniejszać cyklicznie
    private static final int TICK_PERIOD_CHANGE = 50;

    private List<EventListener> eventListeners = new ArrayList<>();
    private int numberOfTicks = 0;

    public GameThread(Board board)
    {
        this.board = board;
        this.eventListeners.add(board);
    }

    @Override
    public void run()
    {
        TimerTask timerTask = getNewTimerTask();
        Timer timer = new Timer();

        while (true)
        {
            if (numberOfTicks == 10)
            {
                timer.cancel();
                numberOfTicks = 0;

                if (currentTickInterval > TICK_PERIOD_CHANGE)
                    currentTickInterval -= TICK_PERIOD_CHANGE;

                timer = new Timer();
                timerTask = getNewTimerTask();
            }
            if (numberOfTicks == 0)
            {
                ++numberOfTicks;
                timer.scheduleAtFixedRate(timerTask, 0, currentTickInterval);
            }
            System.out.println(currentTickInterval);
        }


    }

    private TimerTask getNewTimerTask()
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                if (board.isGameActive())
                {
                    sendEventToListeners(new TickEvent(this));
                    sendEventToListeners(new PlusOneEvent(this));
                    ++numberOfTicks;
                }
            }
        };
    }

    public void addEventListener(EventListener gameEventListener)
    {
        eventListeners.add(gameEventListener);
    }

    void sendEventToListeners(GameEvent gameEvent)
    {
        for (EventListener eventListener : eventListeners)
            eventListener.onEvent(gameEvent);
    }

    void resetSteps()
    {
        currentTickInterval = 500;
        board.resetBoard();
    }


    @Override
    public void onEvent(GameEvent gameEvent)
    {
        if (gameEvent instanceof ResetEvent && gameEvent.getSource() != this)
        {
            resetSteps();
            sendEventToListeners(new ResetEvent(this));
        }
        else if (gameEvent instanceof PlusOneEvent && gameEvent.getSource() instanceof SevenSegmentDigit
                && ((SevenSegmentDigit)(gameEvent.getSource())).getSevenSegmentType() == SevenSegmentType.Hundreds)
        {
            resetSteps();
        }
    }
}
