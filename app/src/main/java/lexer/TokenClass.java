package lexer;

import ast.Node;

public interface TokenClass {

    boolean isFunction();

    boolean isConstant();

    boolean isOperator();

    Node toNode(String data) throws Exception;

}
