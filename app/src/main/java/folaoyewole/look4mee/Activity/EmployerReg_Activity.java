package folaoyewole.look4mee.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Pattern;

import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.Model.AccountDetails;
import folaoyewole.look4mee.R;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;
import folaoyewole.look4mee.Utilities.progressDialog;

public class EmployerReg_Activity extends AppCompatActivity {

    private EditText mFirstname, mSurname, mPhonenumber,mResidentState, mPassword, mConfirmPassword;
    private static Date newdate;
    String uri;
    private String Firstname, Surname, stateId, residentStateId, ConfirmPassword, Password, phoneNo;
    private Button Register_Btn;
    private static int LOAD_IMAGE_RESULTS = 201;
    String[] state_array_Ids, ItemsArray;
    TransparentProgressDialog transparentProgressDialog;
    private static final String USER_ACCOUNT_TYPE = "Employer";
    private AccountType_Db accountTypeDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountTypeDb = new AccountType_Db(EmployerReg_Activity.this);

        setContentView(R.layout.activity_employer);

        new GetStates().execute(); // url call to get all states

        mResidentState = (EditText)findViewById(R.id.state_residence);
        mResidentState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(EmployerReg_Activity.this, DropDownList_Activity.class);
                i.putExtra("items", ItemsArray);
                i.putExtra("context", "EmployerReg_Activity");
                startActivityForResult(i, 208);
                EmployerReg_Activity.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });

        mFirstname = (EditText)findViewById(R.id.firstname_txt);
        mSurname = (EditText)findViewById(R.id.surname_txt);
        mPhonenumber = (EditText)findViewById(R.id.mobileno_txt);
        mPassword = (EditText)findViewById(R.id.password_txt);
        mConfirmPassword = (EditText)findViewById(R.id.confirm_txt);

        Register_Btn = (Button)findViewById(R.id.reg_Btn);
        Register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Firstname = mFirstname.getText().toString().trim();
                Surname = mSurname.getText().toString().trim();
                phoneNo = mPhonenumber.getText().toString();
                Password = mPassword.getText().toString().trim();
                ConfirmPassword = mConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(mFirstname.getText()) && TextUtils.isEmpty(mSurname.getText()) &&
                        TextUtils.isEmpty(mPhonenumber.getText()) && TextUtils.isEmpty(mPassword.getText()) &&
                        TextUtils.isEmpty(mConfirmPassword.getText()) && TextUtils.isEmpty(mResidentState.getText())){

                    mFirstname.setError("Field Information Required");
                    mSurname.setError("Field Information Required");
                    mPhonenumber.setError("Field Information Required");
                    mPassword.setError("Field Information Required");
                    mConfirmPassword.setError("Field Information Required");
                    mResidentState.setError("Field Information Required");

                }

                else if(TextUtils.isEmpty(mFirstname.getText())){
                    mFirstname.setError("Field Information Required");
                }

                else if(TextUtils.isEmpty(mSurname.getText())){
                    mSurname.setError("Field Information Required");
                }

                else if(TextUtils.isEmpty(mPhonenumber.getText())){
                    mPhonenumber.setError("Field Information Required");
                }

                else if(TextUtils.isEmpty(mPassword.getText())){
                    mPassword.setError("Field Information Required");
                }

                else if(TextUtils.isEmpty(mConfirmPassword.getText())){

                    mConfirmPassword.setError("Field Information Required");
                }

                else if(TextUtils.isEmpty(mResidentState.getText())){
                    mResidentState.setError("Field Information Required");
                }

                else if(ConfirmPassword.equals(Password) == false){

                    mConfirmPassword.setError("Passwords Do Not Match");
                }

                else if(phoneNo.length() < 11){

                    mPhonenumber.setError("Incomplete Phone Number Entry..");

                }

                else {

                    try {

                        uri = "method=employerregistration&firstname="+ URLEncoder.encode(Firstname,"UTF-8")+"&lastname="+URLEncoder.encode(Surname, "UTF-8")+"&residentStateid="+residentStateId+"&phonenumber="+phoneNo+"&password="+Password;

                        new RegistrationTask().execute();

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){

            String val = data.getStringExtra("ListItem");
            int Id = data.getIntExtra("ListPosition", -1);

            switch (requestCode){

                case 208:
                    mResidentState.setText(val);
                    residentStateId = state_array_Ids[Id];

                    break;

                default:
                    break;
            }

        }


}


