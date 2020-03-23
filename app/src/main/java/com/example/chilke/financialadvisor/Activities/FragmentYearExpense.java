package com.example.chilke.financialadvisor.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chilke.financialadvisor.Databases.ExpenseDBHandler;
import com.example.chilke.financialadvisor.R;

public class FragmentYearExpense extends Fragment {

    private double spent = 0.0;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_year_expense, container, false);
        TextView textView = v.findViewById(R.id.year_spent_amount);
        context = getActivity().getApplicationContext();
        ExpenseDBHandler handler = new ExpenseDBHandler(context);
        spent = handler.sumYearCosts();
        textView.setText(String.format("%.2f", spent) + " kn");

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Year Expense");
    }
}
