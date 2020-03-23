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

import java.util.Map;
import java.util.TreeMap;

public class FragmentMonthExpense extends Fragment {

    private double spent = 0.0;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_month_expense, container, false);

        context = getActivity().getApplicationContext();
        ExpenseDBHandler handler = new ExpenseDBHandler(context);

        Map<String, Double> map = handler.monthExpenses();

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            String amount;
            TextView view = null;

            switch (key) {
                case "TOTAL":
                    view = v.findViewById(R.id.month_spent_amount);
                    break;
                case "FOOD":
                    view = v.findViewById(R.id.month_food_amount);
                    break;
                case "DRINKS":
                    view = v.findViewById(R.id.month_drinks_amount);
                    break;
                case "REGULAR":
                    view = v.findViewById(R.id.month_regular_amount);
                    break;
                case "SPORTS":
                    view = v.findViewById(R.id.month_sports_amount);
                    break;
                case "OTHER":
                    view = v.findViewById(R.id.month_other_amount);
                    break;
            }

            amount = String.valueOf(entry.getValue()) + " kn";
            view.setText(amount);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Month Expense");
    }
}
