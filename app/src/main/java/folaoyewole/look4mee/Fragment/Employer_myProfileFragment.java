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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import folaoyewole.look4mee.Activity.EditEmployerProfile_Activity;
import folaoyewole.look4mee.R;
import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Employer_myProfileFragment extends Fragment {

    private TextView fullname, titleFullname, mMobileno, mResidentState;
    private String[] ItemArray;
    private String[] state_array_Ids;
    private AccountType_Db accountTypeDb;
    private String _uri;



    public Employer_myProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountTypeDb = new AccountType_Db(getActivity());

        _uri = "method=getEmployerProfile&phoneNumber="+accountTypeDb.getAccount_Record().get(0).getPhone();

        new getEmployerProfileTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_employer_my_profile, container, false);

        setHasOptionsMenu(true); // contains options menu in actionbar

        titleFullname = (TextView)v.findViewById(R.id.profile_employer_name_txt);
        fullname = (TextView)v.findViewById(R.id.profile_employer_fullname);
        mMobileno = (TextView)v.findViewById(R.id.profile_employer_mobile);
        mResidentState = (TextView)v.findViewById(R.id.profile_employer_resState);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear(); // clear whatever menu is existing

        inflater.inflate(R.menu.menu_employer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_menu_about_employer) {
            return true;
        }

        else if(id == R.id.option_menu_editprofileEmployer){

            Intent i = new Intent(getActivity(), EditEmployerProfile_Activity.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);

    }

    private class getEmployerProfileTask extends AsyncTask<Void, Void, JSONObject> {

        TransparentProgressDialog dialog;
        JSONObject output;
        JSONArray  json_states;

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

                json_states = new Connection_Service().getJSONfromUrl("method=getstates");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            dialog.dismiss();

            try {
                String firstname = jsonObject.get("firstname").toString();
                String lastname = jsonObject.get("lastname").toString();
                String mobile = jsonObject.get("phoneNumber").toString();
                String stateofResidence = jsonObject.get("stateofResidentName").toString();

                String Fullname = lastname+" "+firstname;

                titleFullname.setText(Fullname);
                fullname.setText(Fullname);
                mMobileno.setText(mobile);
                mResidentState.setText(stateofResidence);


            } catch (JSONException e) {
                e.printStackTrace();

            } catch (Exception e){

                Toast.makeText(getActivity(), "Could not complete retrieval process", Toast.LENGTH_LONG).show();
            }


        }
    }
}
