package GUI;
import characters.Player;
import javax.swing.*;
import java.awt.*;
public class VictoryGUI extends JFrame {
    public VictoryGUI(Player omori, RoomSelectionGUI roomSelection) {
        setTitle("VICTORY!");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("VICTORY!");
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Monospaced", Font.BOLD, 48));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel msg = new JLabel("Enemy defeated!");
        msg.setForeground(Color.WHITE);
        msg.setFont(new Font("Monospaced", Font.PLAIN, 16));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel statsMsg = new JLabel("Level: " + omori.getLevel() + " | EXP: " + omori.getExp() + "/" + omori.getExpToLevel());
        statsMsg.setForeground(Color.LIGHT_GRAY);
        statsMsg.setFont(new Font("Monospaced", Font.PLAIN, 14));
        statsMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton continueBtn = new JButton("Continue");
        continueBtn.setBackground(Color.DARK_GRAY);
        continueBtn.setForeground(Color.WHITE);
        continueBtn.setFont(new Font("Monospaced", Font.BOLD, 16));
        continueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueBtn.setMaximumSize(new Dimension(200, 40));
        continueBtn.setFocusPainted(false);
        continueBtn.setBorderPainted(false);
        continueBtn.addActionListener(e -> { 
            roomSelection.refreshRooms();
            roomSelection.setVisible(true);
            dispose();
            //changess
        });
        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(msg);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(statsMsg);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(continueBtn);
        panel.add(Box.createVerticalGlue());
        add(panel);
    }
}