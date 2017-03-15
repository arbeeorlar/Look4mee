package folaoyewole.look4mee.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by sp_developer on 9/16/16.
 */
public class mySpinnerAdapter extends ArrayAdapter<String> {

    public mySpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        int count = super.getCount();

        return count > 0 ? count-1 : count;
    }
}
