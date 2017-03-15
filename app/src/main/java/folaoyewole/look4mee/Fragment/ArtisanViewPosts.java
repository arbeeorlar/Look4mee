package folaoyewole.look4mee.Fragment;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import folaoyewole.look4mee.R;
import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.DbHelper.ProfileDB;
import folaoyewole.look4mee.Model.PostArtisan;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.SimpleDividerItemDecoration;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;
import folaoyewole.look4mee.adapter.ArtisanViewPosts_Adapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtisanViewPosts extends Fragment {

    RecyclerView mRecycler;
    LinearLayoutManager mLayoutManager  = null;
    ArtisanViewPosts_Adapter adapter;
    ArrayList<PostArtisan> list;
    ProfileDB mProfileDB;
    AccountType_Db accountTypeDb;
    private String _uri;
    String[] skills_list;
    String[] skills_ids;
    RelativeLayout mRelative;
    Integer initialSkillPos = 0;

    public ArtisanViewPosts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileDB = new ProfileDB(getActivity());
        accountTypeDb = new AccountType_Db(getActivity());

        _uri = "method=GetArtisanSkill&username="+accountTypeDb.getAccount_Record().get(0).getPhone();

        new getPostsTask().execute(initialSkillPos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true); // contains options menu in actionbar


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_artisan_view_posts, container, false);

        mRelative = (RelativeLayout)v.findViewById(R.id.rel_no_post);
        mRecycler = (RecyclerView)v.findViewById(R.id.list_artisan_viewpost);
        mRecycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear(); // clear whatever menu is existing

        inflater.inflate(R.menu.menu_artisanviewposts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id._show_skill){

            try {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final AlertDialog dialog;
                View v = getActivity().getLayoutInflater().inflate(R.layout.artisanpost_skills_dialog,null);
                ListView listView = (ListView)v.findViewById(R.id.skills_for_artisan);
                String[] items = skills_list;
                ArrayAdapter arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);

                builder.setView(v);
                dialog = builder.create();
                //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        new getPostsTask().execute(i);
                        dialog.dismiss();

                    }
                });

            } catch (Exception e){

            }


        }


        return super.onOptionsItemSelected(item);

    }

    private class getPostsTask extends AsyncTask<Integer, Void, String> {

        TransparentProgressDialog dialog;
        JSONArray output;
        String uri, jsonString;
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new TransparentProgressDialog(getActivity(), R.mipmap.rotate_right);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Integer... param) {

            try {

                output = new Connection_Service().getJSONfromUrl(_uri);
                int len = output.length();

                skills_list = new String[len];
                skills_ids = new String[len];

                System.out.println("Param = "+param[0]);
                System.out.println("json array length = "+len);

                for(int i = 0; i < len; i++){

                    skills_ids[i] = output.getJSONObject(i).get("Id").toString();
                    skills_list[i] = output.getJSONObject(i).get("SkillName").toString();
                }
                uri = "method=listpostedjobs&skillId="+skills_ids[param[0]];
               // uri = "method=listpostedjobs&skillId="+5;

                jsonString = new Connection_Service().getJSONArrayString(uri);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonString;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);

            dialog.dismiss();


                try {

                    if(string.trim().startsWith("[]")){

                        mRelative.setVisibility(View.VISIBLE);

                    }

                    else if(string.trim().startsWith("Connection Lost")){

                        Toast.makeText(getActivity(), "Connection Timeout..Could not complete process", Toast.LENGTH_LONG).show();

                    }

                    else {

                        jsonArray = new JSONArray(string);

                        list = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject object = jsonArray.getJSONObject(i);

                            PostArtisan postArtisan = new PostArtisan();
                            postArtisan.setDescription(object.get("JobDescription").toString());
                            postArtisan.setFullname(object.get("fullname").toString());

                            list.add(0,postArtisan);

                        }


                        mRelative.setVisibility(View.GONE); // just incase no posts is visible

                        adapter = new ArtisanViewPosts_Adapter(getActivity(), list);

                        mRecycler.setAdapter(adapter);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Connection Timeout..Could not complete process", Toast.LENGTH_LONG).show();

                }

        }
    }

}
