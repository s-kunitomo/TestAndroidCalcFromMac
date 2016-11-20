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

public class AnotherCalcActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener, View.OnClickListener {

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
        setContentView(R.layout.activity_another_calc);

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
        findViewById(R.id.backButton).setOnClickListener(this);
    }

    // 2つのEditTextに入力がされているかをチェックする
    private boolean checkEditTextInput() {
        // 入力内容を確認する
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();

        return !TextUtils.isEmpty(input1) && !TextUtils.isEmpty(input2);
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
        if (!checkEditTextInput()) {
            setResult(RESULT_CANCELED);
        } else {
            //
            int result = calc();

            Intent data = new Intent();
            data.putExtra("result", result);
            setResult(RESULT_OK, data);
        }

        finish();
    }
}
