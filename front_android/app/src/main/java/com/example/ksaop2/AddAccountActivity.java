package com.example.ksaop2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ksaop2.service.SoapClient;
import org.ksoap2.serialization.SoapObject;

public class AddAccountActivity extends AppCompatActivity {
    private EditText soldeInput, typeInput;
    private TextView resultTextView;
    private SoapClient soapClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add an account");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        soapClient = new SoapClient();
        soldeInput = findViewById(R.id.soldeInput);
        typeInput = findViewById(R.id.typeInput);
        resultTextView = findViewById(R.id.resultTextView);
        Button addAccountButton = findViewById(R.id.addAccountButton);

        addAccountButton.setOnClickListener(v -> {
            String solde = soldeInput.getText().toString();
            String type = typeInput.getText().toString();

            if (solde.isEmpty() || type.isEmpty()) {
                resultTextView.setText("Please fill in all fields");
                return;
            }

            soapClient.createCompte(solde, type, new SoapClient.SoapResponse<SoapObject>() {
                @Override
                public void onSuccess(SoapObject response) {
                    runOnUiThread(() -> {
                        resultTextView.setText(
                            "Account added successfully ! \n\n"
                           // "ID: " + response.getProperty("id") + "\n" +
                           // "Balance: " + response.getProperty("solde") + "\n" +
                            //"Created On: " + response.getProperty("dateCreation") + "\n" +
                           // "Type: " + response.getProperty("type")
                        );
                        soldeInput.setText("");
                        typeInput.setText("");
                    });
                }

                @Override
                public void onError(Exception exception) {
                    runOnUiThread(() -> resultTextView.setText("Error: " + exception.getMessage()));
                }
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
