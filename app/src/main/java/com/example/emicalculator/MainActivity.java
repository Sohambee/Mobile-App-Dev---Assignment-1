package com.example.emicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize all input fields.
        EditText mortgage = (EditText)findViewById(R.id.mortgageAmount);

        Spinner interest = (Spinner) findViewById(R.id.interestRate);

        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(this, R.array.interest_rate, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interest.setAdapter(interestAdapter);

        Spinner authorization = (Spinner) findViewById(R.id.authPeriod);

        ArrayAdapter<CharSequence> authAdapter = ArrayAdapter.createFromResource(this, R.array.auth_period, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        authorization.setAdapter(authAdapter);

        EditText result = (EditText)findViewById(R.id.emiNumber);

        //Initialize button and setup onclick function.
        Button calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Fetch all required input fields.
                String mortgageValue = mortgage.getText().toString();
                String interestValue = interest.getSelectedItem().toString();
                String authorizationValue = authorization.getSelectedItem().toString();
                //Check if mortgage principle value is empty.
                if (TextUtils.isEmpty(mortgageValue)) {
                    mortgage.setError("Please enter a principle mortgage value.");
                    return;
                }
                //Convert input values to float.
                float principle = Float.parseFloat(mortgageValue);
                float interest = Float.parseFloat(interestValue);
                float auth = Float.parseFloat(authorizationValue);

                //Calculate values with created methods.
                principle = calculatePrinciple(principle);
                interest = calculateInterest(interest);
                auth = calculateAuth(auth);

                //Convert to float and output to results box.
                String EMIResult = Float.toString(calculatePayment(principle, interest, auth));

                result.setText(EMIResult + " / monthly");
            }
        });
    }

    //Setup methods to get user input and calculate final EMI.
    public float calculatePrinciple(float principle) {
        return (float)(principle);
    }

    public float calculateInterest(float interest) {
        return (float)(interest / 12 / 100);
    }

    public float calculateAuth(float auth) {
        return (float)(auth * 12);
    }

    public float calculatePayment(float principle, float rate, float term) {
        return (float)(Math.round((principle * ((rate * (Math.pow((1 + rate), term))) / ((Math.pow((1 + rate), term)) - 1))) * 100) / 100);
    }
}