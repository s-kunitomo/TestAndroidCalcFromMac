package com.shoji.testandroidcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_CODE_ANOTHER_CALC_1 = 1;
    private static final int REQUEST_CODE_ANOTHER_CALC_2 = 2;

    // 上のEditText
    private EditText numberInput1;
    // 下のEditText
    private EditText numberInput2;
    // 演算子選択用のSpinner
    private Spinner operatorSelector;
    // 計算結果表示用のTextView
    private TextView calcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 上のEditText
        numberInput1 = (EditText) findViewById(R.id.numberInput1);
        // 上のEditTextの文字入力イベントを受け取る
        numberInput1.addTextChangedListener(this);
        // 下のEditText
        numberInput2 = (EditText) findViewById(R.id.numberInput2);
        // 下のEditTextの文字入力イベントを受け取る
        numberInput2.addTextChangedListener(this);

        // 演算子選択用Spinneer
        operatorSelector = (Spinner) findViewById(R.id.operatorSelector);
        //
        calcResult = (TextView) findViewById(R.id.calcResult);

        //
        findViewById(R.id.calcButton1).setOnClickListener(this);
        findViewById(R.id.calcButton2).setOnClickListener(this);
        findViewById(R.id.nextButton).setOnClickListener(this);
    }

    // 2つのEditTextに入力がされているかをチェックする
    private boolean checkEditTextInput() {
        // 入力内容を確認する
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();

        return !TextUtils.isEmpty(input1) &&!TextUtils.isEmpty(input2);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        refreshResult();
    }

    private void refreshResult() {
        if (checkEditTextInput()) {
            int result = calc();

            String resultText = getString(R.string.calc_result_text, result);
            calcResult.setText(resultText);
        } else {
            calcResult.setText(R.string.calc_result_default);
        }
    }

    private int calc() {
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();

        int number1 = Integer.parseInt(input1);
        int number2 = Integer.parseInt(input2);

        int operator = operatorSelector.getSelectedItemPosition();

        switch (operator) {
            case 0:
                return number1 + number2;
            case 1:
                return number1 - number2;
            case 2:
                return number1 * number2;
            case 3:
                return number1 / number2;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.calcButton1:
                Intent intent1 = new Intent(this, AnotherCalcActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_ANOTHER_CALC_1);
                break;
            case R.id.calcButton2:
                Intent intent2 = new Intent(this, AnotherCalcActivity.class);
                startActivityForResult(intent2, REQUEST_CODE_ANOTHER_CALC_2);
                break;
            case R.id.nextButton:
                if (checkEditTextInput()) {
                    //
                    int result = calc();
                    numberInput1.setText(String.valueOf(result));
                    refreshResult();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        super.onActivityResult(requestCode, resultCode, data);

        //
        if (resultCode != RESULT_OK) return;

        //
        Bundle resultBundle = data.getExtras();

        //
        if (!resultBundle.containsKey("result")) return;;

        //
        int result = resultBundle.getInt("result");

        if (requestCode == REQUEST_CODE_ANOTHER_CALC_1) {
            numberInput1.setText(String.valueOf(result));
        } else if (requestCode == REQUEST_CODE_ANOTHER_CALC_2) {
            numberInput2.setText(String.valueOf(result));
        }

        refreshResult();
    }
}
