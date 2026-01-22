import java.util.Objects;
import java.util.Scanner;

public class Airis {
    private static final String helloMessage = """
        Hello! I'm Airis! Nice to meet you!
        What can I do for you?""";

    private static final String byeMessage = """
        See you next time!""";

    public static void main(String[] args) {
        System.out.println(wrapMessage(helloMessage));

        Scanner input = new Scanner(System.in);
        while (true) {
            String command = input.nextLine();
            if (command.equals("bye")) {
                System.out.println(wrapMessage(byeMessage));
                break;
            } else {
                System.out.println(wrapMessage(command));
            }
        }
    }

    static String wrapMessage(String msg) {
        String hline = "_".repeat(50);
        return hline + "\n" + msg + "\n" + hline;
    }
}
