package com.triaxyd.unipi.modernswe1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {

    public SQLiteConnector connector;


    private void showStatusMessage(Status status, String name) {
        switch (status) {
            case SUCCESS:
                Toast.makeText(this,  name + " added successfully!", Toast.LENGTH_SHORT).show();
                break;
            case ALREADY_EXISTS:
                Toast.makeText(this, name + " loaded in Database.", Toast.LENGTH_SHORT).show();
                break;
            case FAILURE:
                Toast.makeText(this, "Failed to add " + name + ".", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // load database
        connector = new SQLiteConnector(MainActivity.this);

        // get paintings descriptions
        String monaLisaDescription = getString(R.string.monalisa_description_description);
        String impressionSunriseDescription = getString(R.string.impression_sunrise_description);
        String libertyLeadingThePeopleDescription = getString(R.string.liberty_leading_the_people_description);
        String theSwingDescription = getString(R.string.the_swing_description);

        // painting names and their descriptions
        String[][] paintings = {
                {"mona_lisa", monaLisaDescription},
                {"impression_sunrise", impressionSunriseDescription},
                {"liberty_leading_the_people", libertyLeadingThePeopleDescription},
                {"the_swing", theSwingDescription}
        };

        // save each painting to the database and display the status
        for (String[] painting : paintings) {
            Status status = connector.addPainting(painting[0], painting[1]);
            showStatusMessage(status, painting[0]);
        }


    }


    //go to paintings activity
    public void goToPaintings(View view){
        startActivity(new Intent(this, MonaLisaActivity.class));
    }




}