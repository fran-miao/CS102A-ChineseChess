package xyz.chengzi.cs102a.chinesechess.listener;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chess.StepData;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import javax.swing.*;
import java.util.ArrayList;

public class ChessboardChessListener extends ChessListener {
    private ChessboardComponent chessboardComponent;
    private ChessComponent first;
    private ArrayList<ChessComponent> possibleStepsl = new ArrayList<ChessComponent>();

    public ChessboardChessListener(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    @Override
    public void onClick(ChessComponent chessComponent) {
        //可加音效
        if (first == null) {
            if (handleFirst(chessComponent)) {
                //chessComponent.setSelected(true);
                first = chessComponent;
                first.setSelected(true);
                possibleSteps(chessboardComponent.getChessboard(), first);
                chessboardComponent.repaint();
            }
        } else {
            if (first == chessComponent) { // Double-click to unselect.
                first.setSelected(false);
                first = null;
                removePossibleSteps();
                chessboardComponent.repaint();
            } else if (handleFirst(chessComponent)) {//select others
                first.setSelected(false);
                removePossibleSteps();
                first = chessComponent;
                first.setSelected((true));
                possibleSteps(chessboardComponent.getChessboard(), first);
                chessboardComponent.repaint();
            } else if (handleSecond(chessComponent)) {
                chessboardComponent.swapChessComponents(first, chessComponent);
                System.out.println(chessboardComponent.generalToGeneral());
//                if(chessboardComponent.generalToGeneral()){
//                    JOptionPane.showMessageDialog(chessboardComponent, "Invalid Step!!");
//                }
                chessboardComponent.swapColor();
                first.setSelected(false);
                removePossibleSteps();
                chessboardComponent.repaint();
                first = null;
            }
        }
    }


    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboardComponent.getCurrentColor();
    }

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboardComponent.getCurrentColor() &&
                first.canMoveTo(chessboardComponent.getChessboard(), chessComponent.getChessboardPoint());
    }


    public void possibleSteps(ChessComponent[][] chessComponents, ChessComponent chessComponent) {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                if (chessComponent.canMoveTo2(chessComponents, chessComponents[i][j].getChessboardPoint()) && chessComponents[i][j].getChessColor() != chessComponent.getChessColor()) {
                    chessComponents[i][j].setPossishow(true);
                    possibleStepsl.add(chessComponents[i][j]);
                }
            }
        }
    }

    public void removePossibleSteps() {
        for (ChessComponent chessComponent : possibleStepsl) {
            chessComponent.setPossishow(false);
        }
        possibleStepsl.removeAll(possibleStepsl);
    }
}
