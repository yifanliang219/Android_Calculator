package cal;

import field.Field;
import function.Combinatorics;
import function.Hyperbolic;
import function.Logarithms;
import function.Trigonometric;
import lexer.Function;

public class UnaryFunctionCal implements Cal {

    private Function unaryFunction;
    private Field arg;

    public UnaryFunctionCal(Function unaryFunction, Field arg) {
        this.unaryFunction = unaryFunction;
        this.arg = arg;
    }

    @Override
    public Field cal() {
        switch (unaryFunction) {
            case SIN:
                return Trigonometric.sin(arg);
            case COS:
                return Trigonometric.cos(arg);
            case TAN:
                return Trigonometric.tan(arg);
            case ARCSIN:
                return Trigonometric.arcsin(arg);
            case ARCCOS:
                return Trigonometric.arccos(arg);
            case ARCTAN:
                return Trigonometric.arctan(arg);
            case SINH:
                return Hyperbolic.sinh(arg);
            case COSH:
                return Hyperbolic.cosh(arg);
            case TANH:
                return Hyperbolic.tanh(arg);
            case ARCSINH:
                return Hyperbolic.arcsinh(arg);
            case ARCCOSH:
                return Hyperbolic.arccosh(arg);
            case ARCTANH:
                return Hyperbolic.arctanh(arg);
            case ABS:
                return arg.modulus();
            case IN:
                return Logarithms.In(arg);
            case FACTORIAL:
                return Combinatorics.factorial(arg);
            default:
                throw new ArithmeticException("Illegal arguments.");
        }
    }

}
