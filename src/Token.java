public class Token {
    public enum TokenType {
        Int,
        Float,
        Name,
        Plus,
        Minus,
        Power,
        Factorial
    }
    public final int tag;

    public Token(int t){
        this.tag = t;
    }
}
