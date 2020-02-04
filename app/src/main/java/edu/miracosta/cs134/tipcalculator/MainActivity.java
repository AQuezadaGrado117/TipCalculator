package edu.miracosta.cs134.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Instance variable
    // Bridge the View and Model
    private edu.miracostacollege.cs134.tipcalculator.Bill currentbill;

    private EditText amountTextView;
    private TextView percentTextView;
    private SeekBar percentSeekBar;
    private TextView tipTextView;
    private TextView totalTextView;

    // Instance variables to format output (currency percent)
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // "Wire up" instance variables (initialize them)
        currentbill = new edu.miracostacollege.cs134.tipcalculator.Bill();
        amountTextView = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentSeekBar = findViewById(R.id.percentSeekBar);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);


        // Let's set the current tip percent
        currentbill.setTipPercent(percentSeekBar.getProgress() / 100);

        // Implement editText
        amountTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Read input from amountEditText (View) and store in currentBill (Model)
                try {
                    double amount = Double.parseDouble(amountTextView.getText().toString());
                    // Store amount in Bill
                    currentbill.setAmount(amount);
                } catch (NumberFormatException e)
                {
                    currentbill.setAmount(0.0);
                }
                calculateBill();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // implement the interface
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the tip percent
                currentbill.setTipPercent(percentSeekBar.getProgress() / 100.0);
                // Update the view
                percentTextView.setText(percent.format(currentbill.getTipPercent()));
                calculateBill();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        }

    public void calculateBill()
    {
        // Update hte Tip Text view and total view
        tipTextView.setText(currency.format(currentbill.getTipAmount()));
        totalTextView.setText((currency.format(currentbill.getTotalAmount())));
    }

}
