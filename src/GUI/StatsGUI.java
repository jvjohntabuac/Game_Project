package GUI;

import characters.Player;
import javax.swing.*;
import java.awt.*;

public class StatsGUI extends JFrame {

    public StatsGUI(Player omori) {
        setTitle("STATS");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("OMORI STATS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monospaced", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        addStat(panel, "Level  : " + omori.getLevel());
        addStat(panel, "HP     : " + omori.getHP() + "/" + omori.getMaxHP());
        addStat(panel, "ATK    : " + omori.getAtk());
        addStat(panel, "DEF    : " + omori.getDef());
        addStat(panel, "EXP    : " + omori.getExp() + "/" + omori.getExpToLevel());
        addStat(panel, "EMOTION: " + omori.getEmotion());

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(Color.DARK_GRAY);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Monospaced", Font.BOLD, 14));
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.addActionListener(e -> dispose());

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(closeBtn);

        add(panel);
    }

    private void addStat(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Monospaced", Font.PLAIN, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}