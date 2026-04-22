package SpaceInvadersGame.pres;
import SpaceInvadersGame.game.events.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class SevenSegmentDigit extends JPanel implements EventListener
{
    int digitValue = 0;
    SevenSegmentType sevenSegmentType;
    public SevenSegmentDigit(SevenSegmentType sevenSegmentType)
    {
        this.sevenSegmentType = sevenSegmentType;
        setPreferredSize(new Dimension(40, 40));
    }
    private java.util.List<EventListener> eventListeners = new ArrayList<>();
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        switch (digitValue)
        {
            case 0 -> drawZero(g);
            case 1 -> drawOne(g);
            case 2 -> drawTwo(g);
            case 3 -> drawThree(g);
            case 4 -> drawFour(g);
            case 5 -> drawFive(g);
            case 6 -> drawSix(g);
            case 7 -> drawSeven(g);
            case 8 -> drawEight(g);
            case 9 -> drawNine(g);
        }
    }

    void drawZero(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 30, 30, 30);
        g.drawLine(0, 0, 0, 30);
        g.drawLine(30, 0, 30, 30);
    }

    void drawOne(Graphics g)
    {
        g.drawLine(30, 0, 30, 30);
    }

    void drawTwo(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 15, 30, 15);
        g.drawLine(0, 30, 30, 30);
        g.drawLine(0, 15, 0, 30);
        g.drawLine(30, 0, 30, 15);

    }

    void drawThree(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 30, 30, 30);
        g.drawLine(0, 15, 30, 15);
        g.drawLine(30, 0, 30, 30);
    }

    void drawFour(Graphics g)
    {
        g.drawLine(30, 0, 30, 30);
        g.drawLine(0, 15, 30, 15);
        g.drawLine(0, 0, 0, 15);

    }

    void drawFive(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 15, 30, 15);
        g.drawLine(0, 30, 30, 30);
        g.drawLine(30, 15, 30, 30);
        g.drawLine(0, 0, 0, 15);

    }

    void drawSix(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 30, 30, 30);
        g.drawLine(0, 15, 30, 15);
        g.drawLine(0, 0, 0, 30);
        g.drawLine(30, 15, 30, 30);
    }

    void drawSeven(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 30, 30, 0);
    }

    void drawEight(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 30, 30, 30);
        g.drawLine(0, 15, 30, 15);
        g.drawLine(0, 0, 0, 30);
        g.drawLine(30, 0, 30, 30);
    }

    void drawNine(Graphics g)
    {
        g.drawLine(0, 0, 30, 0);
        g.drawLine(0, 0, 0, 15);
        g.drawLine(0, 30, 30, 30);
        g.drawLine(0, 15, 30, 15);
        g.drawLine(30, 0, 30, 30);
    }


    public int getDigitValue()
    {
        return digitValue;
    }

    @Override
    public void onEvent(GameEvent gameEvent)
    {
        if (gameEvent instanceof PlusOneEvent)
        {
            if (digitValue == 9)
            {
                sendEventToListeners(new PlusOneEvent(this));
                digitValue = 0;
            }
            else
                digitValue++;
        }
        else if (gameEvent instanceof ResetEvent || gameEvent instanceof StartEvent)
        {
            sendEventToListeners(gameEvent);
            digitValue = 0;
        }
        repaint();
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

    public SevenSegmentType getSevenSegmentType()
    {
        return sevenSegmentType;
    }
}


