public class Airis {
    public static void main(String[] args) {
        String helloMessage = """
        Hello! I'm Airis! Nice to meet you!
        What can I do for you?""";

        String byeMessage = """
        See you next time!""";

        String hline = "_".repeat(30);

        System.out.println(hline + "\n"
                + helloMessage + "\n"
                + hline + "\n"
                + byeMessage + "\n"
                + hline + "\n");
    }
}
