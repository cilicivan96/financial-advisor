package com.example.chilke.financialadvisor.Databases;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.chilke.financialadvisor.Model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExpenseDBHandler extends SQLiteOpenHelper {

    // database info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ExpenseDB.db";
    public static final String TABLE_NAME = "expenseTable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DESCRIPTION = "description";


    //initializing the database
    public ExpenseDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_AMOUNT + " REAL,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT " + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public double sumTodayCosts() {
        double sum = 0;

        Date date = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        String day;
        String month;
        int dayNo = cal1.get(Calendar.DAY_OF_MONTH);
        int monthNo = cal1.get(Calendar.MONTH) + 1;
        if (dayNo < 10)
            day = "0" + String.valueOf(dayNo);
        else
            day = String.valueOf(dayNo);
        if (monthNo < 10)
            month = "0" + String.valueOf(monthNo);
        else
            month = String.valueOf(monthNo);

        String time = "'" + String.valueOf(cal1.get(Calendar.YEAR)) + "-" + month +
                "-" + day + "%" + "'";

        String query = "SELECT " + "SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME +
                " WHERE " + COLUMN_TIME + " LIKE " + time;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            sum = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return sum;
    }

    public Map<String, Double> monthExpenses() {
        Date date = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        String month;
        int monthNo = cal1.get(Calendar.MONTH) + 1;
        if (monthNo < 10)
            month = "0" + String.valueOf(monthNo);
        else
            month = String.valueOf(monthNo);

        String time = "'" + String.valueOf(cal1.get(Calendar.YEAR)) + "-" + month + "%" + "'";

        String query = "SELECT " + COLUMN_TYPE + ", SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_NAME +
                " WHERE " + COLUMN_TIME + " LIKE " + time + " GROUP BY " + COLUMN_TYPE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Map<String, Double> map = getAmountByType(cursor);

        cursor.close();
        db.close();

        return map;
    }

    public double sumYearCosts() {
        double sum = 0;
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Date date = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        while (cursor.moveToNext()) {
            try {
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date time = dt.parse(cursor.getString(1));
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(time);

                if (cal2.get(Calendar.YEAR) == cal1.get(Calendar.YEAR))
                    sum += cursor.getDouble(2);
            }
            catch (ParseException ex) {
                System.err.println("Wrong time format!");
            }
        }

        cursor.close();
        db.close();

        return sum;
    }

    public void addHandler(Expense expense) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(COLUMN_ID, expense.getId());
        values.put(COLUMN_TIME, expense.getTime());
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_TYPE, expense.getType().toString());
        values.put(COLUMN_DESCRIPTION, expense.getDescription());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteBase (Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    private Map<String, Double> getAmountByType(Cursor cursor) {
        double total = 0.0;
        double food = 0.0;
        double drinks = 0.0;
        double regular = 0.0;
        double sports = 0.0;
        double other = 0.0;

        Map<String, Double> map = new TreeMap<>();

        while (cursor.moveToNext()) {
            double amount = cursor.getDouble(1);
            total += amount;

            String type = cursor.getString(0);
            switch (type) {
                case "FOOD":
                    food += amount;
                    break;
                case "DRINKS":
                    drinks += amount;
                    break;
                case "REGULAR":
                    regular += amount;
                    break;
                case "SPORTS":
                    sports += amount;
                    break;
                case "OTHER":
                    other += amount;
                    break;
            }
        }

        map.put("TOTAL", total);
        map.put("FOOD", food);
        map.put("DRINKS", drinks);
        map.put("REGULAR", regular);
        map.put("SPORTS", sports);
        map.put("OTHER", other);

        return map;
    }

    public List<String> getTodayExpenses() {
        List<String> result = new LinkedList<>();

        Date date = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        String month;
        int monthNo = cal1.get(Calendar.MONTH) + 1;
        if (monthNo < 10)
            month = "0" + String.valueOf(monthNo);
        else
            month = String.valueOf(monthNo);

        String day;
        int dayNo = cal1.get(Calendar.DAY_OF_MONTH);
        if (dayNo < 10)
            day = "0" + String.valueOf(dayNo);
        else
            day = String.valueOf(dayNo);

        String time = "'" + String.valueOf(cal1.get(Calendar.YEAR)) + "-" + month +
                "-" + day + "%" + "'";

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TIME +
                " LIKE " + time;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Expense expense;
        while (cursor.moveToNext()) {
            expense = new Expense(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    ExpenseType.valueOf(cursor.getString(3)),
                    cursor.getString(4));
            result.add(expense.toString());
        }

        cursor.close();

        return result;
    }
}
