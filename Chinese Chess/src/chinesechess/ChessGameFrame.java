package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chess.*;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import java.io.File;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ChessGameFrame extends JFrame {
    private static JLabel statusLabel = new JLabel();
    private static JLabel checkingCheckLabel = new JLabel();
    private static JLabel checkingCheckPlusLabel = new JLabel();
    Component c = this;
///////////////////////////////////////////////////////////////attention:the board and chess can be beautified (sample is in Tchess2)

//    public static void checkingCheck() {/////////////////////////////////////////////////////
//        //ChessComponent.checkingCheck;
//        // chessboard.getChessboard();
//        System.out.print("checking!!!!!!");
//    }


    //    public ChessGameEnter(){
//        setTitle("2019 ");
//    }
    private ChessboardComponent chessboard = new ChessboardComponent(540, 600);

    //    JLabel statusLabel = new JLabel();
//    JLabel checkingCheckLabel=new JLabel();
    public static void setStatues(String turns, String checking, String checkingPlus) {
        statusLabel.setText(turns);
        checkingCheckLabel.setText(checking);
        checkingCheckPlusLabel.setText(checkingPlus);
    }


    public ChessGameFrame() {
        setTitle("Welcome use our program");
        setSize(700, 750);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem item = new JMenuItem("new game");
        item.addActionListener(e -> {
            JButton but1 = new JButton("Yes");
            JButton but2 = new JButton("No");
            JDialog d = new JDialog();
            JLabel l = new JLabel();
            l.setText("Any unsaved game will be lost, would you want to continue?");
            d.add(l);
            d.add(but1);
            d.add(but2);
            d.setLocation(500, 300);
            d.setSize(400, 120);
            d.setLayout(new FlowLayout());
            but1.addActionListener((ex) -> {
                StepData.nearestSteps.clear();
                StepData.eatenChess.clear();
                chessboard.setCurrentColor(ChessColor.RED);
                d.dispose();
                chessboard.initialGame();
                chessboard.repaint();
                JOptionPane.showMessageDialog(this, "successful1");
            });
            but2.addActionListener((ex) -> {
                d.dispose();
            });
            d.setVisible(true);

        });
        JMenuItem item1 = new JMenuItem("save game");
        item1.addActionListener(e -> {
            String str1 = JOptionPane.showInputDialog("Enter the name:");
            File file = new File("C:\\Users\\G3\\Desktop\\Project Tchess\\Tchess5\\savedGame\\" + str1 + ".txt");
            try {
                if (!file.exists()) {
                    //file.createNewFile();

                    //FileWriter fileWriter = new FileWriter(file.getName(), true);
                    FileWriter fileWriter = new FileWriter(file.getPath(), true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write("@Last_mover=" + chessboard.getCurrentColor() + "\n");//Here to add the turns
                    bufferedWriter.write("@@\n");
                    bufferedWriter.write("\n");
                    for (int j = 0; j < 11; j++) {
                        bufferedWriter.write(chessboard.scanTheChess()[j]);
                        bufferedWriter.write("\n");
                    }
                    bufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(this, "Successful2");
                } else {
                    JDialog d = new JDialog();
                    JLabel l = new JLabel();
                    l.setText("The file already exists ,please rename your game");
                    JButton but = new JButton("OK");
                    d.add(l);
                    d.add(but);
                    d.setLocation(550, 400);
                    d.setSize(350, 100);
                    d.setLayout(new FlowLayout());
                    d.setVisible(true);
                    but.addActionListener((ex) -> {
                        d.dispose();
                    });
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        JMenuItem item2 = new JMenuItem("load game");
        item2.addActionListener(e -> {
            String defaultPath = "C:\\Users\\mfr17\\Desktop\\Tchess11";
            JFileChooser f = new JFileChooser();
            f.setMultiSelectionEnabled(false);
            f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            f.setFileHidingEnabled(true);
            f.setAcceptAllFileFilterUsed(false);
            f.setCurrentDirectory(new File(defaultPath));
            f.setFileFilter(new MyFilter("JAVA FILE"));
            f.setFileFilter(new MyFilter("TXT"));
            String selectPath = "";
            int re = f.showOpenDialog(chessboard);
            if (re == JFileChooser.APPROVE_OPTION) {
                selectPath = f.getSelectedFile().getPath();
                System.out.println(selectPath);
                f.hide();
            }


            try {
                String[] loadFile = load(selectPath);
                if (loadCheck(loadFile)) {
                    chessboard.clearGame();
                    setTheGame(loadFile);
                    chessboard.repaint();
                    //JOptionPane.showMessageDialog(this, "Successful3");
                } else
                    JOptionPane.showMessageDialog(
                            this,
                            "The file is invalid ,please select the correct one");

            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });
        JMenuItem item3 = new JMenuItem("regret");
        item3.addActionListener(e -> {
            int size = StepData.nearestSteps.size();
            if (size == 0) {
                JOptionPane.showMessageDialog(this, "You can't regret anymore!");
                return;
            }
            ChessComponent chess2 = StepData.nearestSteps.get(size - 1).getChessComponent();
            int beforeX = StepData.nearestSteps.get(size - 1).getChessSteps()[0];
            int beforeY = StepData.nearestSteps.get(size - 1).getChessSteps()[1];
            int afterX = StepData.nearestSteps.get(size - 1).getChessSteps()[2];
            int afterY = StepData.nearestSteps.get(size - 1).getChessSteps()[3];
            ChessColor cc = StepData.nearestSteps.get(size - 1).getChessColor();

//            System.out.print(beforeX);
//            System.out.print(beforeY);
//            System.out.print(afterX);
//            System.out.print(afterY);
//            System.out.print(cc.toString());
//            System.out.print(StepData.nearestSteps.get(size-1).getChess());
//            System.out.println();

            if (cc.equals(ChessColor.RED)) {
                chessboard.setCurrentColor(ChessColor.RED);
            } else {
                chessboard.setCurrentColor(ChessColor.BLACK);
            }
            if (StepData.nearestSteps.get(size - 1).getChess() == 'A') {
                chessboard.aInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'N') {
                chessboard.nInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'C') {
                chessboard.cInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'E') {
                chessboard.eInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'G') {
                chessboard.gInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'H') {
                chessboard.hInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'S') {
                chessboard.sInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                StepData.nearestSteps.remove(size - 1);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
                repaint();
            }

            int size2 = StepData.eatenChess.size();
            int col = StepData.eatenChess.get(size2 - 1).getChessSteps()[0] / 60;
            int row = StepData.eatenChess.get(size2 - 1).getChessSteps()[1] / 60;
//                System.out.print(row+""+col);
//                System.out.print(StepData.eatenChess.get(size2-1).getChessColor().toString());
//                System.out.println(StepData.eatenChess.get(size2-1).getChess());

            if (StepData.eatenChess.get(size2 - 1).getChess() == 'O') {
                StepData.eatenChess.remove(size2 - 1);
            } else {
                if (StepData.eatenChess.get(size2 - 1).getChess() == 'A') {
                    chessboard.aInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'N') {
                    chessboard.nInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'C') {
                    chessboard.cInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'E') {
                    chessboard.eInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'G') {
                    chessboard.gInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'H') {
                    chessboard.hInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'S') {
                    chessboard.sInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                }
            }

        });
        JMenuItem item4 = new JMenuItem("load by steps");
        item4.addActionListener(e -> {
            String defaultPath = "C:\\Users\\mfr17\\Desktop\\Tchess11";
            JFileChooser f = new JFileChooser();
            f.setMultiSelectionEnabled(false);
            f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            f.setFileHidingEnabled(true);
            f.setAcceptAllFileFilterUsed(false);
            f.setCurrentDirectory(new File(defaultPath));
            f.setFileFilter(new MyFilter("JAVA FILE"));
            f.setFileFilter(new MyFilter("TXT"));
            String selectPath = "";
            int re = f.showOpenDialog(chessboard);
            if (re == JFileChooser.APPROVE_OPTION) {
                selectPath = f.getSelectedFile().getPath();
                System.out.println(selectPath);
                f.hide();
            }

            try {
                int[][] loadFile = loadGameBySteps(selectPath);//the first [] is the step ,the second means the number stored in the step
                //for example
                //  1   2   3   4
                //  5   6   7   8
                //  8   5   4   1
                //loadFile[0][0]=1,loadFile[0][1]=2,loadFile[0][2]=3,and so on.....
                //loadFile[3][2]=4,loadFile[3][3]=1
                //Caution:loadFile[].length here may be not 4
                //checking is in the loadGameByStepsCheck
                //
                //
                //////////////////////you can add something here (for example the presentation of the steps step by step
                if (loadGameByStepsCheck(loadFile)) {
//                    chessboard.clearGame();
//                    setTheGame(loadFile);
//                    chessboard.repaint();           //////////////////////attention here:you can change the setting way!!
                    //JOptionPane.showMessageDialog(this, "Successful3");
                } else
                    JOptionPane.showMessageDialog(
                            this,
                            "The file is invalid ,please select the correct one");

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        menu.add(item);
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        bar.add(menu);
        this.setJMenuBar(bar);

        // ChessboardComponent chessboard = new ChessboardComponent(540, 600);
        add(chessboard);


        statusLabel.setLocation(550, 400);
        statusLabel.setSize(200, 20);
        add(statusLabel);//here is the turns
        statusLabel.repaint();

        checkingCheckLabel.setLocation(550, 500);
        checkingCheckLabel.setSize(200, 20);
        add(checkingCheckLabel);
        checkingCheckLabel.repaint();

        checkingCheckPlusLabel.setLocation(550, 300);
        checkingCheckPlusLabel.setSize(200, 20);
        add(checkingCheckPlusLabel);
        checkingCheckPlusLabel.repaint();

        JButton button = new JButton("New Game");
        button.addActionListener((e) -> {
            JButton but1 = new JButton("Yes");
            JButton but2 = new JButton("No");
            JDialog d = new JDialog();
            JLabel l = new JLabel();
            l.setText("Any unsaved game will be lost, would you want to continue?");
            d.add(l);
            d.add(but1);
            d.add(but2);
            d.setLocation(500, 300);
            d.setSize(400, 120);
            d.setLayout(new FlowLayout());
            but1.addActionListener((ex) -> {
                chessboard.setCurrentColor(ChessColor.RED);
                d.dispose();
                chessboard.initialGame();
                chessboard.repaint();
                JOptionPane.showMessageDialog(this, "successful1");
            });
            but2.addActionListener((ex) -> {
                d.dispose();
            });
            d.setVisible(true);

        });
        button.setLocation(550, 100);
        button.setSize(120, 30);
        add(button);


        JButton button2 = new JButton("Save Game");
        button2.addActionListener((e) -> {
            String str1 = JOptionPane.showInputDialog("Enter the name:");
            File file = new File("C:\\Users\\G3\\Desktop\\Project Tchess\\Tchess5\\savedGame\\" + str1 + ".txt");
            try {
                if (!file.exists()) {
                    //file.createNewFile();

                    //FileWriter fileWriter = new FileWriter(file.getName(), true);
                    FileWriter fileWriter = new FileWriter(file.getPath(), true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write("@Last_mover=" + chessboard.getCurrentColor() + "\n");//Here to add the turns
                    bufferedWriter.write("@@\n");
                    bufferedWriter.write("\n");
                    for (int j = 0; j < 11; j++) {
                        bufferedWriter.write(chessboard.scanTheChess()[j]);
                        bufferedWriter.write("\n");
                    }
                    bufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(this, "Successful2");
                } else {
                    JDialog d = new JDialog();
                    JLabel l = new JLabel();
                    l.setText("The file already exists ,please rename your game");
                    JButton but = new JButton("OK");
                    d.add(l);
                    d.add(but);
                    d.setLocation(550, 400);
                    d.setSize(350, 100);
                    d.setLayout(new FlowLayout());
                    d.setVisible(true);
                    but.addActionListener((ex) -> {
                        d.dispose();
                    });
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


//            for (int j = 0; j < 11; j++) {
//                for (int k = 0; k < 9; k++) {
//                    System.out.printf(chessboard.scanTheChess()[j][k]);
//                }
//                System.out.println();
//            }                                //this is test code

        });
        button2.setLocation(550, 150);
        button2.setSize(120, 30);
        add(button2);


        JButton button3 = new JButton("Load Game");
        button3.addActionListener((e) -> {
            String defaultPath = "C:\\Users\\G3\\Desktop\\Project Tchess\\Tchess5";
            JFileChooser f = new JFileChooser();
            f.setMultiSelectionEnabled(false);
            f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            f.setFileHidingEnabled(true);
            f.setAcceptAllFileFilterUsed(false);
            f.setCurrentDirectory(new File(defaultPath));
            f.setFileFilter(new MyFilter("JAVA FILE"));
            f.setFileFilter(new MyFilter("TXT"));
            String selectPath = "";
            int re = f.showOpenDialog(chessboard);
            if (re == JFileChooser.APPROVE_OPTION) {
                selectPath = f.getSelectedFile().getPath();
                System.out.println(selectPath);
                f.hide();
            }


            try {
                String[] loadFile = load(selectPath);
                if (loadCheck(loadFile)) {
                    chessboard.clearGame();
                    setTheGame(loadFile);
                    chessboard.repaint();
                    //JOptionPane.showMessageDialog(this, "Successful3");
                } else
                    JOptionPane.showMessageDialog(
                            this,
                            "The file is invalid ,please select the correct one");

            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });
        button3.setLocation(550, 200);
        button3.setSize(120, 30);
        add(button3);


        JButton button4 = new JButton("Regret");
        button4.addActionListener(e -> {

            int size = StepData.nearestSteps.size();
            if (size == 0) {
                JOptionPane.showMessageDialog(this, "You can't regret anymore!");
                return;
            }
            ChessComponent chess2;
            int beforeX = StepData.nearestSteps.get(size - 1).getChessSteps()[0];
            int beforeY = StepData.nearestSteps.get(size - 1).getChessSteps()[1];
            int afterX = StepData.nearestSteps.get(size - 1).getChessSteps()[2];
            int afterY = StepData.nearestSteps.get(size - 1).getChessSteps()[3];
            ChessColor cc = StepData.nearestSteps.get(size - 1).getChessColor();

//            for (int i = 0; i < size; i++) {
//                System.out.println(Arrays.toString(StepData.nearestSteps.get(i).getChessSteps()) + " "
//                        + StepData.nearestSteps.get(i).getChess() + " " +
//                        StepData.nearestSteps.get(i).getChessColor());
//            }

            if (cc.equals(ChessColor.RED)) {
                chessboard.setCurrentColor(ChessColor.RED);
            } else {
                chessboard.setCurrentColor(ChessColor.BLACK);
            }
            if (StepData.nearestSteps.get(size - 1).getChess() == 'A') {
                chessboard.aInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent
                        (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                chessboard.getChessboard()[afterX][afterY] = chess2;
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'N') {
                chessboard.nInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent
                        (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                chessboard.getChessboard()[afterX][afterY] = chess2;
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'C') {
                chessboard.cInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent
                        (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                chessboard.getChessboard()[afterX][afterY] = chess2;
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'E') {
                chessboard.eInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent
                        (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                chessboard.getChessboard()[afterX][afterY] = chess2;
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'G') {
                chessboard.gInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent
                        (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                chessboard.getChessboard()[afterX][afterY] = chess2;
                StepData.nearestSteps.remove(size - 1);
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'H') {
                chessboard.hInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent
                        (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                StepData.nearestSteps.remove(size - 1);
                chessboard.getChessboard()[afterX][afterY] = chess2;
                repaint();
            } else if (StepData.nearestSteps.get(size - 1).getChess() == 'S') {
                chessboard.sInitTestBoard(beforeX, beforeY, cc);
                chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                chessboard.add(chess2 = new EmptySlotComponent
                        (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                chessboard.getChessboard()[afterX][afterY] = chess2;
                StepData.nearestSteps.remove(size - 1);
                repaint();
            }

            int size2 = StepData.eatenChess.size();
            int col = StepData.eatenChess.get(size2 - 1).getChessSteps()[0] / 60;
            int row = StepData.eatenChess.get(size2 - 1).getChessSteps()[1] / 60;

//            for (int i = 0; i < size2; i++) {
//                System.out.println(Arrays.toString(StepData.eatenChess.get(i).getChessSteps()) + " " +
//                        StepData.eatenChess.get(i).getChess() + " " +
//                        StepData.eatenChess.get(i).getChessColor());
//            }

            if (StepData.eatenChess.get(size2 - 1).getChess() == 'O') {
                StepData.eatenChess.remove(size2 - 1);
            } else {
                if (StepData.eatenChess.get(size2 - 1).getChess() == 'A') {
                    chessboard.aInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'N') {
                    chessboard.nInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'C') {
                    chessboard.cInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'E') {
                    chessboard.eInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'G') {
                    chessboard.gInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'H') {
                    chessboard.hInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                } else if (StepData.eatenChess.get(size2 - 1).getChess() == 'S') {
                    chessboard.sInitTestBoard(row, col, StepData.eatenChess.get(size2 - 1).getChessColor());
                    StepData.eatenChess.remove(size2 - 1);
                    repaint();
                }
            }
        });
        button4.setLocation(550, 250);
        button4.setSize(120, 30);
        add(button4);


        JButton button5 = new JButton("load game by steps");
        button5.addActionListener(e -> {
            String defaultPath = "C:\\Users\\mfr17\\Desktop\\Tchess9\\src\\xyz\\chengzi\\cs102a\\chinesechess";
            JFileChooser f = new JFileChooser();
            f.setMultiSelectionEnabled(false);
            f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            f.setFileHidingEnabled(true);
            f.setAcceptAllFileFilterUsed(false);
            f.setCurrentDirectory(new File(defaultPath));
            f.setFileFilter(new MyFilter("JAVA FILE"));
            f.setFileFilter(new MyFilter("TXT"));
            String selectPath = "";
            int re = f.showOpenDialog(chessboard);
            if (re == JFileChooser.APPROVE_OPTION) {
                selectPath = f.getSelectedFile().getPath();
                System.out.println(selectPath);
                f.hide();
            }

            try {
                int[][] loadFile = loadGameBySteps(selectPath);//the first [] is the step ,the second means the number stored in the step
                //for example
                //  1   2   3   4
                //  5   6   7   8
                //  8   5   4   1
                //loadFile[0][0]=1,loadFile[0][1]=2,loadFile[0][2]=3,and so on.....
                //loadFile[3][2]=4,loadFile[3][3]=1
                //Caution:loadFile[].length here may be not 4
                //checking is in the loadGameByStepsCheck
                //
                //
                //////////////////////you can add something here (for example the presentation of the steps step by step
                for (int i = 0; i < loadFile.length; i++) {
                    int beforeY = loadFile[i][0];
                    int beforeX = loadFile[i][1];
                    int afterY = loadFile[i][2];
                    int afterX = loadFile[i][3];
                    if (i % 2 != 0) {
                        afterX--;
                        afterY--;
                        beforeX--;
                        beforeY--;
                        ChessComponent chess2 = chessboard.getChessboard()[beforeX][beforeY];
                        if (chess2 instanceof ChariotChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.cInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.cInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof AdvisorChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.aInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.aInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof CannonChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.nInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.nInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof ElephantChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.eInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.eInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof GeneralChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.gInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.gInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof HorseChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.hInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.hInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof SoldierChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.sInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.sInitTestBoard(afterX, afterY, ChessColor.BLACK);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        }
                    } else {
                        beforeX = 10 - beforeX;
                        beforeY = 9 - beforeY;
                        afterX = 10 - afterX;
                        afterY = 9 - afterY;
                        ChessComponent chess2 = chessboard.getChessboard()[beforeX][beforeY];
                        if (chess2 instanceof ChariotChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.cInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.cInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof AdvisorChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.aInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.aInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof CannonChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.nInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.nInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof ElephantChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.eInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.eInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof GeneralChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.gInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.gInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof HorseChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.hInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.hInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;

                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        } else if (chess2 instanceof SoldierChessComponent) {
                            if (chess2.canMoveTo2(chessboard.getChessboard(), new ChessboardPoint(afterX, afterY))) {
                                if (chessboard.getChessboard()[afterX][afterY] instanceof EmptySlotComponent) {
                                    chessboard.sInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                } else {
                                    chessboard.remove(chessboard.getChessboard()[afterX][afterY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(afterX, afterY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[afterX][afterY] = chess2;

                                    chessboard.sInitTestBoard(afterX, afterY, ChessColor.RED);

                                    chessboard.remove(chessboard.getChessboard()[beforeX][beforeY]);
                                    chessboard.add(chess2 = new EmptySlotComponent
                                            (new ChessboardPoint(beforeX, beforeY), new Point(afterY * 60, afterX * 60)));
                                    chessboard.getChessboard()[beforeX][beforeY] = chess2;
                                }
                            } else {
//                                JOptionPane.showMessageDialog(this, "Invalid Step! Row:" + (i + 4));
//                                return;
                            }
                        }
                    }
                    repaint();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        button5.setLocation(550, 350);
        button5.setSize(120, 30);
        add(button5);

        button.setToolTipText("This is to restart the game");
        button2.setToolTipText("This will save your game");
        button3.setToolTipText("This will load your saved game");
        button4.setToolTipText("This is to regret your last step");
        button5.setToolTipText("This is to load game by steps");


        JLabel one = new JLabel("");
        URL url = null;
        try {
            url = new File("C:\\Users\\G3\\Desktop\\Project Tchess\\Tchess7\\picture\\back2.png\\").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert url != null;
        Icon icon = new ImageIcon(url);
        one.setIcon(icon);
        one.setLocation(0, -250);
        one.setSize(800, 1200);
        add(one);
    }


    public static class MyFilter extends javax.swing.filechooser.FileFilter implements FileFilter {
        private String ext;

        MyFilter(String extString) {
            this.ext = extString;
        }

        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = getExtension(f);
            return extension.toLowerCase().equals(this.ext.toLowerCase());
        }

        public String getDescription() {
            return this.ext.toUpperCase();
        }

        private String getExtension(File f) {
            String name = f.getName();
            int index = name.lastIndexOf('.');
            if (index == -1) {
                return "";
            } else {
                return name.substring(index + 1).toLowerCase();
            }
        }
    }


    private static String[] load(String filePath) throws IOException {
        String[] load = new String[10];
        String statues;
        FileReader fr;
        fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        statues = br.readLine().subSequence(12, 15).toString();
        System.out.println(statues);
        br.readLine();
        br.readLine();//Here is the annotation
        for (int i = 0; i < 5; i++) {
            load[i] = br.readLine();
        }
        //br.readLine();//attention: Here is to check "enter"
        br.readLine();//Here is the river !!!!!!
        for (int i = 5; i < 10; i++) {
            load[i] = br.readLine();
        }
//        String tem =br.readLine();
//        if ()
//        load[10] = tem;
        //load[10] = "";
        for (int i = 0; i < 10; i++) {
            System.out.println(load[i]);
        }

        return load;
    }


    private boolean loadCheck(String[] loadFile) {
        boolean check = true;
//        if (!(loadFile[10].isEmpty()))
//            JOptionPane.showMessageDialog(this, "more rows!");
        int[][] checkTimes = new int[7][2];
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                checkTimes[i][j] = 0;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (loadFile[i].length() > 9) {
                check = false;
                out.add("more colume!!!\n");
                break;
            }
        }

        for (int j = 0; j < 10; j++) {//check can be removed!
            for (int i = 0; i < 9; i++) {
                switch (loadFile[j].charAt(i)) {
                    case 'c':
                        checkTimes[0][0]++;
                        break;
                    case 'C':
                        checkTimes[0][1]++;
                        break;
                    case 'h':
                        checkTimes[1][0]++;
                        break;
                    case 'H':
                        checkTimes[1][1]++;
                        break;
                    case 'e':
                        checkTimes[2][0]++;
                        if (!(((i == 2 || i == 6) && j == 9) || ((i == 0 || i == 4 || i == 8) && j == 7)
                                || ((i == 2 || i == 6) && j == 5))) {
                            check = false;
                            out.add("red elephlt is not in its correct place\n");
                        }
                        break;
                    case 'E':
                        checkTimes[2][1]++;
                        if (!(((i == 2 || i == 6) && j == 0) || ((i == 0 || i == 4 || i == 8) && j == 2)
                                || (i == 2 || (i == 6) && j == 4))) {
                            check = false;
                            out.add("black elephlt is not in its correct place\n");
                        }
                        break;
                    case 'a':
                        checkTimes[3][0]++;
                        if (!(((i == 3 || i == 5) && j == 9) || (i == 4 && j == 8) || ((i == 3 || i == 5) && j == 7))) {
                            check = false;
                            out.add("red adivor is not in its correct place\n");
                        }
                        break;
                    case 'A':
                        checkTimes[3][1]++;
                        if (!(((i == 3 || i == 5) && j == 0) || (i == 4 && j == 1) || ((i == 3 || i == 5) && j == 2))) {
                            check = false;
                            out.add("black adivor is not in its correct place\n");
                        }
                        break;
                    case 'g':
                        checkTimes[4][0]++;
                        if (!(((i == 3 || i == 4 || i == 5) && j == 9) || ((i == 3 || i == 4 || i == 5) && j == 8)
                                || ((i == 3 || i == 4 || i == 5) && j == 7))) {
                            check = false;
                            out.add("red general is not in its correct place\n");
                        }
                        break;
                    case 'G':
                        checkTimes[4][1]++;
                        if (!(((i == 3 || i == 4 || i == 5) && j == 0) || ((i == 3 || i == 4 || i == 5) && j == 1)
                                || ((i == 3 || i == 4 || i == 5) && j == 2))) {
                            check = false;
                            out.add("black general is not in its correct place\n");
                        }
                        break;
                    case 'n':
                        checkTimes[5][0]++;
                        break;
                    case 'N':
                        checkTimes[5][1]++;
                        break;
                    case 's':
                        checkTimes[6][0]++;
                        if (!(j < 5 || (j < 7 && (i == 0 || i == 2 || i == 4 || i == 6 || i == 8)))) {
                            check = false;
                            out.add("red soldier is not in its correct place\n");
                        }
                        break;
                    case 'S':
                        checkTimes[6][1]++;
                        if (!(j > 4 || (j > 2 && (i == 0 || i == 2 || i == 4 || i == 6 || i == 8)))) {
                            check = false;
                            out.add("black soldier is not in its correct place\n");
                        }
                        break;
                }
            }
        }
        if (check) {
            if (checkTimes[4][0] != 1)
                out.add("loss of red general");
            if (checkTimes[4][1] != 1)
                out.add("loss of black general");
        }
        if (check) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 2; j++) {
                    if (checkTimes[i][j] > 2) {
                        check = false;
                        break;//here needs some improvement !!
                    }
                }
            }
        }
        if (check)
            out.add("successful3");
        JOptionPane.showMessageDialog(this, out.toString());
        return check;
    }

    public void setTheGame(String[] loadFile) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                switch (loadFile[i].charAt(j)) {
                    case 'c':
                        chessboard.cInitTestBoard(i, j, ChessColor.RED);
                        break;
                    case 'C':
                        chessboard.cInitTestBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'h':
                        chessboard.hInitTestBoard(i, j, ChessColor.RED);
                        break;
                    case 'H':
                        chessboard.hInitTestBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'e':
                        chessboard.eInitTestBoard(i, j, ChessColor.RED);
                        break;
                    case 'E':
                        chessboard.eInitTestBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'a':
                        chessboard.aInitTestBoard(i, j, ChessColor.RED);
                        break;
                    case 'A':
                        chessboard.aInitTestBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'g':
                        chessboard.gInitTestBoard(i, j, ChessColor.RED);
                        break;
                    case 'G':
                        chessboard.gInitTestBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'n':
                        chessboard.nInitTestBoard(i, j, ChessColor.RED);
                        break;
                    case 'N':
                        chessboard.nInitTestBoard(i, j, ChessColor.BLACK);
                        break;
                    case 's':
                        chessboard.sInitTestBoard(i, j, ChessColor.RED);
                        break;
                    case 'S':
                        chessboard.sInitTestBoard(i, j, ChessColor.BLACK);
                        break;
                }
            }
        }

    }//attention : this can be replaced by method  finalLOadGame() in ChessboardComponent!!!


    public static int[][] loadGameBySteps(String filePath) throws IOException {
        ArrayList<String> load = new ArrayList<String>();//here can be changed
        String statues;
        FileReader fr;
        fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        statues = br.readLine().subSequence(12, 13).toString();
        System.out.println(statues);
        System.out.printf("%s", br.readLine());
        System.out.println();
        System.out.printf("%s", br.readLine());
        System.out.println();
        String a;
        while ((a = br.readLine()) != null) {
            load.add(a);
        }
        int[][] finalLoad = new int[load.size()][4];
        for (int i = 0; i < load.size(); i++) {
            String[] tem = load.get(i).split(" ");
            for (int j = 0; j < tem.length; j++) {
                System.out.printf("%s", tem[j]);
                finalLoad[i][j] = Integer.parseInt(tem[j]);
            }
            System.out.println();
        }
        for (int i = 0; i < load.size(); i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(finalLoad[i][j]);
            }
            System.out.println();
        }
        br.close();
        fr.close();
        return finalLoad;
    }


    private boolean loadGameByStepsCheck(int[][] loadFile) {
        boolean check = true;//this is also a hard work!!!   come on guys!!!!   we can do it!!!!
        return check;
    }


    public static void main(String[] args) {
//        SwingUtilities.invokeLater(()->{
//            ChessGameFrame
//        });
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame();
            mainFrame.setVisible(true);

        });
    }
}