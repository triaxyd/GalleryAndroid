package com.triaxyd.unipi.modernswe1;

import static android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextSpeechConverter {
    private final TextToSpeech textToSpeech;

    public TextSpeechConverter(Context context){
        TextToSpeech.OnInitListener initListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        };
        textToSpeech = new TextToSpeech(context, initListener);
    }
    public void speak(String message){
        // for the volume
        Bundle b = new Bundle();
        b.putFloat(KEY_PARAM_VOLUME, 1.0f);
        // call text to speech method
        textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, b, "1");
    }


}
