package folaoyewole.look4mee.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.Model.AccountDetails;
import folaoyewole.look4mee.Model.Skill;
import folaoyewole.look4mee.R;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;
import folaoyewole.look4mee.Utilities.progressDialog;
import folaoyewole.look4mee.Utilities.skillsJson;
import folaoyewole.look4mee.adapter.SkillsAdapter2;


public class ArtisanReg_Activity extends AppCompatActivity {

    private EditText  mResidenceState;
    private String residentStateId, ConfirmPassword, residence_state;
    private TextView mCategory;
    private EditText mFirstname, mSurname, mPhonenumber, mPassword, mConfirmPassword;
    private static Date newdate;
    String uri;
    ArrayList<Skill> skills_list = new ArrayList<>();
    SkillsAdapter2 skillsAdapter;
    String[] state_array_Ids, ItemsArray, skill_array_Ids, SkillArray;
    Button  RegBtn;
    private String Firstname, Surname, Phonenumber, Password;
    TransparentProgressDialog transparentProgressDialog;
    RecyclerView recyclerView;
    private AccountType_Db accountTypeDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountTypeDb = new AccountType_Db(ArtisanReg_Activity.this);

        setContentView(R.layout.activity_artisan_reg);

        new GetStates().execute();


//        mSpinnerGender = (Spinner)findViewById(R.id.spinner);
//
//        mySpinnerAdapter spinnerAdapter = new mySpinnerAdapter(ArtisanReg_Activity.this, android.R.layout.simple_list_item_1);
//        spinnerAdapter.addAll(genderspinnervalue);
//        spinnerAdapter.add("Select gender");
//
//        mSpinnerGender.setAdapter(spinnerAdapter);
//        mSpinnerGender.setSelection(spinnerAdapter.getCount());
//
//        mSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });



        recyclerView = (RecyclerView)findViewById(R.id.artisan_skills_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(ArtisanReg_Activity.this));

