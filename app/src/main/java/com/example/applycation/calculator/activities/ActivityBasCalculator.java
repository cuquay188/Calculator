package com.example.applycation.calculator.activities;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.applycation.calculator.R;
import com.example.applycation.calculator.calculatorhandler.Check;
import com.example.applycation.calculator.calculatorhandler.Expression;
import com.vstechlab.easyfonts.EasyFonts;

import java.math.BigDecimal;


public class ActivityBasCalculator extends MainMenuActivity implements View.OnClickListener {


    String expressionString;

    TextView text_Result,text_smallResult;

    Button numpad[];

    Button math_Plus,math_Minus,math_Multi,math_Divide,math_Mod,math_DaoDau;

    Button action_Equal,action_CE,action_C, action_Back;

    Button math_sqrt,math_mu2,math_1chiaX;

    private boolean isInputAbletoNewExpression;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_basic);
        attachIdToView();
        attachOnClickListener();

        isInputAbletoNewExpression = true;

        expressionString="0";
        text_Result.setText("");
        text_smallResult.setText("0");
    }
    //gan View vao cac doi tuong
    public void attachIdToView(){
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/DejaVuSans.ttf");

        //text
        text_Result = (TextView)findViewById(R.id.text_Result);
        //text_Result.setTypeface(EasyFonts.freedom(this));
        text_smallResult = (TextView)findViewById(R.id.text_SmallResult);
        //text_Result.setTypeface(EasyFonts.freedom(this));

        //numpad
        numpad = new Button[11];
        numpad[0] = (Button)findViewById(R.id.button_numpad_0);
        numpad[1] = (Button)findViewById(R.id.button_numpad_1);
        numpad[2] = (Button)findViewById(R.id.button_numpad_2);
        numpad[3] = (Button)findViewById(R.id.button_numpad_3);
        numpad[4] = (Button)findViewById(R.id.button_numpad_4);
        numpad[5] = (Button)findViewById(R.id.button_numpad_5);
        numpad[6] = (Button)findViewById(R.id.button_numpad_6);
        numpad[7] = (Button)findViewById(R.id.button_numpad_7);
        numpad[8] = (Button)findViewById(R.id.button_numpad_8);
        numpad[9] = (Button)findViewById(R.id.button_numpad_9);
        numpad[10] = (Button)findViewById(R.id.button_numpad_dot);
        for(Button num:numpad)
                num.setTypeface(custom_font);

        //math
        math_Plus = (Button)findViewById(R.id.button_Math_plus);
        math_Minus = (Button)findViewById(R.id.button_Math_minus);
        math_Multi = (Button)findViewById(R.id.button_Math_multiply);
        math_Divide = (Button)findViewById(R.id.button_Math_divide);
        math_DaoDau = (Button)findViewById(R.id.button_Math_DaoDau);
        math_Mod = (Button)findViewById(R.id.button_Math_mod);

        //action
        action_Equal = (Button)findViewById(R.id.button_action_equal);
        //action_CE = (Button)findViewById(R.id.button_action_CE);
        action_C = (Button)findViewById(R.id.button_action_C);
        action_Back = (Button)findViewById(R.id.button_action_back);
        //action_C.setTypeface(custom_font);
        //action_CE.setTypeface(custom_font);
        action_Back.setTypeface(custom_font);


    }
    //gan Listener vao cac doi tuong
    public void attachOnClickListener(){
        // hung chim to ok
        //numpad
        for(int i=0;i<numpad.length;i++){
            numpad[i].setOnClickListener(this);
        }

        //math
        math_Plus.setOnClickListener(this);
        math_Minus.setOnClickListener(this);
        math_Multi.setOnClickListener(this);
        math_Divide.setOnClickListener(this);
        math_Mod.setOnClickListener(this);
        math_DaoDau.setOnClickListener(this);

        //action
        action_Equal.setOnClickListener(this);
        //action_CE.setOnClickListener(this);
        action_C.setOnClickListener(this);
        action_Back.setOnClickListener(this);


    }
    public void starColorAnimaton(View v,int color){
        int colorStar = Color.WHITE;
        int colorEnd = color;
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(
                v,"backgroundColor",colorStar,colorEnd
        );
        valueAnimator.setDuration(100);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();

    }



    //cai dat Listener
    @Override
    public void onClick(View v) {

        starColorAnimaton(v, 0xFF55AAFF);

        if(v.getId()==action_Equal.getId()){
            try {
                BigDecimal result = new Expression(expressionString).eval();
                expressionString=result+"";
                text_Result.setText(Check.scaleValue(result,6));
                isInputAbletoNewExpression = false;
            }catch (Exception ex){
                expressionString="";
                text_Result.setText("LỖI");
            }
            return;
        }
        for(Button num : numpad) {
            if (num.getId() == v.getId()) {
                if (!isInputAbletoNewExpression || expressionString.equals("0")) {
                    expressionString = "";
                }
                expressionString += num.getText();
            }
        }
        switch (v.getId()){
            case R.id.button_action_C:
                expressionString="";
                text_Result.setText("");
                break;
            case R.id.button_action_CE:
                int dem=1;
                for(int i=0;i<expressionString.length();i++){
                    if(Check.isNumber(expressionString.substring(expressionString.length()-dem)))
                        dem++;
                    else break;
                }
                expressionString=expressionString.substring(0,expressionString.length()+1-dem);
                break;
            case R.id.button_action_back:
                char charLast = expressionString.charAt(expressionString.length()-1);
                if(charLast=='(')
                    expressionString=expressionString.substring(0,expressionString.lastIndexOf("sqrt"));
                else
                    expressionString=expressionString.substring(0,expressionString.length()-1);
                break;
            case R.id.button_Math_mod:
                expressionString+="%";
                break;
            case R.id.button_Math_plus:
                expressionString+="+";
                break;
            case R.id.button_Math_minus:
                expressionString+="-";
                break;
            case R.id.button_Math_multiply:
                expressionString+="*";
                break;
            case R.id.button_Math_divide:
                expressionString+="/";
                break;
            case R.id.button_Math_DaoDau:
                expressionString+="-";
                break;
        }
        if(expressionString.equals(""))expressionString="0";
        isInputAbletoNewExpression=true;
        String lastChar = expressionString.charAt(expressionString.length()-1)+"";
        if(Check.isOperator(lastChar)&&!Check.isBrace(expressionString)){
            expressionString = expressionString.substring(0,expressionString.length()-1)+")"+lastChar;
        }
        text_smallResult.setText(Check.toViewString(expressionString));

    }
}
