package com.example.ksaop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ksaop2.service.SoapClient;
import org.ksoap2.serialization.SoapObject;
import java.util.Vector;

public class AllAccountsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private SoapClient soapClient;

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_accounts);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("All Accounts");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        soapClient = new SoapClient();
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        errorTextView = findViewById(R.id.errorTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadAccounts();
    }
*/
    private void loadAccounts() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);

        soapClient.getAllComptes(new SoapClient.SoapResponse<Vector<SoapObject>>() {
            @Override
            public void onSuccess(Vector<SoapObject> response) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    StringBuilder result = new StringBuilder();
                    for (SoapObject item : response) {
                        result.append("Account id: ").append(item.getProperty("id")).append("\n");
                        result.append("Balance: ").append(item.getProperty("solde")).append("\n");
                        result.append("Type: ").append(item.getProperty("type")).append("\n\n");
                        result.append("Create date: ").append(item.getProperty("dateCreation")).append("\n");

                    }
                    errorTextView.setText(result.toString());
                    errorTextView.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onError(Exception exception) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("Error: " + exception.getMessage());
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
