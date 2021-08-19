package xyz.chengzi.cs102a.chinesechess.chessboard;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;
import xyz.chengzi.cs102a.chinesechess.chess.*;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;
import xyz.chengzi.cs102a.chinesechess.listener.ChessboardChessListener;
import xyz.chengzi.cs102a.chinesechess.chess.StepData;
import xyz.chengzi.cs102a.chinesechess.chess.NearestSteps;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessboardComponent extends JComponent {
    private ChessListener chessListener = new ChessboardChessListener(this);
    public static ChessComponent[][] chessboard = new ChessComponent[10][9];
    private ChessColor currentColor = ChessColor.RED;
    private String winGame;

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void setWinGame(String winGame) {
        this.winGame = winGame;
    }

    public String getWinGame() {
        return winGame;
    }

    public ChessboardComponent(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        ChessComponent.registerListener(chessListener);


        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));
            }
        }

        // FIXME: Initialize chessboard for testing only.
        cInitTestBoard(0, 0, ChessColor.BLACK);
        cInitTestBoard(0, 8, ChessColor.BLACK);
        cInitTestBoard(9, 0, ChessColor.RED);
        cInitTestBoard(9, 8, ChessColor.RED);

        sInitTestBoard(3, 0, ChessColor.BLACK);
        sInitTestBoard(3, 2, ChessColor.BLACK);
        sInitTestBoard(3, 4, ChessColor.BLACK);
        sInitTestBoard(3, 6, ChessColor.BLACK);
        sInitTestBoard(3, 8, ChessColor.BLACK);
        sInitTestBoard(6, 0, ChessColor.RED);
        sInitTestBoard(6, 2, ChessColor.RED);
        sInitTestBoard(6, 4, ChessColor.RED);
        sInitTestBoard(6, 6, ChessColor.RED);
        sInitTestBoard(6, 8, ChessColor.RED);

        gInitTestBoard(0, 4, ChessColor.BLACK);
        gInitTestBoard(9, 4, ChessColor.RED);

        eInitTestBoard(0, 2, ChessColor.BLACK);
        eInitTestBoard(0, 6, ChessColor.BLACK);
        eInitTestBoard(9, 2, ChessColor.RED);
        eInitTestBoard(9, 6, ChessColor.RED);

        aInitTestBoard(0, 3, ChessColor.BLACK);
        aInitTestBoard(0, 5, ChessColor.BLACK);
        aInitTestBoard(9, 3, ChessColor.RED);
        aInitTestBoard(9, 5, ChessColor.RED);

        hInitTestBoard(0, 1, ChessColor.BLACK);
        hInitTestBoard(0, 7, ChessColor.BLACK);
        hInitTestBoard(9, 1, ChessColor.RED);
        hInitTestBoard(9, 7, ChessColor.RED);

        nInitTestBoard(2, 1, ChessColor.BLACK);
        nInitTestBoard(2, 7, ChessColor.BLACK);
        nInitTestBoard(7, 1, ChessColor.RED);
        nInitTestBoard(7, 7, ChessColor.RED);

        setStatues();
    }

    public ChessComponent[][] getChessboard() {
        return chessboard;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessboard[row][col] != null) {
            remove(chessboard[row][col]);
        }
        add(chessboard[row][col] = chessComponent);
    }

    public boolean generalToGeneral() {
        int redGeneralX = 0;
        int blackGeneralX = 0;
        int redGeneralY = 0;
        int blackGeneralY = 0;
        boolean b = true;
        O:
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                if (chessboard[i][j] instanceof GeneralChessComponent) {
                    blackGeneralX = j;
                    blackGeneralY = i;
                    break O;
                }
            }
        }
        O:
        for (int i = 7; i < 10; i++) {
            for (int j = 3; j < 6; j++) {
                if (chessboard[i][j] instanceof GeneralChessComponent) {
                    redGeneralX = j;
                    redGeneralY = i;
                    break O;
                }
            }
        }
        if (blackGeneralX != redGeneralX) {
            b = false;
        } else {
            for (int i = blackGeneralY + 1; i < redGeneralY - 1; i++) {
                if (!(chessboard[i][redGeneralX] instanceof EmptySlotComponent)) {
                    b = false;
                }
            }
        }
        //System.out.print("gen");//
        return b;
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        if (chess2 instanceof GeneralChessComponent) {                        //判断是否吃“将”
            if (chess2.getChessColor().equals(ChessColor.RED)) {
                setWinGame("BLACK wins the game!");
            } else {
                setWinGame("RED wins the game");
            }
            JOptionPane.showMessageDialog(this, getWinGame());
        }

        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            if (chess2 instanceof AdvisorChessComponent)
                StepData.eatenChess.add(new NearestSteps(new int[]{chess2.getX(), chess2.getY()}, chess2.getChessColor(), 'A'));
            else if (chess2 instanceof CannonChessComponent)
                StepData.eatenChess.add(new NearestSteps(new int[]{chess2.getX(), chess2.getY()}, chess2.getChessColor(), 'N'));
            else if (chess2 instanceof ChariotChessComponent)
                StepData.eatenChess.add(new NearestSteps(new int[]{chess2.getX(), chess2.getY()}, chess2.getChessColor(), 'C'));
            else if (chess2 instanceof ElephantChessComponent)
                StepData.eatenChess.add(new NearestSteps(new int[]{chess2.getX(), chess2.getY()}, chess2.getChessColor(), 'E'));
            else if (chess2 instanceof GeneralChessComponent)
                StepData.eatenChess.add(new NearestSteps(new int[]{chess2.getX(), chess2.getY()}, chess2.getChessColor(), 'G'));
            else if (chess2 instanceof HorseChessComponent)
                StepData.eatenChess.add(new NearestSteps(new int[]{chess2.getX(), chess2.getY()}, chess2.getChessColor(), 'H'));
            else if (chess2 instanceof SoldierChessComponent)
                StepData.eatenChess.add(new NearestSteps(new int[]{chess2.getX(), chess2.getY()}, chess2.getChessColor(), 'S'));


            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
        } else {
            StepData.eatenChess.add(new NearestSteps(new int[]{-1, -1}, ChessColor.NONE, 'O'));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessboard[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessboard[row2][col2] = chess2;


        if (generalToGeneral()) {
            if (chess1.getChessColor().equals(ChessColor.RED)) {
                setWinGame("BLACK wins the game!");
            } else {
                setWinGame("RED wins the game!");
            }
            JOptionPane.showMessageDialog(this, getWinGame());
        }
    }

    public String[] scanTheChess() {
        String[][] s = new String[11][9];
        for (int i = 0; i < 9; i++) {
            s[5][i] = "-";
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (chessboard[i][j] instanceof EmptySlotComponent) {
                    if (i >= 5)
                        s[i + 1][j] = ".";
                    else
                        s[i][j] = ".";
                } else {
                    if (chessboard[i][j] instanceof ChariotChessComponent) {
                        if (chessboard[i][j].getChessColor().equals(ChessColor.RED)) {
                            if (i >= 5)
                                s[i + 1][j] = "c";
                            else
                                s[i][j] = "c";
                        } else {
                            if (i >= 5)
                                s[i + 1][j] = "C";
                            else
                                s[i][j] = "C";
                        }
                    } else if (chessboard[i][j] instanceof AdvisorChessComponent) {
                        if (chessboard[i][j].getChessColor().equals(ChessColor.RED)) {
                            if (i >= 5)
                                s[i + 1][j] = "a";
                            else
                                s[i][j] = "a";
                        } else {
                            if (i >= 5)
                                s[i + 1][j] = "A";
                            else
                                s[i][j] = "A";
                        }
                    } else if (chessboard[i][j] instanceof CannonChessComponent) {
                        if (chessboard[i][j].getChessColor().equals(ChessColor.RED)) {
                            if (i >= 5)
                                s[i + 1][j] = "n";
                            else
                                s[i][j] = "n";
                        } else {
                            if (i >= 5)
                                s[i + 1][j] = "N";
                            else
                                s[i][j] = "N";
                        }
                    } else if (chessboard[i][j] instanceof ElephantChessComponent) {
                        if (chessboard[i][j].getChessColor().equals(ChessColor.RED)) {
                            if (i >= 5)
                                s[i + 1][j] = "e";
                            else
                                s[i][j] = "e";
                        } else {
                            if (i >= 5)
                                s[i + 1][j] = "E";
                            else
                                s[i][j] = "E";
                        }
                    } else if (chessboard[i][j] instanceof GeneralChessComponent) {
                        if (chessboard[i][j].getChessColor().equals(ChessColor.RED)) {
                            if (i >= 5)
                                s[i + 1][j] = "g";
                            else
                                s[i][j] = "g";
                        } else {
                            if (i >= 5)
                                s[i + 1][j] = "G";
                            else
                                s[i][j] = "G";
                        }
                    } else if (chessboard[i][j] instanceof HorseChessComponent) {
                        if (chessboard[i][j].getChessColor().equals(ChessColor.RED)) {
                            if (i >= 5)
                                s[i + 1][j] = "h";
                            else
                                s[i][j] = "h";
                        } else {
                            if (i >= 5)
                                s[i + 1][j] = "H";
                            else
                                s[i][j] = "H";
                        }
                    } else if (chessboard[i][j] instanceof SoldierChessComponent) {
                        if (chessboard[i][j].getChessColor().equals(ChessColor.RED)) {
                            if (i >= 5)
                                s[i + 1][j] = "s";
                            else
                                s[i][j] = "s";
                        } else {
                            if (i >= 5)
                                s[i + 1][j] = "S";
                            else
                                s[i][j] = "S";
                        }
                    }
                }
            }
        }
        String[] out = new String[11];
        for (int i = 0; i < 11; i++) {
            out[i] = s[i][0] + s[i][1] + s[i][2] + s[i][3] + s[i][4] + s[i][5]
                    + s[i][6] + s[i][7] + s[i][8];
        }
        return out;
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK;
    }

    public void cInitTestBoard(int row, int col, ChessColor color) {//初始化“车"
        ChessComponent chessComponent = new ChariotChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void sInitTestBoard(int row, int col, ChessColor color) {//初始化“兵”
        ChessComponent chessComponent = new SoldierChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void gInitTestBoard(int row, int col, ChessColor color) {//初始化“帅”
        ChessComponent chessComponent = new GeneralChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void eInitTestBoard(int row, int col, ChessColor color) {//初始化“象”
        ChessComponent chessComponent = new ElephantChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void aInitTestBoard(int row, int col, ChessColor color) {//初始化“士”
        ChessComponent chessComponent = new AdvisorChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void hInitTestBoard(int row, int col, ChessColor color) {//初始化“馬”
        ChessComponent chessComponent = new HorseChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void nInitTestBoard(int row, int col, ChessColor color) {//初始化“炮”
        ChessComponent chessComponent = new CannonChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

//    public void pInitTestBoard(int row, int col, ChessColor color) {//初始化“"
//        ChessComponent chessComponent = new CannonChessComponent(new ChessboardPoint(row, col),
//                calculatePoint(row, col), color);
//        chessComponent.setVisible(true);
//        putChessOnBoard(chessComponent);
//    }//

    @Override
    protected void paintComponent(Graphics g) {//paint the chessboard
        setStatues();
        super.paintComponent(g);


        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBoardLine(g, 0, 0, 9, 0);
        paintBoardLine(g, 0, 8, 9, 8);
        paintHalfBoard(g, 0);
        paintHalfBoard(g, 5);
        paintKingSquare(g, 1, 4);
        paintKingSquare(g, 8, 4);

        int x = 25, y = 195;
        for (int i = 0; i < 4; i++) {
            g.drawLine(x, y, x, y - 10);
            g.drawLine(x, y, x + 10, y);
            g.drawLine(x, y + 10, x, y + 20);
            g.drawLine(x, y + 10, x + 10, y + 10);
            g.drawLine(x, y + 180, x, y - 10 + 180);
            g.drawLine(x, y + 180, x + 10, y + 180);
            g.drawLine(x, y + 10 + 180, x, y + 20 + 180);
            g.drawLine(x, y + 10 + 180, x + 10, y + 10 + 180);
            x += 120;
        }
        x = 135;
        for (int i = 0; i < 4; i++) {
            g.drawLine(x, y, x, y - 10);
            g.drawLine(x, y, x - 10, y);
            g.drawLine(x, y + 10, x, y + 20);
            g.drawLine(x, y + 10, x - 10, y + 10);
            g.drawLine(x, y + 180, x, y - 10 + 180);
            g.drawLine(x, y + 180, x - 10, y + 180);
            g.drawLine(x, y + 10 + 180, x, y + 20 + 180);
            g.drawLine(x, y + 10 + 180, x - 10, y + 10 + 180);
            x += 120;
        }

        x = 85;
        y = 135;
        for (int i = 0; i < 2; i++) {
            g.drawLine(x, y, x + 10, y);
            g.drawLine(x, y, x, y - 10);
            g.drawLine(x - 10, y, x - 20, y);
            g.drawLine(x - 10, y, x - 10, y - 10);
            g.drawLine(x, y + 10, x + 10, y + 10);
            g.drawLine(x, y + 10, x, y + 20);
            g.drawLine(x - 10, y + 10, x - 10, y + 20);
            g.drawLine(x - 10, y + 10, x - 20, y + 10);
            x = x + 360;
        }
        x = 85;
        y = 435;
        for (int i = 0; i < 2; i++) {
            g.drawLine(x, y, x + 10, y);
            g.drawLine(x, y, x, y - 10);
            g.drawLine(x - 10, y, x - 20, y);
            g.drawLine(x - 10, y, x - 10, y - 10);
            g.drawLine(x, y + 10, x + 10, y + 10);
            g.drawLine(x, y + 10, x, y + 20);
            g.drawLine(x - 10, y + 10, x - 10, y + 20);
            g.drawLine(x - 10, y + 10, x - 20, y + 10);
            x = x + 360;
        }

        g.setFont(new Font("楷体", Font.BOLD, 30));
        g.drawString("楚  河", 100, 305);
        g.drawString("汉  界", 330, 305);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image backGroud = toolkit.getImage("C:\\Users\\mfr17\\Desktop\\Tchess10 - 副本\\src\\xyz\\chengzi\\cs102a\\chinesechess\\Pictures\\");
        g.drawImage(backGroud, 6, 8, 505, 564, this);
    }

    /////////////////////attention :in tem ChessboardComponent, here is a method called finalLOadGame
    public void clearGame() {
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));
            }
        }
    }


    public void initialGame() {
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));
            }
        }

        // FIXME: Initialize chessboard for testing only.
        cInitTestBoard(0, 0, ChessColor.BLACK);
        cInitTestBoard(0, 8, ChessColor.BLACK);
        cInitTestBoard(9, 0, ChessColor.RED);
        cInitTestBoard(9, 8, ChessColor.RED);

        sInitTestBoard(3, 0, ChessColor.BLACK);
        sInitTestBoard(3, 2, ChessColor.BLACK);
        sInitTestBoard(3, 4, ChessColor.BLACK);
        sInitTestBoard(3, 6, ChessColor.BLACK);
        sInitTestBoard(3, 8, ChessColor.BLACK);
        sInitTestBoard(6, 0, ChessColor.RED);
        sInitTestBoard(6, 2, ChessColor.RED);
        sInitTestBoard(6, 4, ChessColor.RED);
        sInitTestBoard(6, 6, ChessColor.RED);
        sInitTestBoard(6, 8, ChessColor.RED);

        gInitTestBoard(0, 4, ChessColor.BLACK);
        gInitTestBoard(9, 4, ChessColor.RED);

        eInitTestBoard(0, 2, ChessColor.BLACK);
        eInitTestBoard(0, 6, ChessColor.BLACK);
        eInitTestBoard(9, 2, ChessColor.RED);
        eInitTestBoard(9, 6, ChessColor.RED);

        aInitTestBoard(0, 3, ChessColor.BLACK);
        aInitTestBoard(0, 5, ChessColor.BLACK);
        aInitTestBoard(9, 3, ChessColor.RED);
        aInitTestBoard(9, 5, ChessColor.RED);

        hInitTestBoard(0, 1, ChessColor.BLACK);
        hInitTestBoard(0, 7, ChessColor.BLACK);
        hInitTestBoard(9, 1, ChessColor.RED);
        hInitTestBoard(9, 7, ChessColor.RED);

        nInitTestBoard(2, 1, ChessColor.BLACK);
        nInitTestBoard(2, 7, ChessColor.BLACK);
        nInitTestBoard(7, 1, ChessColor.RED);
        nInitTestBoard(7, 7, ChessColor.RED);
    }


    private void paintHalfBoard(Graphics g, int startRow) {
        for (int row = startRow; row < startRow + 5; row++) {
            paintBoardLine(g, row, 0, row, 8);
        }
        for (int col = 0; col < 9; col++) {
            paintBoardLine(g, startRow, col, startRow + 4, col);
        }
    }

    private void paintKingSquare(Graphics g, int centerRow, int centerCol) {
        paintBoardLine(g, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1);
        paintBoardLine(g, centerRow - 1, centerCol + 1, centerRow + 1, centerCol - 1);
    }

    private void paintBoardLine(Graphics g, int rowFrom, int colFrom, int rowTo, int colTo) {
        int offsetX = ChessComponent.CHESS_SIZE.width / 2, offsetY = ChessComponent.CHESS_SIZE.height / 2;
        Point p1 = calculatePoint(rowFrom, colFrom), p2 = calculatePoint(rowTo, colTo);
        g.drawLine(p1.x + offsetX, p1.y + offsetY, p2.x + offsetX, p2.y + offsetY);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * getWidth() / 9, row * getHeight() / 10);
    }


    public void setStatues() {
        String[] turn = {"This is red turn", "This is black turn"};
        String[] checkingCheck = {"red checking ", "black checking", ""};
        String[] checkingCheckPlus = {"red checking 222", "black checking 222", ""};
        int i;
        int j;
        int k;
        if (currentColor == ChessColor.RED)
            i = 0;
        else i = 1;
        if (checkingCheck()) {
            if (currentColor.equals(ChessColor.RED))
                j = 1;
            else
                j = 0;
        } else j = 2;
        if (checkingCheckPlus()) {
            if (currentColor.equals(ChessColor.RED)) {
                k = 1;
            } else k = 0;
        } else k = 2;
        ChessGameFrame.setStatues(turn[i], checkingCheck[j], checkingCheckPlus[k]);
    }

    private boolean checkingCheck() {
        GeneralChessComponent generalChessComponent = null;
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if (chessboard[i][j] instanceof EmptySlotComponent || chessboard[i][j].getChessColor() != currentColor)
                    continue;
                if (chessboard[i][j] instanceof GeneralChessComponent)
                    generalChessComponent = (GeneralChessComponent) chessboard[i][j];
            }
        }
        if (generalChessComponent != null) {
            for (int i = 0; i < chessboard.length; i++) {
                for (int j = 0; j < chessboard[i].length; j++) {
                    if (chessboard[i][j] instanceof EmptySlotComponent || chessboard[i][j].getChessColor() == currentColor)
                        continue;
                    if (chessboard[i][j].canMoveTo2(chessboard, generalChessComponent.getChessboardPoint()))
                        return true;
                }
            }
        }
        return false;
    }

    private boolean checkingCheckPlus() {
        GeneralChessComponent generalChessComponent = null;
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if (chessboard[i][j] instanceof EmptySlotComponent || chessboard[i][j].getChessColor() == currentColor)
                    continue;
                if (chessboard[i][j] instanceof GeneralChessComponent)
                    generalChessComponent = (GeneralChessComponent) chessboard[i][j];
            }
        }
        if (generalChessComponent != null) {
            for (int i = 0; i < chessboard.length; i++) {
                for (int j = 0; j < chessboard[i].length; j++) {
                    if (chessboard[i][j] instanceof EmptySlotComponent || chessboard[i][j].getChessColor() != currentColor)
                        continue;
                    if (chessboard[i][j].canMoveTo2(chessboard, generalChessComponent.getChessboardPoint()))
                        return true;
                }
            }
        }
        return false;
    }
}
