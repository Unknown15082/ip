import java.util.ArrayList;

public class Storage<T> {
    private final ArrayList<T> memory;

    public Storage() {
        memory = new ArrayList<>(120);
    }

    public void add(T item) {
        memory.add(item);
    }

    public T get(int idx) {
        return memory.get(idx);
    }

    public String getAllAsString() throws IndexOutOfBoundsException {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < memory.size(); i++) {
            T item = memory.get(i);
            str.append(String.format("%d: %s", i+1, item.toString()));
            if (i < memory.size() - 1) str.append("\n");
        }
        return str.toString();
    }
}
