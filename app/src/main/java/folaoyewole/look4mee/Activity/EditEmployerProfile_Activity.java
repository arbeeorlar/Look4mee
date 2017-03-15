package folaoyewole.look4mee.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import folaoyewole.look4mee.DbHelper.AccountType_Db;
import folaoyewole.look4mee.R;
import folaoyewole.look4mee.Utilities.Connection_Service;
import folaoyewole.look4mee.Utilities.RealPathUtil;
import folaoyewole.look4mee.Utilities.TransparentProgressDialog;
import folaoyewole.look4mee.adapter.SamplePhotos_Adapter;
import folaoyewole.look4mee.adapter.SkillsAdapter;

public class EditEmployerProfile_Activity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employer_profile);

        accountTypeDb = new AccountType_Db(EditEmployerProfile_Activity.this);

        _uri = "method=getEmployerProfile&phoneNumber="+accountTypeDb.getAccount_Record().get(0).getPhone();


        new getEmployerProfileTask().execute();

        editprofile_name_txt = (TextView)findViewById(R.id.editprofile_employer_titlename_txt);
        full_name = (TextView)findViewById(R.id.editprofile_employer_fullname);
        mMobile = (EditText)findViewById(R.id.editprofile_employer_mobile);

        mEditProfileName = (ImageButton)findViewById(R.id.editprofile_employer_editfullname);
        mEditProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditEmployerProfile_Activity.this);
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

                            Toast.makeText(EditEmployerProfile_Activity.this, "Please enter a Profile Name", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            full_name.setText(editedName);
                            editprofile_name_txt.setText(editedName);

                        }

                        dialog.dismiss();

                    }

                });


            }
        });



        mStateOfResidence = (EditText)findViewById(R.id.editprofile_employer_state_of_residence);
        mStateOfResidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(EditEmployerProfile_Activity.this, DropDownList_Activity.class);
                i.putExtra("items", ItemArray);
                i.putExtra("context", "EditEmployerProfile_Activity");
                startActivityForResult(i, 201);
                EditEmployerProfile_Activity.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

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

                case 11:

                    try {

                        if(Build.VERSION.SDK_INT<11){
                            bitmapPath= RealPathUtil.getRealPathFromURI_BelowAPI11(EditEmployerProfile_Activity.this,data.getData());
                        } else if (Build.VERSION.SDK_INT < 19) {
                            bitmapPath= RealPathUtil.getRealPathFromURI_API11to18(EditEmployerProfile_Activity.this,data.getData());
                        } else {

                            bitmapPath= RealPathUtil.getRealPathFromURI_API19(EditEmployerProfile_Activity.this,data.getData());

                        }

                        Uri uriFromPath = Uri.fromFile(new File(bitmapPath));
                        bitmap = BitmapFactory.decodeStream(EditEmployerProfile_Activity.this.getContentResolver().openInputStream(uriFromPath));


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

            EditEmployerProfile_Activity.this.finish();

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

            dialog = new TransparentProgressDialog(EditEmployerProfile_Activity.this, R.mipmap.rotate_right);
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

                editprofile_name_txt.setText(Fullname);
                full_name.setText(Fullname);
                mMobile.setText(mobile);
                mStateOfResidence.setText(stateofResidence);


                // parse the states json result
                ItemArray = new String[json_states.length()];
                state_array_Ids = new String[json_states.length()];

                for(int n = 0; n < json_states.length(); n++){

                    String id = json_states.getJSONObject(n).get("Id").toString();
                    String state = json_states.getJSONObject(n).get("stateName").toString();

                    state_array_Ids[n] = id;
                    ItemArray[n] = state;
                }

            } catch (JSONException e) {
                e.printStackTrace();

            } catch (Exception e){

                Toast.makeText(EditEmployerProfile_Activity.this, "Could not complete retrieval process", Toast.LENGTH_LONG).show();
            }


        }
    }


}
