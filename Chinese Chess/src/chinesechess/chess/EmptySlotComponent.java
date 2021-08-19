package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class EmptySlotComponent extends ChessComponent {
    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location) {
        super(chessboardPoint, location, ChessColor.NONE);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public boolean canMoveTo2(ChessComponent[][] chessboard, ChessboardPoint chessboardPoint) {
        return false;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isPossishow()) {
            g.setColor(STEP_COLOR);
            g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}