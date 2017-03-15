package folaoyewole.look4mee.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import folaoyewole.look4mee.Activity.EditArtisanProfile_Activity;
import folaoyewole.look4mee.R;
import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.DbHelper.ProfileDB;
import folaoyewole.look4mee.Model.ArtisanProfile;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Artisan_myProfileFragment extends Fragment {

    ProfileDB mProfileDB;
    AccountType_Db accountTypeDb;
    private TextView mFullname, mMobile, mStateOfResidence, mSkills;
    private String _uri;

    public Artisan_myProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileDB = new ProfileDB(getActivity());
        accountTypeDb = new AccountType_Db(getActivity());


//        if (mProfileDB.getProfile_Records().size() > 0 == false){
//
//            _uri = "method=getartisanProfile&phoneNumber="+accountTypeDb.getAccount_Record().get(0).getPhone();
//
//            new getProfileTask().execute();
//        }

        _uri = "method=getartisanProfile&phoneNumber="+accountTypeDb.getAccount_Record().get(0).getPhone();

        new getProfileTask().execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_myprofile, container, false);

        setHasOptionsMenu(true); // contains options menu in actionbar


        mFullname = (TextView)v.findViewById(R.id.profile_name_txt);
        mMobile = (TextView)v.findViewById(R.id.profile_Mobile);
        mStateOfResidence = (TextView)v.findViewById(R.id.profile_state_of_residence);
        mSkills = (TextView)v.findViewById(R.id.profile_skills);

        return v;
    }


    private void updateProfile(){
        StringBuilder sb = new StringBuilder();
        ArrayList<ArtisanProfile> mProfile;
        mProfile = mProfileDB.getProfile_Records();
        ArtisanProfile profile = mProfile.get(0);

        mFullname.setText(profile.getFullname());
        mMobile.setText(profile.getMobile());
        mStateOfResidence.setText(profile.getResidenceState());

        String[] strings = profile.getSkills().split(",");

        for (int i = 0; i < strings.length; i++){

            sb.append(strings[i]);
            if(!(i == (strings.length -1))){
                sb.append("\n\n");
            }
        }

        mSkills.setText(sb.toString());

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear(); // clear whatever menu is existing

        inflater.inflate(R.menu.menu_artisan, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_menu_about_artisan) {
            return true;
        }

        else if(id == R.id.option_menu_editprofile_artisan){

            Intent i = new Intent(getActivity(), EditArtisanProfile_Activity.class);
            startActivity(i);
            return true;
        }

        else if(id == R.id.option_menu_artisan_logout){

        }

        return super.onOptionsItemSelected(item);

    }

