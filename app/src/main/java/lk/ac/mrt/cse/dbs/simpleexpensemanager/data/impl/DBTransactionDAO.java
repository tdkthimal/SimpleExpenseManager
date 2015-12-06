package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by TDK-NOTEPAD on 12/4/2015.
 */
public class DBTransactionDAO implements TransactionDAO {


    private SQLiteDatabase db;

    public DBTransactionDAO(SQLiteDatabase db){
        this.db = db;
    }


    @Override
    public void logTransaction(String date, String accountNo, ExpenseType expenseType, double amount) {
        String query = "INSERT INTO BTransaction VALUES (?,?,?,?)";
        String eType ;
        if(expenseType == ExpenseType.EXPENSE){
            eType = "EXPENSE";
        }else{
            eType = "INCOME";
        }
        db.execSQL(query,new Object[]{date,accountNo,eType,amount});
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor c = db.rawQuery("SELECT * FROM BTransaction", new String[]{});
        List<Transaction> list = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                String date= c.getString(c.getColumnIndex("date"));
                String accountNo = c.getString(c.getColumnIndex("accountNo"));
                String eType= c.getString(c.getColumnIndex("expenseType"));
                double amount= c.getDouble(c.getColumnIndex("amount"));
                ExpenseType expenseType;
                if (eType.equals("EXPENSE")) {
                    expenseType = ExpenseType.EXPENSE;
                } else {
                    expenseType = ExpenseType.INCOME;
                }
                list.add(new Transaction(date, accountNo, expenseType, amount));
            } while (c.moveToNext());
        }
        return list;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        int i = 0;
        Cursor c = db.rawQuery("SELECT * FROM BTransaction", new String[]{});
        List<Transaction> list = new ArrayList<>();
        if(c.moveToFirst()) {
            do{
                i++;
                String date= c.getString(c.getColumnIndex("date"));
                String accountNo = c.getString(c.getColumnIndex("accountNo"));
                String eType= c.getString(c.getColumnIndex("expenseType"));
                double amount= c.getDouble(c.getColumnIndex("amount"));
                ExpenseType expenseType;
                if(eType.equals("EXPENSE")){
                    expenseType = ExpenseType.EXPENSE;
                }else{
                    expenseType = ExpenseType.INCOME;
                }
                list.add(new Transaction(date,accountNo,expenseType,amount));
                if(i >= limit){
                    break;
                }
            }while(c.moveToNext());
        }
        return list;
    }
}
