public class AirisException extends Exception {
    public AirisException(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("[Airis] %s", super.getMessage());
    }

    public String getAirisMessage() {
        return String.format("Oh no, an error has occurred!" +
                "\n\t%s", super.getMessage());
    }
}
