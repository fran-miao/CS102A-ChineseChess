package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.awt.*;

public class HorseChessComponent extends ChessComponent {
    public HorseChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        super(chessboardPoint, location, chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (!((Math.abs(source.getY() - destination.getY()) == 1 & Math.abs(source.getX() - destination.getX()) == 2) |
                (Math.abs(source.getY() - destination.getY()) == 2 & Math.abs(source.getX() - destination.getX()) == 1)))
            return false;

        if ((destination.getY() == source.getY() + 2 & destination.getX() == source.getX() + 1) |
                (destination.getY() == source.getY() + 2 & destination.getX() == source.getX() - 1)) {
            if (!(chessboard[source.getX()][source.getY() + 1] instanceof EmptySlotComponent))
                return false;
        } else if ((destination.getY() == source.getY() - 2 & destination.getX() == source.getX() + 1) |
                (destination.getY() == source.getY() - 2 & destination.getX() == source.getX() - 1)) {
            if (!(chessboard[source.getX()][source.getY() - 1] instanceof EmptySlotComponent))
                return false;
        } else if ((destination.getY() == source.getY() + 1 & destination.getX() == source.getX() + 2) |
                (destination.getY() == source.getY() - 1 & destination.getX() == source.getX() + 2)) {
            if (!(chessboard[source.getX() + 1][source.getY()] instanceof EmptySlotComponent))
                return false;
        } else if ((destination.getY() == source.getY() + 1 & destination.getX() == source.getX() - 2) |
                (destination.getY() == source.getY() - 1 & destination.getX() == source.getX() - 2)) {
            if (!(chessboard[source.getX() - 1][source.getY()] instanceof EmptySlotComponent))
                return false;
        }
        recordStep(source, destination);
        return true;
    }



    @Override
    public boolean canMoveTo2(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (!((Math.abs(source.getY() - destination.getY()) == 1 & Math.abs(source.getX() - destination.getX()) == 2) |
                (Math.abs(source.getY() - destination.getY()) == 2 & Math.abs(source.getX() - destination.getX()) == 1)))
            return false;

        if ((destination.getY() == source.getY() + 2 & destination.getX() == source.getX() + 1) |
                (destination.getY() == source.getY() + 2 & destination.getX() == source.getX() - 1)) {
            return chessboard[source.getX()][source.getY() + 1] instanceof EmptySlotComponent;
        } else if ((destination.getY() == source.getY() - 2 & destination.getX() == source.getX() + 1) |
                (destination.getY() == source.getY() - 2 & destination.getX() == source.getX() - 1)) {
            return chessboard[source.getX()][source.getY() - 1] instanceof EmptySlotComponent;
        } else if ((destination.getY() == source.getY() + 1 & destination.getX() == source.getX() + 2) |
                (destination.getY() == source.getY() - 1 & destination.getX() == source.getX() + 2)) {
            return chessboard[source.getX() + 1][source.getY()] instanceof EmptySlotComponent;
        } else if ((destination.getY() == source.getY() + 1 & destination.getX() == source.getX() - 2) |
                (destination.getY() == source.getY() - 1 & destination.getX() == source.getX() - 2)) {
            return chessboard[source.getX() - 1][source.getY()] instanceof EmptySlotComponent;
        }
        return true;
    }


    public void recordStep(ChessboardPoint source, ChessboardPoint destination) {
        NearestSteps nearestSteps = new NearestSteps(
                new int[]{source.getX(), source.getY(), destination.getX(),
                        destination.getY()}, getChessColor(), 'H', this);
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
            g.drawString("傌", 12, 25); // FIXME: Use library to find the correct offset.
        if (getChessColor().equals(ChessColor.BLACK))
            g.drawString("馬", 12, 25);
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.RED);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//            for (int i = 0; i < AllPossibleNextMove().size(); i++) {
//                System.out.println(Arrays.toString(AllPossibleNextMove().get(i)));
//            }
        }
    }
}
