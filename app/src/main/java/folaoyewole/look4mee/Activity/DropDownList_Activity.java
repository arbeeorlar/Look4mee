package folaoyewole.look4mee.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import folaoyewole.look4mee.R;

public class DropDownList_Activity extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> adapter;
    String[] mItems;
    String context_string;
    int actual_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_list);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        try {

            mItems = getIntent().getStringArrayExtra("items");
            context_string = getIntent().getStringExtra("context");

            listView = (ListView)findViewById(R.id.list_item);

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItems);

            listView.setAdapter(adapter);
            listView.setTextFilterEnabled(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String out = parent.getItemAtPosition(position).toString();
                    Intent i;

                    if(context_string.equals("ArtisanReg_Activity")){

                        i = new Intent(DropDownList_Activity.this,ArtisanReg_Activity.class);

                    }

                    else if(context_string.equals("EditArtisanProfile_Activity")){

                        i = new Intent(DropDownList_Activity.this,EditArtisanProfile_Activity.class);
                    }


                    else {

                        i = new Intent(DropDownList_Activity.this,EmployerReg_Activity.class);

                    }

                    for(int n = 0; n < mItems.length; n++){

                        if(mItems[n].equals(out)){
                            actual_position = n;
                        }
                    }

                    i.putExtra("ListItem", out);
                    i.putExtra("ListPosition", actual_position);
                    setResult(RESULT_OK, i);
                    DropDownList_Activity.this.finish();

                }
            });

        } catch (Exception e){

            Toast.makeText(DropDownList_Activity.this,"No Item to display",Toast.LENGTH_LONG).show();

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id._search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);

        SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                System.out.println("on query submit: "+query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                System.out.println("On text Change text: "+newText);

                return true;
            }
        };
        searchView.setOnQueryTextListener(textListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:

                DropDownList_Activity.this.finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
