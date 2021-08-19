package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ChariotChessComponent extends ChessComponent {
    public ChariotChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else { // Not on the same row or the same column.
            return false;
        }
        recordStep(source,destination);
        return true;
    }

    @Override
    public boolean canMoveTo2(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else { // Not on the same row or the same column.
            return false;
        }
        return true;
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




    public void recordStep(ChessboardPoint source,ChessboardPoint destination){
        NearestSteps nearestSteps = new NearestSteps(
                new int[]{source.getX(),source.getY(),destination.getX(),
                        destination.getY()},getChessColor(),'N',this);
        StepData.nearestSteps.add(nearestSteps);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isPossishow())g.setColor(STEP_COLOR);
        else g.setColor(CHESS_COLOR);
        g.setFont(new Font("楷体",Font.BOLD,16));
        g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(getChessColor().getColor());
        g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        g.setColor(getChessColor().getColor());
        if(getChessColor().equals(ChessColor.RED))
            g.drawString("俥", 12, 25); // FIXME: Use library to find the correct offset.
        if(getChessColor().equals(ChessColor.BLACK))
            g.drawString("車", 12, 25);
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//            for (int i = 0; i < AllPossibleNextMove().size(); i++) {
//                System.out.println(Arrays.toString(AllPossibleNextMove().get(i)));
//            }
        }
    }
}
