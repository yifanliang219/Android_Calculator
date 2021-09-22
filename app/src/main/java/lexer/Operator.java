package lexer;

import ast.BinaryOperator;
import ast.Node;
import ast.UnaryOperator;

public enum Operator implements TokenClass {

    UNARY_PLUS, UNARY_MINUS, ADD, SUB, MUL, PRIOR_MUL, DIV, EXP;

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return true;
    }


    @Override
    public Node toNode(String data) {
        if (this == UNARY_PLUS | this == UNARY_MINUS) return new UnaryOperator(this);
        else return new BinaryOperator(this);
    }

}
