package com.example.ksaop2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ksaop2.service.SoapClient;
import org.ksoap2.serialization.SoapObject;

public class GetAccountActivity extends AppCompatActivity {
    private EditText accountIdInput;
    private TextView resultTextView;
    private SoapClient soapClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_account);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Get Account Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        soapClient = new SoapClient();
        accountIdInput = findViewById(R.id.accountIdInput);
        resultTextView = findViewById(R.id.resultTextView);
        Button getAccountButton = findViewById(R.id.getAccountButton);

        getAccountButton.setOnClickListener(v -> {
            try {
                long id = Long.parseLong(accountIdInput.getText().toString());
                soapClient.getCompteById(id, new SoapClient.SoapResponse<SoapObject>() {
                    @Override
                    public void onSuccess(SoapObject response) {
                        runOnUiThread(() -> resultTextView.setText(
                            "Account Details:\n\n" +
                            "ID: " + response.getProperty("id") + "\n" +
                            "Balance: " + response.getProperty("solde") + "\n" +
                            "Created On: " + response.getProperty("dateCreation") + "\n" +
                            "Type: " + response.getProperty("type"))
                        );
                    }
                    @Override
                    public void onError(Exception exception) {
                        runOnUiThread(() -> resultTextView.setText("Error: " + exception.getMessage()));
                    }
                });
            } catch (NumberFormatException e) {
                resultTextView.setText("Please enter a valid account ID");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
