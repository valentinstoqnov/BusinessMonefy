package org.elsys.valiolucho.businessmonefy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by valen on 21.4.2016 г..
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bussinessmonefy.db";
    private static final String INCOME_TABLE = "income_table";
    private static final String OUTCOME_TABLE = "outcome_table";
    private static final String UID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String MONEY = "money";
    private static final String DATE = "date";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    private String createTableStringBuilder(String tableName, ArrayList<String> cols, ArrayList<String> varTypes) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table ");
        stringBuffer.append(tableName);
        stringBuffer.append(" ( ");
        for (int i = 0; i < cols.size(); i++) {
            stringBuffer.append(cols.get(i));
            stringBuffer.append(" ");
            stringBuffer.append(varTypes.get(i));
            stringBuffer.append(", ");
        }
        stringBuffer.append(")");
        stringBuffer.deleteCharAt(stringBuffer.lastIndexOf(","));
        return stringBuffer.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableStringBuilder(INCOME_TABLE, new ArrayList<String>() {{
            add(UID);
            add(NAME);
            add(DESCRIPTION);
            add(MONEY);
            add(DATE);
        }}, new ArrayList<String>() {{
            add("INTEGER PRIMARY KEY AUTOINCREMENT");
            //havwe to check for longer name
            add("VARCHAR(64)");
            add("TEXT");
            //store money in lowest currency unit(ex. 10lv -> 1000st)
            add("INTEGER");
            add("DATETIME");
        }}));

        db.execSQL(createTableStringBuilder(OUTCOME_TABLE, new ArrayList<String>() {{
            add(UID);
            add(NAME);
            add(DESCRIPTION);
            add(MONEY);
            add(DATE);
        }}, new ArrayList<String>() {{
            add("INTEGER PRIMARY KEY AUTOINCREMENT");
            //havwe to check for longer name
            add("VARCHAR(64)");
            add("TEXT");
            //store money in lowest currency unit(ex. 10lv -> 1000st)
            add("INTEGER");
            add("DATETIME");
        }}));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INCOME_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OUTCOME_TABLE);
        onCreate(db);
    }

    public boolean insertData(Transaction transaction, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, transaction.getName());
        contentValues.put(DESCRIPTION, transaction.getDescription());
        ;
        contentValues.put(MONEY, transaction.getMoney());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        contentValues.put(DATE, date);
        long res;
        if (tableName.equals(INCOME_TABLE)) {
            res = db.insert(INCOME_TABLE, null, contentValues);
        } else {
            res = db.insert(OUTCOME_TABLE, null, contentValues);
        }
        if (res == -1) {
            return false;
        }
        return true;
    }

    public boolean updateData(Transaction transaction,String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, transaction.getName());
        contentValues.put(DESCRIPTION, transaction.getDescription());
        contentValues.put(MONEY, transaction.getMoney());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        contentValues.put(DATE, date);
        if (tableName.equals(INCOME_TABLE)) {
            db.insert(INCOME_TABLE, null, contentValues);
            db.update(INCOME_TABLE,contentValues, NAME + " = ?",new String[] {transaction.getName()});
        } else {
            db.insert(OUTCOME_TABLE, null, contentValues);
            db.update(OUTCOME_TABLE,contentValues, NAME + " = ?",new String[] {transaction.getName()});
        }
        return true;
    }

    public Integer deleteData(Transaction transaction,String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer res;
        if (tableName.equals(INCOME_TABLE)) {
            res = db.delete(INCOME_TABLE,NAME + " = ?",new String[] {transaction.getName()});
        }else {
            res = db.delete(OUTCOME_TABLE,NAME + " = ?",new String[] {transaction.getName()});
        }
        return res;
    }

    public Cursor getAllData(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if (tableName.equals(INCOME_TABLE)) {
            res = db.rawQuery("select * from" + INCOME_TABLE, null);
        }else {
            res = db.rawQuery("select * from" + OUTCOME_TABLE, null);
        }
        return res;
    }
}
