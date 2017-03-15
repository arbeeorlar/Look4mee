package folaoyewole.look4mee.App;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import folaoyewole.look4mee.Activity.ArtisanReg_Activity;
import folaoyewole.look4mee.Activity.EmployerReg_Activity;
import folaoyewole.look4mee.Activity.LoginActivity;
import folaoyewole.look4mee.R;

/**
 * Created by sp_developer on 9/11/16.
 */
public class MainActivity extends AppCompatActivity {

    private Button LoginBtn;
    private TextView mArtisan, mEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginBtn = (Button)findViewById(R.id.login_main);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);


            }
        });

        mArtisan = (TextView)findViewById(R.id.artisan_btn);
        mArtisan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ArtisanReg_Activity.class);
                //new ArtisanReg_Activity(MainActivity.this);
                startActivity(i);


            }
        });


        mEmployer = (TextView)findViewById(R.id.employer_btn);
        mEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, EmployerReg_Activity.class);
               // new EmployerReg_Activity(MainActivity.this);

                startActivity(i);

            }
        });
    }

}