        mCategory = (TextView)findViewById(R.id.category);
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(ArtisanReg_Activity.this, DropDownList_Activity.class);
                i.putExtra("items", SkillArray);
                i.putExtra("context", "ArtisanReg_Activity");
                startActivityForResult(i, 105);
                ArtisanReg_Activity.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
        });

        mResidenceState = (EditText)findViewById(R.id.state_residence);
        mResidenceState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ArtisanReg_Activity.this, DropDownList_Activity.class);
                i.putExtra("items", ItemsArray);
                i.putExtra("context", "ArtisanReg_Activity");
                startActivityForResult(i, 106);
                ArtisanReg_Activity.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
        });



        mFirstname = (EditText)findViewById(R.id.firstname_txt);

        mSurname = (EditText)findViewById(R.id.surname_txt);

        mPhonenumber = (EditText)findViewById(R.id.mobileno_txt);

        mPassword = (EditText)findViewById(R.id.password_txt);

        mConfirmPassword = (EditText)findViewById(R.id.confirm_txt);

        RegBtn = (Button)findViewById(R.id.register_artisan);
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Firstname = mFirstname.getText().toString().trim();
                Surname = mSurname.getText().toString().trim();
                Phonenumber = mPhonenumber.getText().toString().trim();
                Password = mPassword.getText().toString().trim();
                ConfirmPassword = mConfirmPassword.getText().toString();
                residence_state = mResidenceState.getText().toString();

                if (TextUtils.isEmpty(mFirstname.getText()) && TextUtils.isEmpty(mSurname.getText()) &&
                        TextUtils.isEmpty(mPhonenumber.getText()) && TextUtils.isEmpty(mPassword.getText()) &&
                        TextUtils.isEmpty(mConfirmPassword.getText()) && TextUtils.isEmpty(mResidenceState.getText())){

                    mFirstname.setError("Field Information Required");
                    mSurname.setError("Field Information Required");
                    mPhonenumber.setError("Field Information Required");
                    mPassword.setError("Field Information Required");
                    mConfirmPassword.setError("Field Information Required");
                    mResidenceState.setError("Field Information Required");

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

                else if(TextUtils.isEmpty(mResidenceState.getText())){
                    mResidenceState.setError("Field Information Required");
                }

                else if(ConfirmPassword.equals(Password) == false){

                    mConfirmPassword.setError("Passwords Do Not Match");
                }

                else if(Phonenumber.length() < 11){
                    mPhonenumber.setError("Incomplete Phone Number Entry..");

                }

                else if(!(skills_list.size() > 0)){

                    Toast.makeText(ArtisanReg_Activity.this,"Please Include Skill(s)",Toast.LENGTH_LONG).show();
                }


                else {

                    try {

                        uri = "method=artisanregistration&firstname="+URLEncoder.encode(Firstname,"UTF-8")+"&lastname="+URLEncoder.encode(Surname, "UTF-8")+"&password="+URLEncoder.encode(Password,"UTF-8")+"&residentStateid="+residentStateId+"&phonenumber="+Phonenumber+"&skills="+URLEncoder.encode(new skillsJson().toJSON(skills_list).toString(), "UTF-8");

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
            System.out.println("State Position = "+Id);

            switch (requestCode){

                case 105:
                    Skill obj = new Skill();
                    obj.setId(Integer.parseInt(skill_array_Ids[Id]));
                    obj.setSkillName(val);

                    if(skills_list.size() > 0){

                        skills_list.add(obj);
                        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                        skillsAdapter.notifyItemInserted(linearLayoutManager.getItemCount());
                    }

                    else{
                        skills_list.add(obj);
                        recyclerView.setVisibility(View.VISIBLE);
                        skillsAdapter = new SkillsAdapter2(ArtisanReg_Activity.this, skills_list);
                        recyclerView.setAdapter(skillsAdapter);
                    }
                    break;

                case 106:
                    mResidenceState.setText(val);
                    residentStateId = state_array_Ids[Id];
                    break;

                default:

                    break;
            }

        }


    }

//    public static  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // use the current date as the default date
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//
//            return new DatePickerDialog(getActivity(),this,year,month,day);
//        }
//
//        @Override
//        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//            newdate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
//
//            mDateofBirth.setText(Date_text_formatter(newdate));
//        }
//    }



    private class GetStates extends AsyncTask<Void, Void, JSONArray>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            transparentProgressDialog = new TransparentProgressDialog(ArtisanReg_Activity.this, R.mipmap.rotate_right);
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

            if(s == null){

                Toast.makeText(ArtisanReg_Activity.this,"Connection Timeout",Toast.LENGTH_LONG).show();

            }

            else {

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
            }

            new GetSkills().execute();
        }


    }


    private class GetSkills extends AsyncTask<Void, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(Void... voids) {
            try {


                JSONArray output = new Connection_Service().getJSONfromUrl("method=getskills");

                return output;

            } catch (Exception e){

                e.printStackTrace();

            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);


            if(jsonArray == null){

                Toast.makeText(ArtisanReg_Activity.this,"Connection Timeout",Toast.LENGTH_LONG).show();

            }

            else {

                skill_array_Ids = new String[jsonArray.length()];
                SkillArray = new String[jsonArray.length()];

                for(int i = 0; i < jsonArray.length(); i++){

                    try {
                        String id = jsonArray.getJSONObject(i).get("Id").toString();
                        String name = jsonArray.getJSONObject(i).get("SkillName").toString();

                        skill_array_Ids[i] = id;
                        SkillArray[i] = name;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            //pd.dismiss();
            transparentProgressDialog.dismiss();


        }
    }

    private class RegistrationTask extends AsyncTask<String, Void, String>{
        String output;
        TransparentProgressDialog transparentProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            transparentProgressDialog = new TransparentProgressDialog(ArtisanReg_Activity.this, R.mipmap.rotate_right);
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


            if(s.startsWith("1")){

                AccountDetails obj = new AccountDetails();
                obj.setPhone(Phonenumber);
                obj.setAccName("Artisan");

                if(accountTypeDb.addAccountRecord(obj) == 1){ // insert artisan as accountType into sqlite

                    Intent i = new Intent(ArtisanReg_Activity.this, Menu.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(i);

                    finish();
                }

                else {
                    Toast.makeText(ArtisanReg_Activity.this,"Failed to perform Action for saving",Toast.LENGTH_LONG).show();
                }

            }

            else {

                new progressDialog().error_Dialog(ArtisanReg_Activity.this, s.trim().toString(), "Alert");
            }
        }

    }


}
