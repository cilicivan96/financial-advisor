package com.example.chilke.financialadvisor.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chilke.financialadvisor.Databases.ExpenseDBHandler;
import com.example.chilke.financialadvisor.R;

import java.util.List;

public class FragmentTodayExpense extends Fragment {

    private Double spent = 0.0;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_today_expense, container, false);
        TextView textView = v.findViewById(R.id.today_spent_amount);
        context = getActivity().getApplicationContext();
        ExpenseDBHandler handler = new ExpenseDBHandler(context);
        spent = handler.sumTodayCosts();
        textView.setText(String.format("%.2f", spent) + " kn");

        List<String> expenseList = handler.getTodayExpenses();
        String[] expenseArray = expenseList.toArray(new String[expenseList.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.list_item1, expenseArray);

        ListView listView = (ListView) v.findViewById(R.id.today_list);
        listView.setAdapter(adapter);



        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Today Expense");
    }
}
