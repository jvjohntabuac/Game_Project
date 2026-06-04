package interfaces;

public interface Saveable {
    String toSaveString();
    void loadFromString(String data);
}
