package xyz.chengzi.cs102a.chinesechess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class INITIAL extends JFrame {
    private static JLabel Label = new JLabel();
    private static JLabel Label2 = new JLabel();

    public INITIAL() {
        setTitle("Welcome use our program");
        setSize(1000, 750);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        Label.setSize(200, 40);
        Label.setLocation(400, 300);
        Label.setFont(new Font("Arial", Font.BOLD, 30));
        Label.setText("Start Game");
        Label2.setSize(200, 40);
        Label2.setLocation(440, 400);
        Label2.setFont(new Font("Arial", Font.BOLD, 30));
        Label2.setText("Exit");
        add(Label);
        add(Label2);


        Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChessGameFrame frame = null;
                frame = new ChessGameFrame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                setVisible(false);
            }
        });
        Label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });

        JLabel one = new JLabel("");
        URL url = null;
        try {
            url = new File("C:\\Users\\G3\\Desktop\\Project Tchess\\Tchess7\\picture\\initial.png\\").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert url != null;
        Icon icon = new ImageIcon(url);
        one.setIcon(icon);
        one.setLocation(0, -250);
        one.setSize(1000, 1200);
        add(one);
    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(()->{
//            ChessGameFrame
//        });
        SwingUtilities.invokeLater(() -> {
            INITIAL mainFrame = new INITIAL();
            mainFrame.setVisible(true);

        });
    }

}