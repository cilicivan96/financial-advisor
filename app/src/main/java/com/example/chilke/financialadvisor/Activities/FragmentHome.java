package com.example.chilke.financialadvisor.Activities;

import android.content.Context;
import android.graphics.Color;
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

public class FragmentHome extends Fragment {

    private double spent = 0.0;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = v.findViewById(R.id.spent_number);
        context = getActivity().getApplicationContext();
        ExpenseDBHandler handler = new ExpenseDBHandler(context);
        spent = handler.sumTodayCosts();
        textView.setText(String.format("%.2f", spent) + " kn");

        if (spent < 50) textView.setTextColor(Color.rgb(30,192,79));
        else if (spent < 80) textView.setTextColor(Color.rgb(255,211,0));
        else textView.setTextColor(Color.RED);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Home");
    }
}
