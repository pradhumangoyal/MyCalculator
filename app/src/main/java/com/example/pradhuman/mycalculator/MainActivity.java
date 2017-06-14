package com.example.pradhuman.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    DecimalFormat formatter = new DecimalFormat("###########.###");
    String display;
    TextView screen;
    Button button;
    String operand;
    Double operand_1;
    Double operand_2;
    boolean flag;
    boolean isDot;
    boolean isEqual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView)findViewById(R.id.textView);
        isEqual = false;
        flag = false;
        isDot = false;
        button = (Button)findViewById(R.id.bt_sign);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                operand = "";
                display = "";

                updateScreen();
                isDot = false;
                return true;
            }
        });
    }
    public void updateScreen(){

        screen.setText(display);
    }

    public void clickNumber(View v){
        button = (Button)v;
        if(isEqual)
        {
            isEqual = false;
            display = "";
            updateScreen();
        }
        if(display!=null&&!display.isEmpty()&&display.equals("Infinity")) {
            display="";
            operand="";

            updateScreen();
            isDot = false;
        }
        if(v.getId()==R.id.bt_dot)
        {
            Log.i("Dot entered: ",display+"");
            if(display==null)
                return;
            if(display.isEmpty())
                return;
            if(display.substring(display.length()-1).equals("."))
                return;
            char c = display.charAt(display.length()-1);
            if(c>='0'&&c<='9')
                ;
            else
                return;
            if(isDot)
                return;
            isDot = true;
        }
        display = screen.getText() + button.getText().toString() ;
        updateScreen();

    }
    public void clickOperation(View v){
        button = (Button)v;
        updateScreen();
        if(isEqual)
            isEqual = false;
        Log.d("flag  ",flag+ "");
        if(flag)
        {
            operand = "";
            display = "";
            updateScreen();
            flag = false;
        }
        if(screen.getText()==""||display.equals(""))
            return;
        if(display.equals("Infinity"))
            return;
        Log.d("MainaCTIVITY",button.getText().toString());
        if(operand!=null && !operand.isEmpty())
        {
            Log.d("operand",operand);
            String[] operate = display.split(Pattern.quote(operand));
            if(operate.length==1)
            {
                if (display.equals("Infinity"))
                {
                    display= "";
                    updateScreen();
                    return;
                }
                display =  display.substring(0,display.length()-1) + button.getText().toString() ;
                Log.d("operate,lennght===1",display);
                updateScreen();
                return;
            }
            else {
                String result = ArithmeticOperation(operate[0], operate[1], operand);
                if(!result.equals("Infinity")){
                Double dle = new Double(result.valueOf(result));
                result = formatter.format(dle);}
                display = result + "";
                updateScreen();
                Log.d("clickOperation", " !operand,isEmpty" + " 1");
            }
        }
        isDot = false;
        operand = button.getText().toString();
        Log.d("MainaCTIVITY",operand);
        display = screen.getText() + button.getText().toString();
        updateScreen();
    }
    private String ArithmeticOperation(String A,String B, String C)
    {
        if(A.equals("")||C.equals(""))
            return "";
        isDot = false;
        operand_1 = Double.parseDouble(A);
        operand_2 = Double.parseDouble(B);

        Log.d("Operand1", Double.toString(operand_1 + operand_2));
        switch(C)
        {
            case "+":
                return (Double.toString(operand_1 + operand_2));
            case "-":
                return (Double.toString(operand_1 - operand_2));
            case "/":
                try {
                    return (Double.toString(operand_1 / operand_2));
                }
                catch(Exception e) {
                    Log.d("Divide", e.getMessage());
                    return "INF";
                }
            case "*":
                return (Double.toString(operand_1 * operand_2));
        }
        return "";

    }
    public void clickEqual(View v){
        String[] operate = display.split(Pattern.quote(operand));
        if(operate.length==1)
            return;
        String result = ArithmeticOperation(operate[0],operate[1],operand);
        if(!result.equals("Infinity")){
        Double dle = new Double(result.valueOf(result));
        result = formatter.format(dle);}
        Log.d("result =  ",operate[0] + " " + operate[1] + " " + operand);
        display = display+ "\n" + result;
        updateScreen();
        display = result;
        operand ="";
        if(display.equals("Infinity"))
            flag = true;
        isDot = false;
        isEqual  = true;
        //  display="";
    }
    public void clickClear(View v){
        operand = "";
        display = "";

        updateScreen();
        isDot = false;
    }
    public void clickPercent(View v){
        button = (Button) v;
        if(flag)
        {
            operand = "";
            display = "";
            updateScreen();
            flag = false;
        }
        if(display==null)
            return;
        if(display.isEmpty())
            return;
        if(operand!=null && !operand.isEmpty())
        {
            Log.d("operand",operand);
            String[] operate = display.split(Pattern.quote(operand));
            if(operate.length==1)
            {
                if (display.equals("Infinity"))
                {
                    display= "";
                    updateScreen();
                    return;
                }
                operand_1 = Double.parseDouble(display.substring(0,display.length()-1));
                operand_1 /=100 ;
                display = operand_1+"";
                Log.d("operate,lennght===1",display);
                updateScreen();
                return;
            }
            else {
                String result = ArithmeticOperation(operate[0], operate[1], operand);
                if(!result.equals("Infinity")){
                Double dle = new Double(result.valueOf(result));
                result = formatter.format(dle);}
                display = result + "";
                updateScreen();
                Log.d("clickOperation", " !operand,isEmpty" + " 1");
            }
        }
        operand_1 = Double.parseDouble(display);
        operand_1 /= 100;
        display = operand_1+"";
        updateScreen();

    }
    public void clickDEL(View v){
        if(display==null)
            return;
        if(display.isEmpty())
            return;
        if(flag)
        {
            operand = "";
            display = "";
            updateScreen();
            flag = false;
        }
        display = display.substring(0,display.length()-1);
        updateScreen();
    }
}
