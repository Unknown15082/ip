public class Deadline extends Task {
    private final String due;

    public Deadline(String description, String due) {
        super(description);
        this.due = due;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.due);
    }
}
