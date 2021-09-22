package lexer;

import ast.Leaf;
import ast.Node;
import field.Field;

public class Token {

    public final TokenClass tokenClass;
    public final String data;
    public Field value;


    public Token(TokenClass tokenClass, String data) {
        this.tokenClass = tokenClass;
        this.data = data;
        this.value = null;
    }

    Token(TokenClass tokenClass, Field value) {
        this.tokenClass = tokenClass;
        this.data = "Ans";
        this.value = value;
    }


    public Node toNode() throws Exception {
        if (tokenClass == Constant.ANS) {
            assert value != null;
            return new Leaf(value, value.toString());
        } else return tokenClass.toNode(data);
    }

    void update_answer_value(Field newValue) {
        value = newValue;
    }


    @Override
    public String toString() {
        if (tokenClass.isConstant()) return tokenClass.toString() + "(" + data + ")";
        else return tokenClass.toString();
    }

}
