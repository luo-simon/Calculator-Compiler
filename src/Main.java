import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String inp = "1236507.2391702023";

        Lexer lex = new Lexer();

        while (true) {
            Token t = lex.scan();
            System.out.println(t);
        }
    }
}