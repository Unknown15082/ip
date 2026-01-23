public class AirisException extends Exception {
    private String description;

    public AirisException(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("[Airis] %s", this.description);
    }
}
