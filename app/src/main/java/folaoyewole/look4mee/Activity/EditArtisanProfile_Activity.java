package folaoyewole.look4mee.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.R;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.RealPathUtil;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;
import folaoyewole.look4mee.adapter.SamplePhotos_Adapter;
import folaoyewole.look4mee.adapter.SkillsAdapter;

public class EditArtisanProfile_Activity extends AppCompatActivity {

    ImageButton addSkillsImageButton, addSamplePhotosImageButton;
    ArrayList<String> skills_list = new ArrayList<>();
    ArrayList<Bitmap> sample_photos_list = new ArrayList<>();
    SkillsAdapter skillsAdapter;
    SamplePhotos_Adapter photosAdapter;
    RecyclerView recyclerView, photo_recyclerView;
    TextView noItemTextview, noPhotosTextview, editprofile_name_txt, full_name;
    private String bitmapPath, _uri;
    private String[] SkillsArray, skill_array_Ids;
    private Bitmap bitmap;
    private CircleImageView addProfileImage;
    private ImageButton mEditProfileName;
    private EditText mMobile;
    private EditText mStateOfResidence;
    AccountType_Db accountTypeDb;
    private String[] ItemArray;
    private String[] state_array_Ids;
    private String update_url;
    private HashMap<String, String> residentState = new HashMap<>();
    private HashMap<String,String> skills_map = new HashMap<>();


    JSONArray jsonArray_skills, json_states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountTypeDb = new AccountType_Db(EditArtisanProfile_Activity.this);

        _uri = "method=getartisanProfile&phoneNumber="+accountTypeDb.getAccount_Record().get(0).getPhone();

        new getProfileTask().execute();

        setContentView(R.layout.activity_edit_profile);

        editprofile_name_txt = (TextView)findViewById(R.id.editprofile_artisan_titlename_txt);
        full_name = (TextView)findViewById(R.id.editprofile_artisan_fullname);
        mMobile = (EditText)findViewById(R.id.editprofile_Mobile);

        mEditProfileName = (ImageButton)findViewById(R.id.editprofile_artisan_editfullname);
        mEditProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditArtisanProfile_Activity.this);
                final View dialogView = getLayoutInflater().inflate(R.layout.editname_dialog, null);
                final EditText nameEditText = (EditText)dialogView.findViewById(R.id.editname_nameEditText);
                final Button doneBtn = (Button)dialogView.findViewById(R.id.doneButton);
                builder.setView(dialogView);
                String profile_name = editprofile_name_txt.getText().toString();
                nameEditText.setText(profile_name);
                final AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String editedName = nameEditText.getText().toString().trim();

                        if (editedName.length() == 0){

                            Toast.makeText(EditArtisanProfile_Activity.this, "Please enter a Profile Name", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            full_name.setText(editedName);
                            editprofile_name_txt.setText(editedName);

                        }

                        dialog.dismiss();

                    }

                });
