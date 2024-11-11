package com.triaxyd.unipi.modernswe1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonalChoiceActivity extends AppCompatActivity {

    EditText numberChoiceText;
    Button choiceButton;
    SharedPreferences sharedPreferencesChoice;

    TextView showPreferenceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.personal_choice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        numberChoiceText = findViewById(R.id.editTextNumber);
        numberChoiceText.setHint("number from 1-4");
        choiceButton = findViewById(R.id.choiceButton);
        showPreferenceTextView = findViewById(R.id.showPreferenceTextView);

        sharedPreferencesChoice = this.getPreferences(MODE_PRIVATE);

        // save number on button click
        choiceButton.setOnClickListener(v -> {
            String userChoice = String.valueOf(numberChoiceText.getText());
            if (!userChoice.isEmpty() && (Integer.parseInt(userChoice) > 0 && Integer.parseInt(userChoice) < 5) ){
                setChoicePreference(userChoice);
                Toast.makeText(this, "Saved Painting Number " + userChoice, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Enter valid number!", Toast.LENGTH_SHORT).show();
            }

        });
    }


    // save user preference in SharedPreferences
    private void setChoicePreference(String userChoice){
        SharedPreferences.Editor editor = sharedPreferencesChoice.edit();
        editor.putString("userChoice", userChoice);
        editor.apply();
    }

    // display the user preference from SharedPreferences
    public void displayPreference(View view){
        try {
            String userChoice = sharedPreferencesChoice.getString("userChoice", "No saved Painting");
            if (!userChoice.isEmpty()){
                showPreferenceTextView.setText(userChoice);
            }
        }catch (Exception ignored){
            showPreferenceTextView.setText(R.string.no_saved_number);
        }


    }

}