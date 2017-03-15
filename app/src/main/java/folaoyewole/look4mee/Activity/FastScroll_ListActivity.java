package folaoyewole.look4mee.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import folaoyewole.look4mee.R;
import folaoyewole.look4mee.adapter.FastScroll_ListAdapter;

public class FastScroll_ListActivity extends AppCompatActivity {

    ListView listView;
    String[] mItems;
    FastScroll_ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_scroll__list);

        mItems = getIntent().getStringArrayExtra("items");

        listView = (ListView)findViewById(R.id.fastscroll_list);
        //listView.setFastScrollEnabled(true);

        List<String> list = Arrays.asList(mItems);
        Collections.sort(list);
        adapter = new FastScroll_ListAdapter(FastScroll_ListActivity.this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox mCheckbox = (CheckBox)view.findViewById(R.id.name_checkbox);

                if(mCheckbox.isChecked()){
                    mCheckbox.setChecked(false);
                }

                else {
                    mCheckbox.setChecked(true);
                }
                adapter.notifyDataSetChanged();


            }
        });




    }
}
