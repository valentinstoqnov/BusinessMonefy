package org.elsys.valiolucho.businessmonefy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bussinessmonefy.db";
    private static final String TABLE_NAME = "transactions";
    private static final int DATABASE_VERSION = 1;

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DATE = "date";
    private static final String MONEY = "money";

    private static final String CREATE_INCOME_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(" + NAME + " TEXT, " +
            DESCRIPTION + " TEXT, " + DATE + " DATETIME, " + MONEY + " BIGINT);";

    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static DataBaseHelper sInstance;

    Context context;
    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INCOME_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public void deleteTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("VACUUM");
        db.close();
    }

    public void insertData(Transaction transaction) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, transaction.getName());
        contentValues.put(DESCRIPTION, transaction.getDescription());
        contentValues.put(DATE, transaction.getDate());
        contentValues.put(MONEY, Transaction.getMoneyAsInt(transaction.getMoney()).intValue());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updateData(Transaction newTransaction) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, newTransaction.getName());
        contentValues.put(DESCRIPTION, newTransaction.getDescription());
        contentValues.put(DATE, newTransaction.getDate());
        contentValues.put(MONEY, Transaction.getMoneyAsInt(newTransaction.getMoney()).intValue());

        String selection = DATE + " LIKE ?";
        String[] selectionArgs = {newTransaction.getDate()};

        db.update(TABLE_NAME, contentValues, selection, selectionArgs);
        db.close();
    }

    public void deleteData(Transaction transaction) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = DATE + " LIKE ?";
        String[] selectionArgs = {transaction.getDate()};
        db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public ArrayList<Transaction> getAllData(String sortOrder) {
        SQLiteDatabase db = getReadableDatabase();
        sortOrder = DATE + " " + sortOrder;
        Cursor cursor = db.query(TABLE_NAME, new String[] {NAME, DESCRIPTION, DATE, MONEY}, null, null, null, null, sortOrder);
        ArrayList<Transaction> arrayList = cursorToArrList(cursor);
        db.close();
        return arrayList;
    }

    public ArrayList<Transaction> getSpecificData(String sortORder, String dateFrom, String dateTo) {
        SQLiteDatabase db = getReadableDatabase();
        sortORder = DATE + " " + sortORder;
        String[] columns = {NAME, DESCRIPTION, DATE, MONEY};
        String selection = DATE + " BETWEEN ? AND ?";
        String[] selectionArgs = {dateFrom, dateTo};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, sortORder);
        ArrayList<Transaction> arrayList = cursorToArrList(cursor);
        db.close();
        return arrayList;
    }

    public ArrayList<Transaction> getIncomings(String sortORder, String dateFrom, String dateTo) {
        SQLiteDatabase db = getReadableDatabase();
        sortORder = DATE + " " + sortORder;
        String[] columns = {NAME, DESCRIPTION, DATE, MONEY};
        String selection = "(" + DATE + " BETWEEN ? AND ?) AND (" + MONEY + " > ?)";
        String[] selectionArgs = {dateFrom, dateTo, "0"};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, sortORder);
        ArrayList<Transaction> arrayList = cursorToArrList(cursor);
        db.close();
        return  arrayList;
    }

    private ArrayList<Transaction> cursorToArrList(Cursor cursor) {
        ArrayList<Transaction> arrayList = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Transaction transaction = new Transaction(cursor.getString(cursor.getColumnIndex(NAME)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        Transaction.getMoneyWithDecPoint(cursor.getInt(cursor.getColumnIndex(MONEY))));
                transaction.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                arrayList.add(transaction);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return arrayList;
    }
}
