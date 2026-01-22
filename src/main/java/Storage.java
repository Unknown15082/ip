import java.util.ArrayList;

public class Storage {
    private final ArrayList<String> memory;

    public Storage() {
        memory = new ArrayList<>(120);
    }

    public void add(String item) {
        memory.add(item);
    }

    public String getAllAsString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < memory.size(); i++) {
            String item = memory.get(i);
            str.append(i + 1);
            str.append(": ");
            str.append(item);
            if (i < memory.size() - 1) str.append("\n");
        }
        return str.toString();
    }
}
