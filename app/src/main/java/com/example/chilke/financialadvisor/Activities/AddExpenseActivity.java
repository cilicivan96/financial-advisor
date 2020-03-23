package com.example.chilke.financialadvisor.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.chilke.financialadvisor.Model.Expense;
import com.example.chilke.financialadvisor.Model.ExpenseType;
import com.example.chilke.financialadvisor.R;
import com.example.chilke.financialadvisor.Databases.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {

    private static final String[] types = {"FOOD", "DRINKS", "REGULAR", "SPORTS", "OTHER"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Spinner dropdown = findViewById(R.id.type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, types);
        dropdown.setAdapter(adapter);
    }

    public void addExpense(View view) {
        EditText editAmount = (EditText) findViewById(R.id.amount);
        Spinner spinnerType = (Spinner) findViewById(R.id.type);
        EditText editDescription = (EditText) findViewById(R.id.description);

        if (editAmount.getText().toString().equals("") || editDescription.getText().toString().equals("")) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Please fill in all data.");
            builder1.setTitle("Error");
            builder1.setCancelable(true);
            AlertDialog alert11 = builder1.create();
            alert11.show();
            return;
        }

        double amount = Double.parseDouble(editAmount.getText().toString());
        ExpenseType type = ExpenseType.valueOf(spinnerType.getSelectedItem().toString());
        String description = editDescription.getText().toString();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);

        Expense expense = new Expense(null, time, amount, type, description);
        ExpenseDBHandler dbHandler = new ExpenseDBHandler(this);
        dbHandler.addHandler(expense);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
