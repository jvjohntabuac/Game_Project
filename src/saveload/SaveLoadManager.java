package saveload;

import characters.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveLoadManager {
    private static final String SAVE_FILE = "omori_save.txt";

    public boolean saveGame(Player currentPlayer, int currentRoom) {
        try {
            FileWriter fw = new FileWriter(SAVE_FILE);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("player=" + currentPlayer.toSaveString());
            bw.newLine();

            bw.write("room=" + currentRoom);
            bw.newLine();

            bw.close();
            fw.close();

            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║      GAME SAVED!             ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.println("Progress saved to " + SAVE_FILE);

            return true;

        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
            return false;
        }
    }

    public int loadGame(Player currentPlayer) {
        File saveFile = new File(SAVE_FILE);

        if (!saveFile.exists()) {
            System.out.println("No save file found!");
            return -1;
        }

        try {
            FileReader fr = new FileReader(SAVE_FILE);
            BufferedReader br = new BufferedReader(fr);

            String line;
            int roomNumber = 0;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("player=")) {
                    String playerData = line.substring(7);
                    currentPlayer.loadFromString(playerData);
                } else if (line.startsWith("room=")) {
                    roomNumber = Integer.parseInt(line.substring(5));
                }
            }

            br.close();
            fr.close();

            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║      GAME LOADED!            ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.println("Welcome back " + currentPlayer.getName() + "!");
            currentPlayer.displayStats();

            return roomNumber;

        } catch (IOException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return -1;
        }
    }

    public boolean saveExists() {
        File saveFile = new File(SAVE_FILE);
        return saveFile.exists();
    }

    public void deleteSave() {
        File saveFile = new File(SAVE_FILE);

        if (saveFile.exists()) {
            saveFile.delete();
            System.out.println("Save file deleted!");
        }
    }
}
