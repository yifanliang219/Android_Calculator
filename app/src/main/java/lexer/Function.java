package lexer;

import ast.BinaryFunction;
import ast.Node;
import ast.UnaryFunction;

public enum Function implements TokenClass {

    SIN, COS, TAN, ARCSIN, ARCCOS, ARCTAN, //trigonometric
    SINH, COSH, TANH, ARCSINH, ARCCOSH, ARCTANH, //hyperbolic
    ABS, NCR, IN, LOG, FACTORIAL;


    @Override
    public boolean isFunction() {
        return true;
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
    public Node toNode(String data) {
        if (this == LOG | this == NCR) return new BinaryFunction(this);
        else return new UnaryFunction(this);
    }

}
