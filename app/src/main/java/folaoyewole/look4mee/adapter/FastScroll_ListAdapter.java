package folaoyewole.look4mee.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import folaoyewole.look4mee.R;

/**
 * Created by sp_developer on 9/26/16.
 */
public class FastScroll_ListAdapter extends ArrayAdapter<String> implements SectionIndexer {

    List<String> ItemsList;
    String[] sections;
    HashMap<String, Integer> mapIndex;

    Context context;
    LayoutInflater getInflater;

    public  InfoHolder holder;
    public SparseBooleanArray mCheckStates;


    public FastScroll_ListAdapter(Context context, List<String> lists) {
        super(context, 0, lists);

        this.ItemsList = lists;
        mapIndex = new LinkedHashMap<String, Integer>();

        mCheckStates = new SparseBooleanArray(lists.size());
        this.context = context;

        for(int i = 0; i < ItemsList.size(); i++){

            String item = ItemsList.get(i);
            String ch = item.substring(0, 1);
            ch = ch.toUpperCase(Locale.US);

            // Hashmap will prevent duplicates
            mapIndex.put(ch, i);
        }

        Set<String> sectionLetters = mapIndex.keySet();

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);

    }

    public SparseBooleanArray getmCheckStates() {
        return mCheckStates;
    }

    public void setmCheckStates(SparseBooleanArray mCheckStates) {
        this.mCheckStates = mCheckStates;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int i) {
        return mapIndex.get(sections[i]);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;

        holder = null;

        if(row == null){

            getInflater = ((Activity)context).getLayoutInflater();

            row = getInflater.inflate(R.layout.fastscroll_row_item, parent, false);

            holder = new InfoHolder();

            holder.txtName = (TextView)row.findViewById(R.id.name);
            holder.checkSelect = (CheckBox)row.findViewById(R.id.name_checkbox);

            row.setTag(holder);

        }

        else {

            holder = (InfoHolder)row.getTag();
        }



        holder.txtName.setText(this.ItemsList.get(position));
        holder.checkSelect.setTag(position);
        holder.checkSelect.setChecked(mCheckStates.get(position, false));

        holder.checkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                setChecked(position, isChecked, buttonView);

            }

        });

        return row;
    }

    public void setChecked(int position, boolean isChecked, CompoundButton view){
        // mCheckStates.put(position, isChecked);
        mCheckStates.put((Integer)view.getTag(),isChecked);
        setmCheckStates(mCheckStates);
    }




    static class InfoHolder {
        TextView txtName;
        CheckBox checkSelect;
    }


    public int SelectedCount(){

        int n = 0;

        for(int i = 0; i < this.ItemsList.size(); i++){

            if(getmCheckStates().get(i) == true){
                n++;
            }

        }

        return n;

    }
}
