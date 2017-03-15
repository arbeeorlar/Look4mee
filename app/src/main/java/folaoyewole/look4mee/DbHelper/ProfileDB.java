package folaoyewole.look4mee.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import folaoyewole.look4mee.Model.ArtisanProfile;

/**
 * Created by sp_developer on 11/3/16.
 */
public class ProfileDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProfileManager";
    public static final String DATABASE_TABLE_NAME = "myProfile_Artisan";
    public static final int DATABASE_VERSION = 1;
    private static final String Tag = "Database_Profile";
    private Context mContext;

    // contacts table column names
    public static final String KEY_ID = "id";
    public static final String KEY_FULLNAME = "Full_name";
    public static final String KEY_MOBILE = "Mobile";
    //public static final String KEY_HOME_ADDRESS = "Home_Address";
   // public static final String KEY_GENDER = "Gender";
   // public static final String KEY_BIRTHDAY = "Birthday";
   // public static final String KEY_STATE_OF_ORIGIN = "State_Of_Origin";
    public static final String KEY_RESIDENCE_STATE = "Residence_State";
   // public static final String KEY_AREA = "Area";
    public static final String KEY_SKILLS = "Skills";

    public ProfileDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {

        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
        stringBuilder.append(DATABASE_TABLE_NAME + " (" + KEY_ID );
        stringBuilder.append(" INTEGER PRIMARY KEY, ");
        stringBuilder.append(KEY_FULLNAME +" TEXT, ");
        stringBuilder.append(KEY_MOBILE +" TEXT, ");
//        stringBuilder.append(KEY_HOME_ADDRESS +" TEXT, ");
//        stringBuilder.append(KEY_GENDER +" TEXT, ");
//        stringBuilder.append(KEY_BIRTHDAY +" TEXT, ");
//        stringBuilder.append(KEY_STATE_OF_ORIGIN +" TEXT, ");
        stringBuilder.append(KEY_RESIDENCE_STATE +" TEXT, ");
      //  stringBuilder.append(KEY_AREA +" TEXT, ");
        stringBuilder.append(KEY_SKILLS +" TEXT );");

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


    public long addArtisanProfileRecord(ArtisanProfile profile){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, profile.getFullname());
        values.put(KEY_MOBILE, profile.getMobile());
//        values.put(KEY_HOME_ADDRESS, profile.getHomeAddress());
//        values.put(KEY_GENDER, profile.getGender());
//        values.put(KEY_BIRTHDAY, profile.getBirthday());
//        values.put(KEY_STATE_OF_ORIGIN, profile.getState_of_origin());
        values.put(KEY_RESIDENCE_STATE, profile.getResidenceState());
      //  values.put(KEY_AREA, profile.getArea());
        values.put(KEY_SKILLS, profile.getSkills());

        // Inserting Row
        long response = db.insert(DATABASE_TABLE_NAME, null, values);
        db.close();

        return response;
    }


    public ArrayList<ArtisanProfile> getProfile_Records(){

        ArrayList<ArtisanProfile> List = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor.moveToNext()){
            do{

                ArtisanProfile obj = new ArtisanProfile();


                obj.setId(Integer.parseInt(cursor.getString(0)));
                obj.setFullname(cursor.getString(1));
                obj.setMobile(cursor.getString(2));
                obj.setResidenceState(cursor.getString(3));
                obj.setSkills(cursor.getString(4));

                // Adding result to list
                List.add(obj);

            } while(cursor.moveToNext());
        }

        cursor.close();
        return  List;
    }

}
