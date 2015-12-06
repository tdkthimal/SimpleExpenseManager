package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by TDK-NOTEPAD on 12/4/2015.
 */

public class DBAccountDAO implements AccountDAO{

    private SQLiteDatabase db;

    public DBAccountDAO(SQLiteDatabase db){
        this.db = db;
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor c = db.rawQuery("SELECT accountNo FROM Account", new String[]{});
        List<String> list = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                String accountNo= c.getString(c.getColumnIndex("accountNo"));
                list.add(accountNo);
            }while(c.moveToNext());
        }

        return list;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor c = db.rawQuery("SELECT * FROM Account", new String[]{});
        List<Account> list = new ArrayList<>();
        if(c.moveToFirst()) {
            do{
                String accountNo= c.getString(c.getColumnIndex("accountNo"));
                String bankName = c.getString(c.getColumnIndex("bankName"));
                String accountHolderName = c.getString(c.getColumnIndex("accountHolderName"));
                double balance = c.getDouble(c.getColumnIndex("balance"));
                list.add(new Account(accountNo,bankName,accountHolderName,balance));
            }while(c.moveToNext());
        }
        return list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor c = db.rawQuery("SELECT * FROM Account WHERE accountNo=?", new String[]{accountNo});
        if(c.moveToFirst()){
            String bankName = c.getString(c.getColumnIndex("bankName"));
            String accountHolderName = c.getString(c.getColumnIndex("accountHolderName"));
            double balance = c.getDouble(c.getColumnIndex("balance"));
            return new Account(accountNo,bankName,accountHolderName,balance);
        }
        return null;
    }

    @Override
    public void addAccount(Account account) {
        String query = "INSERT INTO Account VALUES (?,?,?,?)";
        db.execSQL(query,new Object[]{account.getAccountNo(),account.getBankName(),account.getAccountHolderName(),account.getBalance()});
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String query = "DELETE FROM Account WHERE accountNo=?";
        db.execSQL(query,new String[]{accountNo});
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        if(expenseType.equals(ExpenseType.EXPENSE)){
            amount = -1 * amount;
        }
        String query = "UPDATE Account SET balance = balance + ? WHERE accountNo=?";
        db.execSQL(query,new Object[]{amount,accountNo});

    }
}
