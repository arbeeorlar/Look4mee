package folaoyewole.look4mee.Fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import folaoyewole.look4mee.R;
import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostJob_Fragment extends Fragment {

    EditText mRequiredSkills, mJobDesc;
    String JobDesc, safe_string_postjob, employerID, SkillID;
    Button Post_Btn;
    private String[] skill_array_Ids;
    private String[] SkillArray;



    public PostJob_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GetRequiredSkills_task().execute();

        employerID =  new AccountType_Db(getActivity()).getAccount_Record().get(0).getEmployerID();

        System.out.println("Employer ID = "+employerID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_post_job, container, false);

        setHasOptionsMenu(true); // contains options menu in actionbar

        mRequiredSkills = (EditText)v.findViewById(R.id.post_jobs_skills);
        mJobDesc = (EditText)v.findViewById(R.id.post_job_desc);
        Post_Btn = (Button)v.findViewById(R.id.post_btn);

        // Disable post button initially
        Post_Btn.setEnabled(false);
        Post_Btn.setAlpha(0.6f);

        mRequiredSkills.setText("Aircondition Repairer");
        mRequiredSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Skill");
                builder.setItems(SkillArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRequiredSkills.setText(SkillArray[i]);
                        SkillID = skill_array_Ids[i];
                    }
                });
                builder.show();

            }
        });

        mJobDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(mJobDesc.getText().toString().trim().equals("") || mJobDesc.getText().length() < 0){
                    Post_Btn.setAlpha(0.6f);
                    Post_Btn.setEnabled(false);
                } else {

                    Post_Btn.setAlpha(1f);
                    Post_Btn.setEnabled(true);
                }

            }
        });

        Post_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JobDesc = mJobDesc.getText().toString().trim();

                try {

                    safe_string_postjob = "method=employerpostjob&jobDescription="+URLEncoder.encode(JobDesc, "UTF-8")+
                            "&employerId="+URLEncoder.encode(employerID, "UTF-8")
                            +"&skillId="+SkillID;

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                new PostJobTask().execute();
            }
        });


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear(); // clear whatever menu is existing

        inflater.inflate(R.menu.menu_myfeeds, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_about) {
            return true;
        }

//        else if(id == R.id.option_menu_editprofileEmployer){
//
//            Intent i = new Intent(getActivity(), EditArtisanProfile_Activity.class);
//            startActivity(i);
//            return true;
//        }



        return super.onOptionsItemSelected(item);

    }


    private class GetRequiredSkills_task extends AsyncTask<Void, Void, JSONArray>{

        TransparentProgressDialog transparentProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            transparentProgressDialog = new TransparentProgressDialog(getActivity(), R.mipmap.rotate_right);
            transparentProgressDialog.setCanceledOnTouchOutside(false);
            transparentProgressDialog.show();
        }

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

            transparentProgressDialog.dismiss();

            if(jsonArray == null){

                Toast.makeText(getActivity(),"Connection Timeout",Toast.LENGTH_LONG).show();

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

                mRequiredSkills.setText(SkillArray[0]);

            }

        }
    }

    private class PostJobTask extends AsyncTask<Void,Void,String>{

        TransparentProgressDialog transparentProgressDialog;

        String output;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            transparentProgressDialog = new TransparentProgressDialog(getActivity(), R.mipmap.rotate_right);
            transparentProgressDialog.setCanceledOnTouchOutside(false);
            transparentProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                 output = new Connection_Service().getResponsefromUrl(safe_string_postjob);

            } catch (Exception e){

                e.printStackTrace();
                Toast toast = Toast.makeText(getActivity(),"Unable to post job, Please retry ..",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }

            return output;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            transparentProgressDialog.dismiss();

            if(s.trim().startsWith("1")){


                Toast.makeText(getActivity(), "Job Post Was Successful..", Toast.LENGTH_LONG).show();

                mJobDesc.setText("");
            }

            else {

                Toast toast = Toast.makeText(getActivity(),"Unable to post job",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        }
    }
}
