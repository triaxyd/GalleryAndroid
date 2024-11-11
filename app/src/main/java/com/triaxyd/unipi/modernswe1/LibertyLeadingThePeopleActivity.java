package com.triaxyd.unipi.modernswe1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LibertyLeadingThePeopleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.painting_prototype);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView paintingImage = findViewById(R.id.paintingImageView);
        EditText paintingDescription = findViewById(R.id.descriptionMultilineText);

        SQLiteConnector connector = new SQLiteConnector(this);
        Painting painting = connector.retrievePainting("liberty_leading_the_people");

        if (painting != null) {
            // set the image - retrieve the drawable ID dynamically with getIdentifier name based on the imgReference
            int imageResId = getResources().getIdentifier(painting.getImg_reference(), "drawable", getPackageName());
            if (imageResId != 0) {
                // found the resource id integer, set image
                paintingImage.setImageResource(imageResId);
            }
            // set the description
            paintingDescription.setText(painting.getImg_description());
        } else {
            // image details not found
            Toast.makeText(this, "Liberty Leading The People not found in the database.", Toast.LENGTH_SHORT).show();
        }

        ImageButton hearingAccessibility = findViewById(R.id.hearingAccessibilityButton);
        TextSpeechConverter converter = new TextSpeechConverter(this);
        hearingAccessibility.setOnClickListener( l -> {
            if (paintingDescription != null) {
                converter.speak("Liberty Leading the People");
                converter.speak(paintingDescription.getText().toString());
            }

        });


        ImageButton nextPaintingButton = findViewById(R.id.nextPaintingButton);
        //go to Choice Activity
        nextPaintingButton.setOnClickListener(v ->
                startActivity(new Intent(this, PersonalChoiceActivity.class))
        );

    }
}