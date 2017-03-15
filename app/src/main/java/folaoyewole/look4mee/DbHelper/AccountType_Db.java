package folaoyewole.look4mee.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import folaoyewole.look4mee.Model.AccountDetails;

/**
 * Created by sp_developer on 11/4/16.
 */
public class AccountType_Db extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AccountManager";
    public static final String DATABASE_TABLE_NAME = "AccountType";
    public static final int DATABASE_VERSION = 1;
    private static final String Tag = "Database_Account";
    private Context mContext;

    // contacts table column names
    public static final String KEY_ID = "id";
    public static final String KEY_Type = "Account_Type";
    public static final String KEY_EMPLOYER_ID = "Employer_ID";
    public static final String KEY_PHONE = "Phone_No";


    public AccountType_Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {

        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
        stringBuilder.append(DATABASE_TABLE_NAME + " (" + KEY_ID );
        stringBuilder.append(" INTEGER PRIMARY KEY, ");
        stringBuilder.append(KEY_Type +" TEXT, ");
        stringBuilder.append(KEY_EMPLOYER_ID +" TEXT, ");
        stringBuilder.append(KEY_PHONE +" TEXT );");


        Log.d(Tag, "the sql statement " + stringBuilder.toString());
        String CREATE_TABLE = stringBuilder.toString();
        Db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase Db, int oldVersion, int newVersion) {

        Db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);

        // create table again
        onCreate(Db);

    }

    public long addAccountRecord(AccountDetails nameType){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Type, nameType.getAccName());
        values.put(KEY_EMPLOYER_ID, nameType.getEmployerID());
        values.put(KEY_PHONE, nameType.getPhone());

        // Inserting Row
        long response = db.insert(DATABASE_TABLE_NAME, null, values);
        db.close();

        return response;
    }


    public ArrayList<AccountDetails> getAccount_Record(){

        ArrayList<AccountDetails> List = new ArrayList<>();
        HashMap<String, String> _list = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor.moveToNext()){
            do{

                String name = cursor.getString(1);
                String employerID = cursor.getString(2);
                String phone = cursor.getString(3);

                // Adding result to list
                AccountDetails accountDetails = new AccountDetails();
                accountDetails.setAccName(name);
                accountDetails.setEmployerID(employerID);
                accountDetails.setPhone(phone);

                List.add(accountDetails);


            } while(cursor.moveToNext());
        }

        cursor.close();
        return  List;
    }

}
