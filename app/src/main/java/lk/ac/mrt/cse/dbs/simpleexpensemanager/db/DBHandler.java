package lk.ac.mrt.cse.dbs.simpleexpensemanager.db;

/**
 * Created by TDK-NOTEPAD on 12/4/2015.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "130311H.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String accounts_table_creation = "CREATE TABLE Account(accountNo TEXT PRIMARY KEY,bankName TEXT,accountHolderName TEXT,balance DOUBLE)";
        db.execSQL(accounts_table_creation);
        String transaction_table_creation= "CREATE TABLE BTransaction(date TEXT,accountNo TEXT,expenseType TEXT,amount DOUBLE, PRIMARY KEY(date,accountNo))";
        db.execSQL(transaction_table_creation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account" );
        db.execSQL("DROP TABLE IF EXISTS BTransaction");
        onCreate(db);
    }


}
