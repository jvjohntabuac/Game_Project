package GUI;

import characters.Player;
import characters.Enemy;
import characters.Boss;
import items.Item;
import rooms.Room;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class RoomSelectionGUI extends JFrame {

    private Player omori;
    private ArrayList<Room> rooms;

    public RoomSelectionGUI(Player omori) {
        this.omori = omori;
        this.rooms = setupRooms();

        setTitle("WHITE SPACE - Room Selection");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("WHERE WILL YOU GO?");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Monospaced", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        for (Room room : rooms) {
            JButton roomBtn = createRoomButton(room);
            mainPanel.add(roomBtn);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton statsBtn = createButton("Check Stats");
        JButton inventoryBtn = createButton("Check Inventory");

        statsBtn.addActionListener(e -> {
            new StatsGUI(omori).setVisible(true);
        });

        inventoryBtn.addActionListener(e -> {
            new InventoryGUI(omori).setVisible(true);
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(statsBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(inventoryBtn);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getViewport().setBackground(Color.BLACK);
        add(scrollPane);
    }

    private JButton createRoomButton(Room room) {
        String status = room.isCleared() ? "✓ CLEARED" : "NOT CLEARED";
        JButton btn = new JButton(room.getName() + " - " + room.getDangerStars() + " [" + status + "]");
        btn.setBackground(room.isCleared() ? new Color(30, 60, 30) : Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(500, 50));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.addActionListener(e -> {
            new BattleGUI(omori, room, this).setVisible(true);
        });

        return btn;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    private ArrayList<Room> setupRooms() {
        ArrayList<Room> roomList = new ArrayList<>();

        Room forest = new Room("Room 1 - The Dark Forest", "Trees that whisper your name...", "Eerie and dark", 1, false);
        forest.addEnemy(new Enemy("MEWO", 40, 6, 2, 30, "SHADOW"));
        forest.addEnemy(new Enemy("SHADOW CAT", 35, 5, 3, 25, "SHADOW"));
        forest.addLoot(new Item("Juice Box", "Restores 20 HP", "heal", 20));
        roomList.add(forest);

        Room library = new Room("Room 2 - The Lost Library", "Books that should not be read...", "Mysterious and quiet", 2, false);
        library.addEnemy(new Enemy("LOST WORDS", 60, 10, 5, 50, "PHANTOM"));
        library.addEnemy(new Enemy("LIBRARIAN", 80, 12, 6, 60, "PHANTOM"));
        library.addLoot(new Item("Candy", "Boosts ATK by 3", "atkBoost", 3));
        roomList.add(library);

        Room castle = new Room("Room 3 - The Sunken Castle", "Something ancient waits inside...", "Dark and overwhelming", 3, true);
        castle.addEnemy(new Boss("SOMETHING", 200, 20, 10, 150, "DARK GRASP"));
        castle.addLoot(new Item("Something's Essence", "Boosts DEF by 10", "defBoost", 10));
        roomList.add(castle);

        return roomList;
    }
}