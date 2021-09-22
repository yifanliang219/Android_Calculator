package lexer;

import field.Field;
import parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tokenizer {

    public int index = -1;
    public ArrayList<Token> tokenList = new ArrayList<>();
    private Map<String, Token> map = new HashMap<>();


    public Tokenizer() {
        setup_general_functions();
        add_endmarker();
    }

    private void add_endmarker() {
        this.tokenList.add(++index, new Token(Symbol.ENDMARKER, ""));
        this.tokenList.add(new Token(Symbol.ENDMARKER, ""));
    }

    public void del() {
        tokenList.remove(index--);
    }

    public void clear() {
        tokenList.clear();
        index = -1;
        add_endmarker();
    }

    public void add_general_function(String function_name) {
        tokenList.add(++index, map.get(function_name));
        tokenList.add(++index, new Token(Symbol.LPAR, "("));
    }

    public void add_modulus() {
        Token token = new Token(Symbol.LVER, "|");
        tokenList.add(++index, token);
        tokenList.add(++index, new Token(Symbol.RVER, "|"));
        index--;
    }

    public void add_nCr() {
        tokenList.add(++index, new Token(Function.NCR, "C"));
    }

    public void add_log() {
        Token token = new Token(Function.LOG, "log");
        tokenList.add(++index, token);
        tokenList.add(++index, new Token(Symbol.LPAR, "("));
        tokenList.add(++index, new Token(Symbol.COMMA, ","));
        tokenList.add(++index, new Token(Symbol.RPAR, ")"));
        index = index - 2;
    }

    public void add_factorial() {
        tokenList.add(++index, new Token(Function.FACTORIAL, "!"));
    }

    public void add_digit(Token digit) {
        tokenList.add(++index, digit);
    }

    public void add_e() {
        Token token = new Token(Constant.E, "e");
        tokenList.add(++index, token);
    }

    public void add_pi() {
        Token token = new Token(Constant.PI, "π");
        tokenList.add(++index, token);
    }

    public void add_i() {
        Token token = new Token(Constant.I, "i");
        tokenList.add(++index, token);
    }

    public void add_ans(Field ans) {
        Token token = new Token(Constant.ANS, ans);
        tokenList.add(++index, token);
    }

    public void add_basic_op(Token op) {
        tokenList.add(++index, op);
    }

    public void add_exp() {
        tokenList.add(++index, new Token(Operator.EXP, "^"));
        tokenList.add(++index, new Token(Symbol.LPAR, "("));
    }

    public void add_symbol(Token symbol) {
        tokenList.add(++index, symbol);
    }

    public Parser toParserStage(Field answer_value) {
        //first update answer value
        this.update_answer_value(answer_value);
        //make a copy of this tokenizer
        Tokenizer tokenizer_copy = new Tokenizer();
        tokenizer_copy.tokenList.clear();
        tokenizer_copy.tokenList.addAll(tokenList);
        tokenizer_copy.tokenize();
        //pass copy to the parser, leave original tokenizer unchanged
        return new Parser(tokenizer_copy);
    }

    private void tokenize() {
        //digits to number
        for (int i = 1; tokenList.get(i).tokenClass != Symbol.ENDMARKER; i++) {
            if (tokenList.get(i).tokenClass == Constant.DIGIT) {
                StringBuilder s = new StringBuilder();
                while (tokenList.get(i).tokenClass == Constant.DIGIT) {
                    s.append(tokenList.get(i).data);
                    tokenList.remove(i);
                }
                tokenList.add(i, new Token(Constant.NUMBER, s.toString()));
            }
        }
        //find unary ops
        int j = 1;
        for (Token current = tokenList.get(j); current.tokenClass != Symbol.ENDMARKER; current = tokenList.get(++j)) {
            if (current.tokenClass == Operator.ADD | current.tokenClass == Operator.SUB) {
                Token previous = tokenList.get(j - 1);
                if (previous.tokenClass == Symbol.LPAR | previous.tokenClass == Symbol.LVER | previous.tokenClass == Symbol.COMMA | previous.tokenClass.isOperator() |
                        previous.tokenClass.isFunction() | previous.tokenClass == Symbol.ENDMARKER) {
                    Token unary = null;
                    switch (current.data) {
                        case "+":
                            unary = new Token(Operator.UNARY_PLUS, "+");
                            break;
                        case "-":
                            unary = new Token(Operator.UNARY_MINUS, "-");
                            break;
                    }
                    tokenList.remove(j);
                    tokenList.add(j, unary);
                }
            }
        }
        //adjust missing mul
        int k = 1;
        for (Token token = tokenList.get(k); token.tokenClass != Symbol.ENDMARKER; token = tokenList.get(++k)) {
            if (isMulMissing(token, tokenList.get(k + 1))) {
                tokenList.add(k + 1, new Token(Operator.PRIOR_MUL, "x"));
            }
        }
    }


    //add missing * between
    //1. constant and e,i,π. e.g. 3π
    //2. constant and functions other than factorial or nCr. e.g. 3sin(π)
    //3. constant and (, LVER
    //4. ) and function, (, LVER, e,i,π
    //5. RVER and (, LVER, e,i,π

    private boolean isMulMissing(Token leftToken, Token rightToken) {
        if (leftToken.tokenClass.isConstant()) {
            return rightToken.tokenClass == Constant.E | rightToken.tokenClass == Constant.I | rightToken.tokenClass == Constant.PI | rightToken.tokenClass == Constant.ANS |
                    (rightToken.tokenClass.isFunction() && rightToken.tokenClass != Function.FACTORIAL && rightToken.tokenClass != Function.NCR) |
                    rightToken.tokenClass == Symbol.LPAR | rightToken.tokenClass == Symbol.LVER;
        } else if (leftToken.tokenClass == Symbol.RPAR | leftToken.tokenClass == Symbol.RVER) {
            return rightToken.tokenClass.isFunction() | rightToken.tokenClass == Symbol.LPAR | rightToken.tokenClass == Symbol.LVER |
                    rightToken.tokenClass == Constant.E | rightToken.tokenClass == Constant.I | rightToken.tokenClass == Constant.PI | rightToken.tokenClass == Constant.ANS;
        } else return false;
    }

    private void update_answer_value(Field answer_value) {
        for (int i = 1; tokenList.get(i).tokenClass != Symbol.ENDMARKER; i++) {
            if (tokenList.get(i).tokenClass == Constant.ANS) {
                tokenList.get(i).update_answer_value(answer_value);
            }
        }
    }

    private void setup_general_functions() {
        map.put("sin", new Token(Function.SIN, "sin"));
        map.put("cos", new Token(Function.COS, "cos"));
        map.put("tan", new Token(Function.TAN, "tan"));
        map.put("sin⁻¹", new Token(Function.ARCSIN, "sin⁻¹"));
        map.put("cos⁻¹", new Token(Function.ARCCOS, "cos⁻¹"));
        map.put("tan⁻¹", new Token(Function.ARCTAN, "tan⁻¹"));
        map.put("sinh", new Token(Function.SINH, "sinh"));
        map.put("cosh", new Token(Function.COSH, "cosh"));
        map.put("tanh", new Token(Function.TANH, "tanh"));
        map.put("sinh⁻¹", new Token(Function.ARCSINH, "sinh⁻¹"));
        map.put("cosh⁻¹", new Token(Function.ARCCOSH, "cosh⁻¹"));
        map.put("tanh⁻¹", new Token(Function.ARCTANH, "tanh⁻¹"));
        map.put("In", new Token(Function.IN, "In"));
    }

}
