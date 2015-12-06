package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DBTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.db.DBHandler;

/**
 * Created by TDK-NOTEPAD on 12/6/2015.
 */
public class DBExpenseManager extends ExpenseManager {

    Activity mainActivity;

    public DBExpenseManager(Activity mainActivity){
        this.mainActivity = mainActivity;
        setup();
    }

    @Override
    public void setup() {
        DBHandler dbHandler = new DBHandler(mainActivity);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        AccountDAO dbAccountDAO = new DBAccountDAO(db);
        setAccountsDAO(dbAccountDAO);
        TransactionDAO inMemoryTransactionDAO = new DBTransactionDAO(dbHandler.getWritableDatabase());
        setTransactionsDAO(inMemoryTransactionDAO);
    }
}
