package GUI;

import characters.Player;
import items.Item;
import javax.swing.*;
import java.awt.*;

public class InventoryGUI extends JFrame {

    public InventoryGUI(Player omori) {
        setTitle("INVENTORY");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("INVENTORY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monospaced", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        if (omori.getInventory().isEmpty()) {
            JLabel empty = new JLabel("Inventory is empty!");
            empty.setForeground(Color.GRAY);
            empty.setFont(new Font("Monospaced", Font.PLAIN, 14));
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(empty);
        } else {
            for (int i = 0; i < omori.getInventory().getSize(); i++) {
                Item item = omori.getInventory().getItem(i);
                JLabel itemLabel = new JLabel("• " + item.getName() + " - " + item.getDescription());
                itemLabel.setForeground(Color.WHITE);
                itemLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
                itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(itemLabel);
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

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
}