//    private class GetProfileInfo extends AsyncTask<Void, Void, String>{
//
//        String output = null;
//        TransparentProgressDialog transparentProgressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            transparentProgressDialog = new TransparentProgressDialog(getActivity(), R.mipmap.rotate_right);
//            transparentProgressDialog.setCanceledOnTouchOutside(false);
//            transparentProgressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            try {
//                output = new Connection_Service().getResponsefromUrl(uri);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//
//            System.out.println("Service output = "+output);
//
//            return output;
//        }
//
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//
//
//            transparentProgressDialog.dismiss();
//
//            if(s.startsWith("Internet Connection Lost") == false){
//                System.out.println("Service output = "+s);
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String firstname = jsonObject.get("firstname").toString();
//                    String lastname = jsonObject.get("lastname").toString();
//                    String mobile = jsonObject.get("phoneNumber").toString();
//                    String stateofResidentId = jsonObject.get("stateofResidentID").toString();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Toast.makeText(getActivity(),"Connection Timeout",Toast.LENGTH_LONG).show();
//            }
////
////            else {
////
////                try{
////
////                    String fullname = "";
////                    String mobile = "";
////                    String homeAddress = "";
////                    String gender = "";
////                    String birthdate = "" ;
////                    String state_of_origin = "";
////                    String residence_state = "" ;
////                    String area = "";
////                    String skills = "";
////                    String mySkills = "";
////
////                    for(int i = 0; i < jsonArray.length(); i++){
////
////                        fullname = jsonArray.getJSONObject(i).get("").toString();
////                        mobile = jsonArray.getJSONObject(i).get("").toString();
////                        homeAddress = jsonArray.getJSONObject(i).get("").toString();
////                        gender = jsonArray.getJSONObject(i).get("").toString();
////                        birthdate = jsonArray.getJSONObject(i).get("").toString();
////                        state_of_origin = jsonArray.getJSONObject(i).get("").toString();
////                        residence_state = jsonArray.getJSONObject(i).get("").toString();
////                        area = jsonArray.getJSONObject(i).get("").toString();
////                        skills = jsonArray.getJSONObject(i).get("").toString();
////
////                    }
////
////                    String[] _skills = skills.split(Pattern.quote("|"));
////                    StringBuilder sb = new StringBuilder();
////
////                    for (int i = 0; i < _skills.length; i++){
////                        sb.append(_skills[i]+"\n");
////                    }
////
////                    mySkills = sb.toString();
////
////                    // update the profile interface
////                    mFullname.setText(fullname);
////                    mMobile.setText(mobile);
////                    mHomeAddress.setText(homeAddress);
////                    mGender.setText(gender);
////                    mBirthdate.setText(birthdate);
////                    mStateOfOrigin.setText(state_of_origin);
////                    mStateOfResidence.setText(residence_state);
////                    mArea.setText(area);
////                    mSkills.setText(mySkills);
////
////                } catch (Exception e){
////                    e.printStackTrace();
////                }
////
////            }
//        }
//    }


    private class updateProfileTask extends AsyncTask<Void, Void, JSONObject>{

        JSONObject output;

        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                output = new Connection_Service().getJSONOBJfromURL(_uri);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if(jsonObject.length() > 0){

                StringBuilder sb = new StringBuilder();

                try {
                    String firstname = jsonObject.get("firstname").toString();
                    String lastname = jsonObject.get("lastname").toString();
                    String mobile = jsonObject.get("phoneNumber").toString();
                    String stateofResidence = jsonObject.get("stateofResidentName").toString();
                    String skills = jsonObject.getString("skillName").toString();
                    String Fullname = lastname+" "+firstname;

                    String[] strings = skills.split(",");
                    for (int i = 0; i < strings.length; i++){

                        sb.append(strings[i]);
                        if(!(i == (strings.length -1))){
                            sb.append("\n\n");
                        }
                    }

                    String dbFullname = mProfileDB.getProfile_Records().get(0).getFullname();
                    String dbMobile =  mProfileDB.getProfile_Records().get(0).getMobile();
                    String dbStateOfResidence = mProfileDB.getProfile_Records().get(0).getResidenceState();
                    String dbSkills = mProfileDB.getProfile_Records().get(0).getSkills();

                    if(!Fullname.equalsIgnoreCase(dbFullname)){

                    }

                    else if(!mobile.equalsIgnoreCase(dbMobile)){

                    }

                    else if(!stateofResidence.equalsIgnoreCase(dbStateOfResidence)){

                    }

                    else if(!skills.equalsIgnoreCase(dbSkills)){

                    }

                    mFullname.setText(Fullname);
                    mMobile.setText(mobile);
                    mStateOfResidence.setText(stateofResidence);
                    mSkills.setText(sb.toString());


                    // Insert artisan profile into sqliteDB
                    ArtisanProfile profile = new ArtisanProfile();
                    profile.setFullname(Fullname);
                    profile.setMobile(mobile);
                    profile.setResidenceState(stateofResidence);
                    profile.setSkills(skills);

                    mProfileDB.addArtisanProfileRecord(profile);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private class getProfileTask extends AsyncTask<Void, Void, JSONObject>{

        TransparentProgressDialog dialog;
        JSONObject output;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new TransparentProgressDialog(getActivity(), R.mipmap.rotate_right);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                output = new Connection_Service().getJSONOBJfromURL(_uri);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            dialog.dismiss();

                StringBuilder sb = new StringBuilder();

                try {
                    String firstname = jsonObject.get("firstname").toString();
                    String lastname = jsonObject.get("lastname").toString();
                    String mobile = jsonObject.get("phoneNumber").toString();
                    String stateofResidence = jsonObject.get("stateofResidentName").toString();
                    String skills = jsonObject.getString("skillName").toString();

                    String Fullname = lastname+" "+firstname;
                    String[] strings = skills.split(",");
                    for (int i = 0; i < strings.length; i++){

                        sb.append(strings[i]);
                        if(!(i == (strings.length -1))){
                            sb.append("\n\n");
                        }
                    }

                    mFullname.setText(Fullname);
                    mMobile.setText(mobile);
//                    mHomeAddress.setText(profile.getHomeAddress());
//                    mGender.setText(profile.getGender());
//                    mBirthdate.setText(profile.getBirthday());
//                    mStateOfOrigin.setText(profile.getState_of_origin());
                    mStateOfResidence.setText(stateofResidence);
                   // mArea.setText(profile.getArea());
                    mSkills.setText(sb.toString());


                    // Insert artisan profile into sqliteDB
//                    ArtisanProfile profile = new ArtisanProfile();
//                    profile.setFullname(Fullname);
//                    profile.setMobile(mobile);
//                    profile.setResidenceState(stateofResidence);
//                    profile.setSkills(skills);
//
//                    mProfileDB.addArtisanProfileRecord(profile);

                } catch (JSONException e) {
                    e.printStackTrace();


                } catch(Exception e){

                    Toast.makeText(getActivity(), "Could not complete retrieval process", Toast.LENGTH_LONG).show();

                }

        }
    }
}