//                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        String editedName = nameEditText.getText().toString().trim();
//
//                        if (editedName.length() == 0){
//
//                            Toast.makeText(EditArtisanProfile_Activity.this, "Please enter a Profile Name", Toast.LENGTH_SHORT).show();
//
//                        }
//                        else{
//                            editprofile_name_txt.setText(editedName);
//
//                        }
//                    }
//                });



            }
        });


        mStateOfResidence = (EditText)findViewById(R.id.editprofile_state_of_residence_origin);
        mStateOfResidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(EditArtisanProfile_Activity.this, DropDownList_Activity.class);
                i.putExtra("items", ItemArray);
                i.putExtra("context", "EditArtisanProfile_Activity");
                startActivityForResult(i, 201);
                EditArtisanProfile_Activity.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
        });

        addSkillsImageButton = (ImageButton)findViewById(R.id.edit_add_skills);
        addSkillsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(EditArtisanProfile_Activity.this, DropDownList_Activity.class);
                i.putExtra("items", SkillsArray);
                i.putExtra("context", "EditArtisanProfile_Activity");
                startActivityForResult(i, 305);
                EditArtisanProfile_Activity.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.editskills_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(EditArtisanProfile_Activity.this));

        noItemTextview = (TextView)findViewById(R.id.no_skill_txt);
        noPhotosTextview = (TextView)findViewById(R.id.no_sample_jobs_photo_txt);

        photo_recyclerView = (RecyclerView)findViewById(R.id.editsample_jobs_photo_recycler);
        photo_recyclerView.setHasFixedSize(true);
        photo_recyclerView.setItemAnimator(new DefaultItemAnimator());
        photo_recyclerView.setLayoutManager(new LinearLayoutManager(EditArtisanProfile_Activity.this,LinearLayoutManager.HORIZONTAL,false));

        addSamplePhotosImageButton = (ImageButton)findViewById(R.id.edit_add_sample_jobs_photo);
        addSamplePhotosImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/* video/*");
                startActivityForResult(i,10);
            }
        });


        addProfileImage = (CircleImageView)findViewById(R.id.user_edit_profile_photo);
        addProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,11);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){

            String val = data.getStringExtra("ListItem");

            switch (requestCode){

                case 201:
                    mStateOfResidence.setText(val);
                    break;

                case 305:

                    if(skills_list.size() > 0){
                        skills_list.add(val);
                        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                        skillsAdapter.notifyItemInserted(linearLayoutManager.getItemCount());
                    }

                    else{
                        skills_list.add(val);
                        noItemTextview.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        skillsAdapter = new SkillsAdapter(EditArtisanProfile_Activity.this, skills_list);
                        recyclerView.setAdapter(skillsAdapter);
                    }


                    break;

                case 10:

                    try {

                    if(Build.VERSION.SDK_INT<11){
                        bitmapPath= RealPathUtil.getRealPathFromURI_BelowAPI11(EditArtisanProfile_Activity.this,data.getData());
                    } else if (Build.VERSION.SDK_INT < 19) {
                        bitmapPath= RealPathUtil.getRealPathFromURI_API11to18(EditArtisanProfile_Activity.this,data.getData());
                    } else {

                            bitmapPath= RealPathUtil.getRealPathFromURI_API19(EditArtisanProfile_Activity.this,data.getData());
                    }

                        Uri uriFromPath = Uri.fromFile(new File(bitmapPath));
                        bitmap = BitmapFactory.decodeStream(EditArtisanProfile_Activity.this.getContentResolver().openInputStream(uriFromPath));


                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    if(sample_photos_list.size() > 0){

                        sample_photos_list.add(bitmap);
                        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)photo_recyclerView.getLayoutManager();
                        photosAdapter.notifyItemInserted(linearLayoutManager.getItemCount());
                    }

                    else{
                        sample_photos_list.add(bitmap);
                        noPhotosTextview.setVisibility(View.GONE);
                        photo_recyclerView.setVisibility(View.VISIBLE);
                        photosAdapter = new SamplePhotos_Adapter(EditArtisanProfile_Activity.this, sample_photos_list);
                        photo_recyclerView.setAdapter(photosAdapter);

                    }

                    break;

                case 11:

                    try {

                    if(Build.VERSION.SDK_INT<11){
                        bitmapPath= RealPathUtil.getRealPathFromURI_BelowAPI11(EditArtisanProfile_Activity.this,data.getData());
                    } else if (Build.VERSION.SDK_INT < 19) {
                        bitmapPath= RealPathUtil.getRealPathFromURI_API11to18(EditArtisanProfile_Activity.this,data.getData());
                    } else {

                        bitmapPath= RealPathUtil.getRealPathFromURI_API19(EditArtisanProfile_Activity.this,data.getData());

                    }

                        Uri uriFromPath = Uri.fromFile(new File(bitmapPath));
                        bitmap = BitmapFactory.decodeStream(EditArtisanProfile_Activity.this.getContentResolver().openInputStream(uriFromPath));


                        addProfileImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                default:

                    break;
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_done) {



            String[] names = full_name.getText().toString().trim().split(Pattern.quote(" "));
            String mobile = mMobile.getText().toString().trim();

            String surname = names[0];
            String firstname = names[1];

            String residentID = residentState.get(mStateOfResidence.getText().toString());

            StringBuilder builder = new StringBuilder();
            for (int i=0; i < skills_list.size(); i++){

                builder.append(skills_map.get(skills_list.get(i)));
                if(i != skills_list.size() - 1){
                    builder.append(",");
                }
            }

            String newSkillsID = builder.toString();

            if(mobile.length() > 0 && surname.length() > 0 && firstname.length() > 0 &&
                    residentID.length() > 0 && newSkillsID.length() > 0){
                update_url = "method=updateartisanprofile&phonenumber="+mobile+"&firstname="+firstname+"&lastname="+surname+"&skills="+newSkillsID+"&residentstateid="+residentID;

                new UpdateProfileTask().execute();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static String Date_text_formatter(Date date){

        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat D = new SimpleDateFormat("EEEE , MMMM dd, yyyy");

        String x;

        //  x =D.format(date)+"-"+ d.format(date)+"-"+m.format(date)+"-"+y.format(date);
        x = D.format(date);

        return x;
    }


    private class getProfileTask extends AsyncTask<Void, Void, JSONObject> {

        TransparentProgressDialog dialog;
        JSONObject output;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new TransparentProgressDialog(EditArtisanProfile_Activity.this, R.mipmap.rotate_right);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            try {
                output = new Connection_Service().getJSONOBJfromURL(_uri);

                jsonArray_skills = new Connection_Service().getJSONfromUrl("method=getskills");
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

                        skills_list.add(strings[i]);
                    }

                    editprofile_name_txt.setText(Fullname);
                    full_name.setText(Fullname);
                    mMobile.setText(mobile);
                    mStateOfResidence.setText(stateofResidence);

                    // update skills recycler view
                    noItemTextview.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    skillsAdapter = new SkillsAdapter(EditArtisanProfile_Activity.this, skills_list);
                    recyclerView.setAdapter(skillsAdapter);


                    // parse the json result of artisan skills
                    skill_array_Ids = new String[jsonArray_skills.length()];
                    SkillsArray = new String[jsonArray_skills.length()];

                    for(int i = 0; i < jsonArray_skills.length(); i++){

                        String id = jsonArray_skills.getJSONObject(i).get("Id").toString();
                        String name = jsonArray_skills.getJSONObject(i).get("SkillName").toString();

                        skill_array_Ids[i] = id;
                        SkillsArray[i] = name;

                        skills_map.put(name, id);  // insert into skills hashmap
                    }

                    // parse the states json result
                    ItemArray = new String[json_states.length()];
                    state_array_Ids = new String[json_states.length()];

                    for(int n = 0; n < json_states.length(); n++){

                        String id = json_states.getJSONObject(n).get("Id").toString();
                        String state = json_states.getJSONObject(n).get("stateName").toString();

                        state_array_Ids[n] = id;
                        ItemArray[n] = state;

                        residentState.put(state, id); // insert into resisdence hashmap

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (Exception e){

                    Toast.makeText(EditArtisanProfile_Activity.this, "Could not complete retrieval process", Toast.LENGTH_LONG).show();
                }


        }
    }

    private class UpdateProfileTask extends AsyncTask<Void, Void, String>{

        TransparentProgressDialog dialog;
        String output;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new TransparentProgressDialog(EditArtisanProfile_Activity.this, R.mipmap.rotate_right);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {

                output = new Connection_Service().getResponsefromUrl(update_url);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();

            if(s.trim().startsWith("1")){

                Toast.makeText(EditArtisanProfile_Activity.this, "Profile Update Successful!",Toast.LENGTH_LONG).show();

                Intent i = new Intent(EditArtisanProfile_Activity.this, Menu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                finish();
            }

            else if(s.trim().startsWith("0")){
                Toast.makeText(EditArtisanProfile_Activity.this, "Failed to update artisan profile",Toast.LENGTH_LONG).show();
            }

            else {
                Toast.makeText(EditArtisanProfile_Activity.this, s, Toast.LENGTH_LONG).show();
            }
        }
    }

}
