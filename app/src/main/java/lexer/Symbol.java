package lexer;

import ast.Node;

public enum Symbol implements TokenClass {

    LPAR, RPAR, LVER, RVER, COMMA, ENDMARKER;

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
        return false;
    }


    @Override
    public Node toNode(String data) throws Exception {
        throw new Exception("Syntax Error: unresolved symbol.");
    }

}
