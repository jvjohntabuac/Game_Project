package GUI;

import characters.Player;
import characters.Enemy;
import rooms.Room;
import items.Item;
import javax.swing.*;
import java.awt.*;

public class BattleGUI extends JFrame {

    private Player omori;
    private Enemy enemy;
    private Room room;
    private RoomSelectionGUI roomSelection;

    private JLabel playerHPLabel;
    private JLabel enemyHPLabel;
    private JTextArea battleLog;

    public BattleGUI(Player omori, Enemy enemy, Room room, RoomSelectionGUI roomSelection) {
        this.omori = omori;
        this.enemy = enemy;
        this.room = room;
        this.roomSelection = roomSelection;

        setTitle("BATTLE - " + enemy.getName());
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());

        // Status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.BLACK);
        statusPanel.setLayout(new GridLayout(2, 2));

        JLabel playerName = new JLabel("OMORI");
        playerName.setForeground(Color.WHITE);
        playerName.setFont(new Font("Monospaced", Font.BOLD, 16));

        JLabel enemyName = new JLabel(enemy.getName());
        enemyName.setForeground(Color.RED);
        enemyName.setFont(new Font("Monospaced", Font.BOLD, 16));

        playerHPLabel = new JLabel("HP: " + omori.getHP() + "/" + omori.getMaxHP());
        playerHPLabel.setForeground(Color.GREEN);
        playerHPLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        enemyHPLabel = new JLabel("HP: " + enemy.getHP() + "/" + enemy.getMaxHP());
        enemyHPLabel.setForeground(Color.GREEN);
        enemyHPLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        statusPanel.add(playerName);
        statusPanel.add(enemyName);
        statusPanel.add(playerHPLabel);
        statusPanel.add(enemyHPLabel);

        // Battle log
        battleLog = new JTextArea();
        battleLog.setBackground(Color.BLACK);
        battleLog.setForeground(Color.WHITE);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 13));
        battleLog.setEditable(false);
        battleLog.setText("A wild " + enemy.getName() + " appears!\n");

        JScrollPane logScroll = new JScrollPane(battleLog);
        logScroll.getViewport().setBackground(Color.BLACK);

        // Action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JButton attackBtn = createButton("Attack");
        JButton itemBtn = createButton("Use Item");
        JButton emotionBtn = createButton("Change Emotion");
        JButton runBtn = createButton("Run");

        attackBtn.addActionListener(e -> handleAttack());
        itemBtn.addActionListener(e -> handleItem());
        emotionBtn.addActionListener(e -> handleEmotion());
        runBtn.addActionListener(e -> handleRun());

        buttonPanel.add(attackBtn);
        buttonPanel.add(itemBtn);
        buttonPanel.add(emotionBtn);
        buttonPanel.add(runBtn);

        mainPanel.add(statusPanel, BorderLayout.NORTH);
        mainPanel.add(logScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleAttack() {
        omori.attack(enemy);
        battleLog.append("OMORI attacks " + enemy.getName() + " for " + omori.getAtk() + " damage!\n");
        updateHP();

        if (!enemy.isAlive()) {
            battleLog.append(enemy.getName() + " was defeated!\n");
            Item loot = room.getRandomLoot();
            if (loot != null) {
                omori.getInventory().addItem(loot);
                battleLog.append("Found " + loot.getName() + "!\n");
            }
            JOptionPane.showMessageDialog(this, enemy.getName() + " defeated!");
            new VictoryGUI(omori, roomSelection).setVisible(true);
            dispose();
            return;
        }

        enemyTurn();
    }

    private void handleItem() {
        if (omori.getInventory().isEmpty()) {
            battleLog.append("No items!\n");
            return;
        }

        String[] items = new String[omori.getInventory().getSize()];
        for (int i = 0; i < omori.getInventory().getSize(); i++) {
            items[i] = omori.getInventory().getItem(i).getName();
        }

        String chosen = (String) JOptionPane.showInputDialog(this,
                "Choose item:", "Use Item",
                JOptionPane.PLAIN_MESSAGE, null, items, items[0]);

        if (chosen != null) {
            for (int i = 0; i < omori.getInventory().getSize(); i++) {
                if (omori.getInventory().getItem(i).getName().equals(chosen)) {
                    omori.useItem(i);
                    battleLog.append("Used " + chosen + "!\n");
                    updateHP();
                    break;
                }
            }
            enemyTurn();
        }
    }

    private void handleEmotion() {
        String[] emotions = {"HAPPY", "SAD", "ANGRY", "AFRAID", "NEUTRAL"};
        String chosen = (String) JOptionPane.showInputDialog(this,
                "Choose emotion:", "Change Emotion",
                JOptionPane.PLAIN_MESSAGE, null, emotions, emotions[0]);

        if (chosen != null) {
            omori.changeEmotion(chosen);
            battleLog.append("OMORI feels " + chosen + "!\n");
        }
    }

    private void handleRun() {
        battleLog.append("OMORI ran away!\n");
        roomSelection.setVisible(true);
        dispose();
    }

    private void enemyTurn() {
        if (enemy.isAlive()) {
            enemy.attack(omori);
            battleLog.append(enemy.getName() + " attacks OMORI!\n");
            updateHP();

            if (!omori.isAlive()) {
                battleLog.append("OMORI has fallen...\n");
                JOptionPane.showMessageDialog(this, "OMORI was defeated...");
                new GameOverGUI().setVisible(true);
                dispose();
            }
        }
    }

    private void updateHP() {
        playerHPLabel.setText("HP: " + omori.getHP() + "/" + omori.getMaxHP());
        enemyHPLabel.setText("HP: " + enemy.getHP() + "/" + enemy.getMaxHP());
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}