package com.example.ksaop2;

import android.content.Intent;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private SoapClient soapClient;
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soapClient = new SoapClient();
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        errorTextView = findViewById(R.id.errorTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountAdapter = new AccountAdapter();
        recyclerView.setAdapter(accountAdapter);

        loadAccounts();

        Button getAllAccountsButton = findViewById(R.id.getAllAccountsButton);
        Button deleteAccountButton = findViewById(R.id.deleteAccountButton);
        Button addAccountButton = findViewById(R.id.addAccountButton);

        getAllAccountsButton.setOnClickListener(v -> {
            loadAccounts();
        });

        deleteAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DeleteAccountActivity.class);
            startActivity(intent);
        });

        addAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAccountActivity.class);
            startActivity(intent);
        });
    }


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
                    accountAdapter.setAccounts(response);
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


}
