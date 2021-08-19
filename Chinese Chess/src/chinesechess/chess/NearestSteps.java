package xyz.chengzi.cs102a.chinesechess.chess;

public class NearestSteps {
    private int[] chessSteps;
    private ChessColor chessColor;
    private char chess;
    private ChessComponent chessComponent;

    public NearestSteps(int[] chessSteps, ChessColor chessColor, char chess, ChessComponent chessComponent) {
        this.chessSteps = chessSteps;
        this.chessColor = chessColor;
        this.chess = chess;
        this.chessComponent = chessComponent;
    }

    public void setChess(char chess) {
        this.chess = chess;
    }

    public NearestSteps(int[] chessSteps, ChessColor chessColor, char chess) {
        this.chessSteps = chessSteps;
        this.chessColor = chessColor;
        this.chess = chess;
    }

    public ChessComponent getChessComponent() {
        return chessComponent;
    }

    public char getChess() {
        return chess;
    }

    public void setChessColor(ChessColor chessColor) {
        this.chessColor = chessColor;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public void setChessSteps(int[] chessSteps) {
        this.chessSteps = chessSteps;
    }

    public int[] getChessSteps() {
        return chessSteps;
    }
}
