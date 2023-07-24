package com.devdroid.splitpay;

public class BankData {
    private String bankName;
    private String accountNumber;
    private double balance;

    public BankData(String bankName, String accountNumber, double balance) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}

