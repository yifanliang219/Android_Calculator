package parser;

import ast.Node;
import ast.ParseTree;
import lexer.*;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Parser {

    public ParseTree tree;
    private Queue<Token> inputQueue;
    private Queue<Token> outputQueue;
    private Stack<Token> operatorStack;

    public Parser(Tokenizer tokenizer) {
        this.inputQueue = new LinkedList<>(tokenizer.tokenList);
        this.outputQueue = new LinkedList<>();
        this.operatorStack = new Stack<>();
    }

    private int precedence(Operator op) {
        switch (op) {
            case ADD:
            case SUB:
                return 1;
            case MUL:
            case DIV:
                return 2;
            case EXP:
                return 3;
            case UNARY_PLUS:
            case UNARY_MINUS:
                return 4;
            case PRIOR_MUL:
                return 5;
            default:
                return 0;
        }
    }

    public void parse() {
        operatorStack.push(inputQueue.poll()); //poll left endmarker onto the operation stack.
        for (Token token = inputQueue.poll(); !inputQueue.isEmpty(); token = inputQueue.poll()) {
            Token topOfStack = operatorStack.peek();
            assert token != null;
            if (token.tokenClass.isConstant()) outputQueue.offer(token);
            else if (token.tokenClass.isFunction()) operatorStack.push(token);
            else if (token.tokenClass == Operator.UNARY_MINUS | token.tokenClass == Operator.UNARY_PLUS)
                operatorStack.push(token);
            else if (token.tokenClass.isOperator()) {
                while (topOfStack.tokenClass.isOperator() | topOfStack.tokenClass.isFunction()) {
                    if (topOfStack.tokenClass.isOperator()) {
                        Operator inputToken = (Operator) token.tokenClass;
                        Operator stackToken = (Operator) topOfStack.tokenClass;
                        if (precedence(stackToken) > precedence(inputToken) | (precedence(stackToken) == precedence(inputToken) && inputToken != Operator.EXP)) {
                            outputQueue.offer(operatorStack.pop());
                            topOfStack = operatorStack.peek();
                        } else break;
                    } else {
                        outputQueue.offer(operatorStack.pop());
                        topOfStack = operatorStack.peek();
                    }
                }
                operatorStack.push(token);
            } else if (token.tokenClass == Symbol.LPAR | token.tokenClass == Symbol.LVER) operatorStack.push(token);
            else if (token.tokenClass == Symbol.RPAR) {
                while (topOfStack.tokenClass != Symbol.LPAR) {
                    try {
                        outputQueue.offer(operatorStack.pop());
                        topOfStack = operatorStack.peek();
                    } catch (EmptyStackException e) {
                        throw new ArithmeticException("Error()): unresolved.");
                    }
                }
                operatorStack.pop();
            } else if (token.tokenClass == Symbol.RVER) {
                while (topOfStack.tokenClass != Symbol.LVER) {
                    try {
                        outputQueue.offer(operatorStack.pop());
                        topOfStack = operatorStack.peek();
                    } catch (EmptyStackException e) {
                        throw new ArithmeticException("Error(|): unresolved");
                    }
                }
                operatorStack.pop();
                outputQueue.offer(new Token(Function.ABS, "abs"));
            } else if (token.tokenClass == Symbol.COMMA) {
                while (topOfStack.tokenClass != Symbol.LPAR) {
                    try {
                        outputQueue.offer(operatorStack.pop());
                        topOfStack = operatorStack.peek();
                    } catch (EmptyStackException e) {
                        throw new ArithmeticException("Error(,): unresolved.");
                    }
                }
            }
        }
        for (Token operator = operatorStack.pop(); !operatorStack.isEmpty(); operator = operatorStack.pop()) {
            if (operator.tokenClass != Symbol.LPAR) outputQueue.offer(operator);
        }
        outputQueue.offer(new Token(Symbol.ENDMARKER, "")); //push an endmarker to the output queue.
        constructTree();

    }

    private void constructTree() {

        Stack<Node> nodeStack = new Stack<>();
        try {
            for (Token token = outputQueue.poll(); !outputQueue.isEmpty(); token = outputQueue.poll()) {
                assert token != null;
                Node node = token.toNode();
                while (node.capacity() != 0) {
                    node.attachChild(nodeStack.pop());
                }
                nodeStack.push(node);
            }
            tree = new ParseTree(nodeStack.pop());
            if (!nodeStack.isEmpty()) throw new Exception("Error: unresolved symbols.");

        } catch (EmptyStackException e) {
            throw new ArithmeticException("Error: unresolved symbols.");
        } catch (Exception e) {
            throw new ArithmeticException(e.getMessage());
        }

    }

}
