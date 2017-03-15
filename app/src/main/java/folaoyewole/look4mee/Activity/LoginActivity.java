package folaoyewole.look4mee.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.Model.AccountDetails;
import folaoyewole.look4mee.R;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;

public class LoginActivity extends AppCompatActivity {

    String uri;
    Button loginBtn;
    EditText mPhoneNo, mPassword;
    String PhoneNo, Password;
    AccountType_Db accountTypeDb;
    TextView mLoginErrorText;

    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountTypeDb = new AccountType_Db(LoginActivity.this);

        setContentView(R.layout.activity_login);

        mRelativeLayout = (RelativeLayout)findViewById(R.id.relative3);

        mPhoneNo = (EditText) findViewById(R.id.phone);
        mPassword = (EditText) findViewById(R.id.password);

        mLoginErrorText = (TextView)findViewById(R.id.login_error_msg);


        loginBtn = (Button)findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneNo = mPhoneNo.getText().toString().trim();
                Password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(mPhoneNo.getText()) || TextUtils.isEmpty(mPassword.getText())){
                    mLoginErrorText.setText("PhoneNo and Password Required !");
                    mRelativeLayout.setVisibility(View.VISIBLE);
                }

                else {

                    mRelativeLayout.setVisibility(View.GONE);

                    try {
                        uri = "method=login&username="+URLEncoder.encode(PhoneNo, "UTF-8")+"&password="+URLEncoder.encode(Password,"UTF-8");

                        new LoginTask().execute();

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private class LoginTask extends AsyncTask<Void, Void, String>{

        TransparentProgressDialog dialog ;
        String output;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new TransparentProgressDialog(LoginActivity.this, R.mipmap.rotate_right);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                output = new Connection_Service().getResponsefromUrl(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();

            if(s.trim().contains("|")){

                String[] values = s.trim().split(Pattern.quote("|"));

                String type = values[0];
                String employerId = values[1];

                if(type.startsWith("1")){

                    AccountDetails obj = new AccountDetails();
                    obj.setPhone(PhoneNo);
                    obj.setAccName("Artisan");

                    if(accountTypeDb.addAccountRecord(obj) == 1) { // insert artisan as accountType into sqlite

                        Intent i = new Intent(LoginActivity.this, Menu.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                        finish();

                    }

                }

                else if(type.startsWith("2")){

                    AccountDetails obj = new AccountDetails();
                    obj.setPhone(PhoneNo);
                    obj.setAccName("Employer");
                    obj.setEmployerID(employerId);


                    if(accountTypeDb.addAccountRecord(obj) == 1) { // insert employer as accountType into sqlite

                        Intent i = new Intent(LoginActivity.this, Menu_Employer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                        finish();
                    }

                }

            }

            else if(s.trim().startsWith("User doesn't exist")){
                mRelativeLayout.setVisibility(View.VISIBLE);
                mLoginErrorText.setText("Invalid Credentials; incorrect mobile number or password ");

                mPhoneNo.setText("");
                mPassword.setText("");
            }


            else  {
                mRelativeLayout.setVisibility(View.VISIBLE);
                mLoginErrorText.setText(s.trim());
            }


        }
    }
}
