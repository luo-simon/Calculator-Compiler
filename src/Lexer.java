import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;

public class Lexer {
    public static int line = 1;
    char peek = ' ';
    HashMap<String, Word> words = new HashMap<>(); // Maps lexemes to tokens

    void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Lexer() {
        reserve(new Word(Tag.PLUS, "+"));
        reserve(new Word(Tag.MINUS, "-"));
        reserve(new Word(Tag.COS, "cos"));
        reserve(new Word(Tag.HAT, "^"));
        reserve(new Word(Tag.FACTORIAL, "!"));
    }

    public Token scan() throws IOException {

        // Skip whitespace
        for (; ; peek = (char)System.in.read() ) {
            if (peek == ' ' || peek == '\t') continue;
            else if (peek == '\n') line += 1;
            else break;
        }

        switch (peek) {
            case '+':
                peek = ' ';
                return new Token(Tag.PLUS);
            case '-':
                peek = ' ';
                return new Token(Tag.MINUS);
            case '^':
                peek = ' ';
                return new Token(Tag.HAT);
            case '!':
                peek = ' ';
                return new Token(Tag.FACTORIAL);
        }

        // Grouping digits into integers
        if (Character.isDigit(peek)) {
            int v = 0;

            do {
                v = 10*v + Character.digit(peek, 10);
                peek = (char)System.in.read();
            } while (Character.isDigit(peek));

            if (peek != '.') return new Num(v);

            float x = v;
            float d = 10;
            for (;;) {
                peek = (char)System.in.read();
                if (!Character.isDigit(peek)) break;
                x = x + Character.digit(peek, 10)/d;
                d *= 10;
            }
            return new Real(x);
        }

        // Distinguishing keywords from identifiers
        if(Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                peek = (char)System.in.read();
            } while (Character.isLetter(peek));
            String s = b.toString();
            Word w = (Word)words.get(s);
            if (w != null) return w;
        }

        Token t = new Token(peek);
        peek = ' ';
        return t;
    }

}
