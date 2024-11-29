package com.example.ksaop2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.ksoap2.serialization.SoapObject;
import java.util.Vector;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private Vector<SoapObject> accounts = new Vector<>();

    public void setAccounts(Vector<SoapObject> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        SoapObject account = accounts.get(position);
        holder.accountId.setText("Account id: " + account.getProperty("id").toString());
        holder.balance.setText("Balance: " + account.getProperty("solde").toString());
        holder.type.setText("Type: " + account.getProperty("type").toString());
        holder.createdOn.setText("Create Date: " + account.getProperty("dateCreation").toString());

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView accountId, balance, createdOn, type;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountId = itemView.findViewById(R.id.accountId);
            balance = itemView.findViewById(R.id.balance);
            createdOn = itemView.findViewById(R.id.createdOn);
            type = itemView.findViewById(R.id.type);
        }
    }
}
