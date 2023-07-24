package com.devdroid.splitpay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BankDataAdapter extends RecyclerView.Adapter<BankDataAdapter.ViewHolder> {

    private List<BankData> bankDataList;

    public BankDataAdapter(List<BankData> bankDataList) {
        this.bankDataList = bankDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankData bankData = bankDataList.get(position);
        holder.tvBankName.setText(bankData.getBankName());
        holder.tvAccountNumber.setText(bankData.getAccountNumber());
        holder.tvBalance.setText(String.valueOf(bankData.getBalance()));
    }

    @Override
    public int getItemCount() {
        return bankDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName;
        TextView tvAccountNumber;
        TextView tvBalance;

        ViewHolder(View itemView) {
            super(itemView);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvBalance = itemView.findViewById(R.id.tvBalance);
        }
    }
}

