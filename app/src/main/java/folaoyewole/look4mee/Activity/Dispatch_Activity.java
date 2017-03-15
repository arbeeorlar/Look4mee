package folaoyewole.look4mee.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import folaoyewole.look4mee.App.MainActivity;
import folaoyewole.look4mee.DbHelper.AccountType_Db;


/**
 * Created by sp_developer on 11/4/16.
 */
public class Dispatch_Activity extends AppCompatActivity {

    private AccountType_Db accountTypeDb;

    @Override
    protected void onStart() {
        super.onStart();

        accountTypeDb = new AccountType_Db(Dispatch_Activity.this);

        int size =  accountTypeDb.getAccount_Record().size();

        if(size > 0){

            String accname = accountTypeDb.getAccount_Record().get(0).getAccName();

            if(accname.startsWith("Artisan")) {

                Intent intent = new Intent(Dispatch_Activity.this, Menu.class);
                startActivity(intent);

                finish();

            }

            else {

                Intent intent = new Intent(Dispatch_Activity.this, Menu_Employer.class);
                startActivity(intent);

                finish();
            }

        }

        else {

            Intent intent = new Intent(Dispatch_Activity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }
    }


}
