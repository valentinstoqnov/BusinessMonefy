package org.elsys.valiolucho.businessmonefy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bussinessmonefy.db";
    private static final String TABLE_NAME = "transactions";
    private static final int DATABASE_VERSION = 1;

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DATE = "date";
    private static final String MONEY = "money";

    private static final String CREATE_INCOME_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(" + NAME + " VARCHAR(64), " +
            DESCRIPTION + " TEXT, " + DATE + " DATETIME, " + MONEY + " INTEGER);";

    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
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

    public void insertData(Transaction transaction, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, transaction.getName());
        contentValues.put(DESCRIPTION, transaction.getDescription());
        contentValues.put(DATE, transaction.getDate());
        contentValues.put(MONEY, transaction.getMoney());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateData(Transaction oldTransaction, Transaction newTransaction, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, newTransaction.getName());
        contentValues.put(DESCRIPTION, newTransaction.getDescription());
        contentValues.put(DATE, newTransaction.getDate());
        contentValues.put(MONEY, newTransaction.getMoney());

        String selection = DATE + " LIKE ?";
        String[] selectionArgs = {oldTransaction.getDate()};

        db.update(TABLE_NAME, contentValues, selection, selectionArgs);
    }

    public void deleteData(Transaction transaction, SQLiteDatabase db) {
        String selection = DATE + " LIKE ?";
        String[] selectionArgs = {transaction.getDate()};
        db.delete(TABLE_NAME, selection, selectionArgs);
    }

    public Cursor getAllData(SQLiteDatabase db, String sortOrder) {
        sortOrder = DATE + sortOrder;
        return db.query("SELECT * FROM " + TABLE_NAME, new String[] {NAME, DESCRIPTION, DATE, MONEY},
                null, null, null, null, sortOrder);
    }

    public Cursor getSpecificData(SQLiteDatabase db, String sortORder, String dateFrom, String dateTo) {
        sortORder = DATE + sortORder;
        String[] columns = {NAME, DESCRIPTION, DATE, MONEY};
        String selection = DATE + " BETWEEN ? AND ?";
        String[] selectionArgs = {dateFrom, dateTo};
        return db.query("SELECT * FROM " + TABLE_NAME, columns, selection, selectionArgs, null, null, sortORder);
    }
}
