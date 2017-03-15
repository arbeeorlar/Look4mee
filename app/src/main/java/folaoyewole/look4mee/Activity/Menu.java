package folaoyewole.look4mee.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import folaoyewole.look4mee.Fragment.ArtisanViewPosts;
import folaoyewole.look4mee.Fragment.Artisan_myProfileFragment;
import folaoyewole.look4mee.R;

public class Menu extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Artisan_myProfileFragment fragment = new Artisan_myProfileFragment();
        FragmentManager fmanager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fmanager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.myprofile_string);


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.myprofile:
                        Artisan_myProfileFragment fragment = new Artisan_myProfileFragment();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.myprofile_string);
                        return true;


                    case R.id.feed:
                        ArtisanViewPosts artisanViewPosts = new ArtisanViewPosts();
                        fragmentTransaction.replace(R.id.frame,artisanViewPosts);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(R.string.feed_string);
                        return true;

                    case R.id.log_out:

                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }



//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        moveTaskToBack(true);
//    }

    //    @Override
//    public boolean onCreateOptionsMenu(android.view.Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_artisan, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.option_menu_about) {
//            return true;
//        }
//
//        else if(id == R.id.option_menu_editprofile){
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