//    @Override
//    public void onActivityResult(int requestCode,int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        String attachedFile;
//        int imagePath;
//
//        //Checks if Image gallery was inititated
//        if(requestCode==LOAD_IMAGE_RESULTS && resultCode== Activity.RESULT_OK && data !=null){
//
//
//            if(Build.VERSION.SDK_INT<11){
//                bitmapPath= RealPathUtil.getRealPathFromURI_BelowAPI11(UserProfileActivity.this
//                        ,data.getData());
//
//            } else if (Build.VERSION.SDK_INT < 19) {
//                bitmapPath= RealPathUtil.getRealPathFromURI_API11to18(UserProfileActivity.this,data.getData());
//            } else {
//                bitmapPath= getKitKatPath.getPath(UserProfileActivity.this,data.getData());
//                Log.d(TAG,"Uri Path :" + bitmapPath);
//                //bitmapPath= RealPathUtil.getRealPathFromURI_API19(getContext(),data.getData());
//            }
//            Log.d(TAG,"Media :"+ bitmapPath);
//            try{
//                if(bitmap!=null){
//                    bitmap.recycle();
//                }
//                uriFromPath = Uri.fromFile(new File(bitmapPath));
//                bitmap = BitmapFactory.decodeStream(UserProfileActivity.this.getContentResolver().openInputStream(uriFromPath));
//
//
//            } catch (FileNotFoundException e){
//                e.printStackTrace();
//            }
//
//            ProfileImage.setImageBitmap(bitmap);
//            new UploadImageTask().execute();
////            updateProfileImage();
//
//        }else if (resultCode != Activity.RESULT_CANCELED) {
//            Toast.makeText(UserProfileActivity.this, "  ", Toast.LENGTH_LONG).show();
//        }
//    }

    private class GetStates extends AsyncTask<Void, Void, JSONArray>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            transparentProgressDialog = new TransparentProgressDialog(EmployerReg_Activity.this, R.mipmap.rotate_right);
            transparentProgressDialog.setCanceledOnTouchOutside(false);
            transparentProgressDialog.show();
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {

            try {

                JSONArray output = new Connection_Service().getJSONfromUrl("method=getstates");

                return output;

            } catch (Exception e){

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray s) {
            super.onPostExecute(s);

            transparentProgressDialog.dismiss();

            try {

                state_array_Ids = new String[s.length()];
                ItemsArray = new String[s.length()];

                for(int i = 0; i < s.length(); i++){

                    try {
                        String id = s.getJSONObject(i).get("Id").toString();
                        String state = s.getJSONObject(i).get("stateName").toString();

                        state_array_Ids[i] = id;
                        ItemsArray[i] = state;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e){

                Toast.makeText(EmployerReg_Activity.this,"Connection Timeout",Toast.LENGTH_LONG).show();
            }

        }


    }


    private class RegistrationTask extends AsyncTask<String, Void, String>{
        String output;
        TransparentProgressDialog transparentProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            transparentProgressDialog = new TransparentProgressDialog(EmployerReg_Activity.this, R.mipmap.rotate_right);
            transparentProgressDialog.setCanceledOnTouchOutside(false);
            transparentProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                output = new Connection_Service().getResponsefromUrl(uri);

            } catch (Exception e){
                e.printStackTrace();
            }
            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            transparentProgressDialog.dismiss();
            System.out.println("Employer reg output = "+s);

            try {

                if(s.contains("|")){

                    String[] out = s.trim().split(Pattern.quote("|"));
                    String employerID = out[1];

                    AccountDetails obj = new AccountDetails();
                    obj.setPhone(phoneNo);
                    obj.setAccName("Employer");
                    obj.setEmployerID(employerID);


                    if(accountTypeDb.addAccountRecord(obj) == 1){ // insert artisan as accountType into sqlite

                        Intent i = new Intent(EmployerReg_Activity.this, Menu_Employer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(i);

                        finish();
                    }

                    else {
                        Toast.makeText(EmployerReg_Activity.this,"Failed Save Action",Toast.LENGTH_LONG).show();
                    }
                }

                else if(s.startsWith("0")){


                    new progressDialog().error_Dialog(EmployerReg_Activity.this, s.trim().toString(), "Registration Not Successful");

                }

                else {

                    new progressDialog().error_Dialog(EmployerReg_Activity.this, s.trim().toString(), "Alert");
                }

            } catch (Exception e){

                Toast.makeText(EmployerReg_Activity.this,s.trim(),Toast.LENGTH_LONG).show();

            }





        }
    }

}
