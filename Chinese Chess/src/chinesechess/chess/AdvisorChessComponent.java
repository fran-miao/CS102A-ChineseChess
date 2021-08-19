package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

//import storage.chessmoveseq;

//import static storage.chessmoveseq.chessmoveseq;

public class AdvisorChessComponent extends ChessComponent {
    public AdvisorChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        super(chessboardPoint, location, chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor().equals(ChessColor.RED)) {
            if ((source.getX() == 9 || source.getX() == 7)
                    && (source.getY() == 3 || source.getY() == 5)
                    && destination.getX() == 8 && destination.getY() == 4) {
                recordStep(source, destination);
                return true;
            }
            if ((source.getX() == 8 && source.getY() == 4)
                    && (destination.getX() == 7
                    && (destination.getY() == 3 || destination.getY() == 5))
                    || (destination.getX() == 9
                    && (destination.getY() == 3 || destination.getY() == 5))) {
                recordStep(source, destination);
                return true;
            }
        } else {
            if ((source.getX() == 0 || source.getX() == 2)
                    && (source.getY() == 3 || source.getY() == 5)
                    && destination.getX() == 1 && destination.getY() == 4) {
                recordStep(source, destination);
                return true;
            }
            if ((source.getX() == 1 && source.getY() == 4)
                    && (destination.getX() == 2
                    && (destination.getY() == 3 || destination.getY() == 5))
                    || (destination.getX() == 0
                    && (destination.getY() == 3 || destination.getY() == 5))) {
                recordStep(source, destination);
                return true;
            }
        }
        return false;
    }

    public boolean canMoveTo2(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor().equals(ChessColor.RED)) {
            if ((source.getX() == 9 || source.getX() == 7)
                    && (source.getY() == 3 || source.getY() == 5)
                    && destination.getX() == 8 && destination.getY() == 4) {
                return true;
            }
            if ((source.getX() == 8 && source.getY() == 4)
                    && (destination.getX() == 7
                    && (destination.getY() == 3 || destination.getY() == 5))
                    || (destination.getX() == 9
                    && (destination.getY() == 3 || destination.getY() == 5))) {
                return true;
            }
        } else {
            if ((source.getX() == 0 || source.getX() == 2)
                    && (source.getY() == 3 || source.getY() == 5)
                    && destination.getX() == 1 && destination.getY() == 4) {
                return true;
            }
            if ((source.getX() == 1 && source.getY() == 4)
                    && (destination.getX() == 2
                    && (destination.getY() == 3 || destination.getY() == 5))
                    || (destination.getX() == 0
                    && (destination.getY() == 3 || destination.getY() == 5))) {
                return true;
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
                new int[]{source.getX(), source.getY(), destination.getX(),
                        destination.getY()}, getChessColor(), 'A', this);
        StepData.nearestSteps.add(nearestSteps);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isPossishow()) g.setColor(STEP_COLOR);
        else g.setColor(CHESS_COLOR);
        g.setFont(new Font("楷体", Font.BOLD, 16));
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(getChessColor().getColor());
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(getChessColor().getColor());
        if (getChessColor().equals(ChessColor.RED))
            g.drawString("仕", 12, 25); // FIXME: Use library to find the correct offset.
        if (getChessColor().equals(ChessColor.BLACK))
            g.drawString("士", 12, 25);
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//            for (int i = 0; i < AllPossibleNextMove().size(); i++) {
//                System.out.println(Arrays.toString(AllPossibleNextMove().get(i)));
//            }
        }
    }
}
