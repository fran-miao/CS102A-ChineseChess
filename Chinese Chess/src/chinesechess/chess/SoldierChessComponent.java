package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;
//import storage.chessmoveseq;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent.chessboard;

public class SoldierChessComponent extends ChessComponent {
    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        super(chessboardPoint, location, chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor().equals(ChessColor.RED)) {
            if (source.getX() >= 5) {
                if (destination.getX() + 1 == source.getX() & destination.getY() == source.getY()) {
                    recordStep(source, destination);
                    return true;
                }
            } else {
                if (destination.getX() + 1 == source.getX() & destination.getY() == source.getY()) {
                    recordStep(source, destination);
                    return true;
                }
                if (destination.getY() + 1 == source.getY() & destination.getX() == source.getX()) {
                    recordStep(source, destination);
                    return true;
                }
                if (destination.getY() - 1 == source.getY() & destination.getX() == source.getX()) {
                    recordStep(source, destination);
                    return true;
                }
            }
        } else {
            if (source.getX() <= 4) {
                if (destination.getX() - 1 == source.getX() & destination.getY() == source.getY()) {
                    recordStep(source, destination);
                    return true;
                }
            } else {
                if (destination.getX() - 1 == source.getX() & destination.getY() == source.getY()) {
                    recordStep(source, destination);
                    return true;
                }
                if (destination.getY() + 1 == source.getY() & destination.getX() == source.getX()) {
                    recordStep(source, destination);
                    return true;
                }
                if (destination.getY() - 1 == source.getY() & destination.getX() == source.getX()) {
                    recordStep(source, destination);
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean canMoveTo2(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor().equals(ChessColor.RED)) {
            if (source.getX() >= 5) {
                if (destination.getX() + 1 == source.getX() & destination.getY() == source.getY()) {
                    return true;
                }
            } else {
                if (destination.getX() + 1 == source.getX() & destination.getY() == source.getY()) {
                    return true;
                }
                if (destination.getY() + 1 == source.getY() & destination.getX() == source.getX()) {
                    return true;
                }
                if (destination.getY() - 1 == source.getY() & destination.getX() == source.getX()) {
                    return true;
                }
            }
        } else {
            if (source.getX() <= 4) {
                if (destination.getX() - 1 == source.getX() & destination.getY() == source.getY()) {
                    return true;
                }
            } else {
                if (destination.getX() - 1 == source.getX() & destination.getY() == source.getY()) {
                    return true;
                }
                if (destination.getY() + 1 == source.getY() & destination.getX() == source.getX()) {
                    return true;
                }
                if (destination.getY() - 1 == source.getY() & destination.getX() == source.getX()) {
                    return true;
                }
            }
        }
        return false;
    }



//    public ArrayList<int[]> AllPossibleNextMove() {
//        ChessComponent[][] chessboard = ChessboardComponent.chessboard;
//        ArrayList<int[]> e = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 9; j++) {
//                if (canMoveTo(chessboard, chessboard[i][j].getChessboardPoint())) {
//                    if (!(getChessColor().equals(chessboard[i][j].getChessColor()))) {
//                        e.add(new int[]{i, j});
//                    }
//                }
//            }
//        }
//        return e;
//    }






    public void recordStep(ChessboardPoint source, ChessboardPoint destination) {
        NearestSteps nearestSteps = new NearestSteps(
                new int[]{source.getX(), source.getY(), destination.getX(), destination.getY()},
                getChessColor(), 'S', this);
        StepData.nearestSteps.add(nearestSteps);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isPossishow())g.setColor(STEP_COLOR);
        else g.setColor(CHESS_COLOR);
        g.setFont(new Font("楷体", Font.BOLD, 16));
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(getChessColor().getColor());
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(getChessColor().getColor());
        if (getChessColor().equals(ChessColor.RED))
            g.drawString("兵", 12, 25); // FIXME: Use library to find the correct offset.
        if (getChessColor().equals(ChessColor.BLACK))
            g.drawString("卒", 12, 25);
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//            for (int i = 0; i < AllPossibleNextMove().size(); i++) {
//                System.out.println(Arrays.toString(AllPossibleNextMove().get(i)));
//            }
        }
    }
}
