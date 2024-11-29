package com.example.ksaop2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ksaop2.service.SoapClient;

public class DeleteAccountActivity extends AppCompatActivity {
    private EditText accountIdInput;
    private TextView resultTextView;
    private SoapClient soapClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Supprimer un compte");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        soapClient = new SoapClient();
        accountIdInput = findViewById(R.id.accountIdInput);
        resultTextView = findViewById(R.id.resultTextView);
        Button deleteAccountButton = findViewById(R.id.deleteAccountButton);

        deleteAccountButton.setOnClickListener(v -> {
            try {
                long id = Long.parseLong(accountIdInput.getText().toString());
                deleteAccount(id); // Directly call deleteAccount instead of showing a dialog
            } catch (NumberFormatException e) {
                resultTextView.setText("Please enter a valid account ID");
            }
        });
    }

    private void deleteAccount(long id) {
        soapClient.deleteCompte(id, new SoapClient.SoapResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                runOnUiThread(() -> {
                    if (result) {
                        resultTextView.setText("Account " + id + " deleted");
                        accountIdInput.setText("");
                    } else {
                        resultTextView.setText("Failed to delete account " + id);
                    }
                });
            }

            @Override
            public void onError(Exception exception) {
                runOnUiThread(() -> resultTextView.setText("Error: " + exception.getMessage()));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
