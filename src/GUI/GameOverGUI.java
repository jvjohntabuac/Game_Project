package GUI;

import javax.swing.*;
import java.awt.*;

public class GameOverGUI extends JFrame {

    public GameOverGUI() {
        setTitle("GAME OVER");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("GAME OVER");
        title.setForeground(Color.RED);
        title.setFont(new Font("Monospaced", Font.BOLD, 48));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel("The darkness consumed OMORI...");
        msg.setForeground(Color.GRAY);
        msg.setFont(new Font("Monospaced", Font.PLAIN, 16));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retryBtn = new JButton("Try Again");
        retryBtn.setBackground(Color.DARK_GRAY);
        retryBtn.setForeground(Color.WHITE);
        retryBtn.setFont(new Font("Monospaced", Font.BOLD, 16));
        retryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        retryBtn.setMaximumSize(new Dimension(200, 40));
        retryBtn.setFocusPainted(false);
        retryBtn.setBorderPainted(false);
        retryBtn.addActionListener(e -> {
            new MainMenuGUI().setVisible(true);
            dispose();
        });

        JButton quitBtn = new JButton("Quit");
        quitBtn.setBackground(Color.DARK_GRAY);
        quitBtn.setForeground(Color.WHITE);
        quitBtn.setFont(new Font("Monospaced", Font.BOLD, 16));
        quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitBtn.setMaximumSize(new Dimension(200, 40));
        quitBtn.setFocusPainted(false);
        quitBtn.setBorderPainted(false);
        quitBtn.addActionListener(e -> System.exit(0));

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(msg);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(retryBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(quitBtn);
        panel.add(Box.createVerticalGlue());

        add(panel);
    }
}