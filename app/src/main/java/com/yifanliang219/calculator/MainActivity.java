package com.yifanliang219.calculator;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cal.Visitor;
import field.Complex;
import field.Decimal;
import field.Field;
import field.IntFraction;
import field.Real;
import lexer.Constant;
import lexer.Operator;
import lexer.Symbol;
import lexer.Token;
import lexer.Tokenizer;
import parser.Parser;

public class MainActivity extends AppCompatActivity {

    private EditText input_display;
    private TextView result_display;
    private Tokenizer tokenizer;
    private Field answer = new IntFraction(0);
    private boolean isStartingNewCal = false;
    private boolean answer_presented_in_fraction = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input_display = findViewById(R.id.input_display);
        result_display = findViewById(R.id.result_display);
        input_display.requestFocus();
        input_display.setOnTouchListener((v, event) -> true);
        result_display.setOnTouchListener((v, event) -> true);
        tokenizer = new Tokenizer();


    }

    public void click_digit(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        Token token = new Token(Constant.DIGIT, text);
        tokenizer.add_digit(token);
        insert_input(text);

    }

    public void click_e(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        tokenizer.add_e();
        insert_input(text);

    }

    public void click_i(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        tokenizer.add_i();
        insert_input(text);

    }

    public void click_pi(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        tokenizer.add_pi();
        insert_input(text);

    }

    public void click_ans(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        tokenizer.add_ans(answer);
        insert_input(text);

    }

    public void click_general_function(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        tokenizer.add_general_function(text);
        insert_input(text + "(");

    }

    public void click_factorial(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        tokenizer.add_factorial();
        insert_input(text);

    }

    public void click_log(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        tokenizer.add_log();
        insert_input("log(,)");
        move_cursor_left(2);

    }

    public void click_nCr(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        tokenizer.add_nCr();
        insert_input("C");

    }

    public void click_modulus(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        tokenizer.add_modulus();
        insert_input("||");
        move_cursor_left(1);

    }

    public void click_basic_op(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        Token op = null;
        switch (text) {
            case "+":
                op = new Token(Operator.ADD, "+");
                break;
            case "-":
                op = new Token(Operator.SUB, "-");
                break;
            case "*":
                op = new Token(Operator.MUL, "*");
                break;
            case "/":
                op = new Token(Operator.DIV, "/");
                break;
            case "^":
                op = new Token(Operator.EXP, "^");
                break;
        }
        tokenizer.add_basic_op(op);
        insert_input(text);

    }

    public void click_exp(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        tokenizer.add_exp();
        insert_input(text + "(");

    }

    public void click_symbol(View v) {
        if (isStartingNewCal) {
            reset();
            isStartingNewCal = false;
        }
        Button btn = (Button) v;
        String text = btn.getText().toString();
        Token symbol = null;
        switch (text) {
            case "(":
                symbol = new Token(Symbol.LPAR, "(");
                break;
            case ")":
                symbol = new Token(Symbol.RPAR, ")");
                break;
            case ",":
                symbol = new Token(Symbol.COMMA, ",");
                break;
        }
        tokenizer.add_symbol(symbol);
        insert_input(text);

    }

    private void reset() {
        input_display.getText().clear();
        tokenizer.clear();
    }

    private void insert_input(String text) {
        input_display.getText().insert(input_display.getSelectionStart(), text);
    }

    private Token peek_cursor_left() {
        return tokenizer.tokenList.get(tokenizer.index);
    }

    private Token peek_cursor_right() {

        return tokenizer.tokenList.get(tokenizer.index + 1);
    }

    private void move_cursor_left(int l) {

        input_display.setSelection(input_display.getSelectionStart() - l);
    }

    private void move_cursor_right(int l) {

        input_display.setSelection(input_display.getSelectionStart() + l);
    }

    public void click_left_arrow(View v) {
        isStartingNewCal = false;
        int token_length = peek_cursor_left().data.length();
        move_cursor_left(token_length);
        if (token_length != 0) {
            tokenizer.index--;
        }

    }

    public void click_right_arrow(View v) {
        isStartingNewCal = false;
        int token_length = peek_cursor_right().data.length();
        move_cursor_right(token_length);
        if (token_length != 0) {
            tokenizer.index++;
        }

    }

    public void click_del(View v) {
        isStartingNewCal = false;
        int token_length = peek_cursor_left().data.length();
        int cur_pos = input_display.getSelectionStart();
        input_display.setText(input_display.getText().delete(cur_pos - token_length, cur_pos));
        input_display.setSelection(cur_pos - token_length);
        if (token_length != 0) {
            tokenizer.del();
        }

    }

    public void click_ac(View v) {
        reset();
    }


    public void click_equal(View v) {
        if(tokenizer.tokenList.size() == 2) return;
        try {
            Parser parser = tokenizer.toParserStage(answer);
            parser.parse();
            answer = parser.tree.root.accept(new Visitor());
            answer_presented_in_fraction = true;
            result_display.setTextColor(Color.BLACK);
            result_display.setText(answer.toString());
        } catch (Exception e) {
            String message = e.getMessage();
            result_display.setTextColor(Color.RED);
            result_display.setText(message);
        }finally {
            isStartingNewCal = true;
        }

    }

    private String answer_in_decimal(){
        Real re = answer.real();
        Real im = answer.imag();
        Decimal dec_re = new Decimal(re.value());
        Decimal dec_im = new Decimal(im.value());
        return new Complex(dec_re, dec_im).toString();
    }

    public void click_switch(View v){
        if(result_display.getCurrentTextColor() == Color.RED) return;
        if(answer_presented_in_fraction){
            result_display.setText(answer_in_decimal());
            answer_presented_in_fraction = false;
        }else{
            result_display.setText(answer.toString());
            answer_presented_in_fraction = true;
        }
    }


}