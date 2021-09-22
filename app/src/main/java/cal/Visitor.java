package cal;

import ast.BinaryFunction;
import ast.BinaryOperator;
import ast.Leaf;
import ast.UnaryFunction;
import ast.UnaryOperator;
import field.Field;

public class Visitor {

    public Field visit(BinaryFunction binaryFunction) {
        return new BinaryFunctionCal(binaryFunction.function, binaryFunction.leftChild.accept(this), binaryFunction.rightChild.accept(this)).cal();
    }

    public Field visit(BinaryOperator binaryOperator) {
        return new BinaryOperatorCal(binaryOperator.operator, binaryOperator.leftChild.accept(this), binaryOperator.rightChild.accept(this)).cal();
    }

    public Field visit(UnaryFunction unaryFunction) {
        return new UnaryFunctionCal(unaryFunction.function, unaryFunction.child.accept(this)).cal();
    }

    public Field visit(UnaryOperator unaryOperator) {
        return new UnaryOperatorCal(unaryOperator.operator, unaryOperator.child.accept(this)).cal();
    }

    public Field visit(Leaf leaf) {
        return leaf.value;
    }

}
