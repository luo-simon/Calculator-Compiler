import java.io.IOException;
import java.util.HashMap;

public class Lexer {
    public static int line = 1;
    char peek = ' ';
    HashMap<String, Word> words = new HashMap<>();

    void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Lexer() {
        reserve(new Word(Tag.TRUE, "true"));
    }

    public Token scan() throws IOException {
        for (; ; peek = (char)System.in.read() ) {
            if (peek == ' ' || peek == '\t') continue;
            else if (peek == '\n') line += 1;
            else break;
        }

        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = 10*v + Character.digit(peek, 10);
                peek = (char)System.in.read();
            } while (Character.isDigit(peek));
            return new Num(v);
        }

        if(Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                peek = (char)System.in.read();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            Word w = (Word)words.get(s);
            if (w != null) return w;
            w = new Word (Tag.ID, s);
            words.put(s, w);
            return w;
        }

        Token t = new Token(peek);
        peek = ' ';
        return t;
    }

}
