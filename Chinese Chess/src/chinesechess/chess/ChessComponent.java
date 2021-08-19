package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

public abstract class ChessComponent extends JComponent {
    public final static Dimension CHESS_SIZE = new Dimension(40, 40);
    public final static Color CHESS_COLOR = new Color(254, 218, 164);
public final static Color STEP_COLOR=new Color(10,240,100,80);
    public static List<ChessListener> listenerList = new ArrayList<>();

    private ChessboardPoint chessboardPoint;
    private ChessColor chessColor;
    private boolean selected;
    private boolean ispossishow;

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(CHESS_SIZE);

        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isPossishow(){return ispossishow; }

    public void setPossishow(boolean possishow){
        this.ispossishow=possishow;
    }

    public void swapLocation(ChessComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            for (ChessListener listener : listenerList) {
                listener.onClick(this);
            }
        }
    }

    public abstract boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination);

    public static boolean registerListener(ChessListener listener) {
        return listenerList.add(listener);
    }

    public static boolean unregisterListener(ChessListener listener) {
        return listenerList.remove(listener);
    }

    public abstract boolean canMoveTo2(ChessComponent[][] chessboard, ChessboardPoint chessboardPoint);
}
