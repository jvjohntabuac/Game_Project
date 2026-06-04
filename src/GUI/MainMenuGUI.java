package GUI;

import characters.Player;
import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {

    public MainMenuGUI() {
        setTitle("OMORI - WHITE SPACE RPG");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("O M O R I");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monospaced", Font.BOLD, 48));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("WHITE SPACE RPG");
        subtitle.setForeground(Color.LIGHT_GRAY);
        subtitle.setFont(new Font("Monospaced", Font.PLAIN, 18));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newGameBtn = createButton("New Game");
        JButton loadGameBtn = createButton("Load Game");
        JButton quitBtn = createButton("Quit");

        newGameBtn.addActionListener(e -> {
            Player omori = new Player("OMORI");
            new RoomSelectionGUI(omori).setVisible(true);
            dispose();
        });

        loadGameBtn.addActionListener(e -> {
            Player omori = new Player("OMORI");
            new RoomSelectionGUI(omori).setVisible(true);
            dispose();
        });

        quitBtn.addActionListener(e -> System.exit(0));

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(newGameBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loadGameBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(quitBtn);
        panel.add(Box.createVerticalGlue());

        add(panel);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 16));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenuGUI().setVisible(true);
        });
    }
}