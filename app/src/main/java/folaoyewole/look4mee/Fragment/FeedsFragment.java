package folaoyewole.look4mee.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import folaoyewole.look4mee.R;

public class FeedsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true); // contains options menu in actionbar

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feeds, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear(); // clear whatever menu is existing

        inflater.inflate(R.menu.menu_myfeeds, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.feeds_option_finder) {
//           // Dialog dialog = new Dialog(getActivity(), R.style.DialogAnimation);
//            //Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), R.style.DialogAnimation));
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            View v = getActivity().getLayoutInflater().inflate(R.layout.artisanpost_skills_dialog,null);
//            builder.setView(v);
//            builder.setTitle("MY Action");
//            AlertDialog dialog = builder.create();
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            dialog.getWindow().setGravity(Gravity.BOTTOM);
//            dialog.show();
//
//            return true;
//        }


        return super.onOptionsItemSelected(item);

    }

}
