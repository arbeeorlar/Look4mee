package folaoyewole.look4mee.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import folaoyewole.look4mee.R;
import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.DbHelper.ProfileDB;
import folaoyewole.look4mee.Model.PostEmployer;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.SimpleDividerItemDecoration;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;
import folaoyewole.look4mee.adapter.EmployerViewPosts_Adapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployerViewPosts extends Fragment {

    RecyclerView mRecycler;
    LinearLayoutManager mLayoutManager  = null;
    EmployerViewPosts_Adapter adapter;
    ArrayList<PostEmployer> list;
    private RelativeLayout mRelative;
    private String _uri;
    ProfileDB mProfileDB;
    AccountType_Db accountTypeDb;


    public EmployerViewPosts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileDB = new ProfileDB(getActivity());
        accountTypeDb = new AccountType_Db(getActivity());

        //_uri = "method=GetArtisanSkill&username="+accountTypeDb.getAccount_Record().get(0).getPhone();
        _uri = "method=employerpostedjobs&employerId="+accountTypeDb.getAccount_Record().get(0).getEmployerID();

        new GetPosts().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_employer_view_posts, container, false);

        mRelative = (RelativeLayout)v.findViewById(R.id.rel_no_post);
        mRecycler = (RecyclerView)v.findViewById(R.id.list_employer_viewpost);
        mRecycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        return v;
    }


    private class GetPosts extends AsyncTask<Void, Void, JSONArray>{

        private String response;
        TransparentProgressDialog dialog;
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new TransparentProgressDialog(getActivity(), R.mipmap.rotate_right);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            System.out.println("Url = "+_uri);
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            try {
                jsonArray = new Connection_Service().getJSONfromUrl(_uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray s) {
            super.onPostExecute(s);

            dialog.dismiss();

            try {

                int size = s.length();

                if(size > 0){

                    list = new ArrayList<>();

                    for(int i = 0; i < jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);

                        PostEmployer postEmployer = new PostEmployer();
                        postEmployer.setDescription(object.get("JobDescription").toString());
                        postEmployer.setUploadtime(object.getString("PostedDate").toString());
                        postEmployer.setJobTitle(object.getString("JobTitle").toString());

                        list.add(0,postEmployer);

                    }

                    mRelative.setVisibility(View.GONE);  // Incase its visible


                    adapter = new EmployerViewPosts_Adapter(getActivity(), list);
                    mRecycler.setAdapter(adapter);

                }

//                else if(s.trim().startsWith("Connection Lost")){
//
//                    Toast.makeText(getActivity(), "Connection lost..Could not complete process", Toast.LENGTH_LONG).show();
//
//                }

                else if(size == 0) {
                    mRelative.setVisibility(View.VISIBLE);
                }


            } catch (Exception e){

                Toast.makeText(getActivity(), "Connection Timeout..Could not complete process", Toast.LENGTH_LONG).show();

            }


        }
    }

    private class ViewEmployerTask extends AsyncTask<Void, Void, String>{

        private String response;
        TransparentProgressDialog dialog;
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new TransparentProgressDialog(getActivity(), R.mipmap.rotate_right);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("Result = "+s);
        }
    }

}
