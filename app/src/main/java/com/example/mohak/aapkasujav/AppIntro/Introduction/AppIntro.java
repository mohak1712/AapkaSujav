package com.example.mohak.aapkasujav.AppIntro.Introduction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mohak.aapkasujav.AppIntro.Login;


public class AppIntro extends com.github.paolorotolo.appintro.AppIntro {


    public void init(Bundle savedInstanceState) {

        SharedPreferences pref = getSharedPreferences("KEY", MODE_PRIVATE);
        if (pref.getBoolean("KEY", true)) {


            // Add your slide's fragments here
            // AppIntro will automatically generate the dots indicator and buttons.
            addSlide(Intro1.newInstance("", ""));
            addSlide(Intro2.newInstance("", ""));
            addSlide(Intro3.newInstance("", ""));

//
//        // Instead of fragments, you can also use our default slide
//        // Just set a title, description, background and image. AppIntro will do the rest
//        addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));
//
//        // OPTIONAL METHODS
//        // Override bar/separator color
//        setBarColor(Color.parseColor("#3F51B5"));
//        setSeparatorColor(Color.parseColor("#2196F3"));
//
//        // Hide Skip/Done button
            showSkipButton(true);
            showDoneButton(true);
//
//        // Turn vibration on and set intensity
//        // NOTE: you will probably need to ask VIBRATE permesssion in Manifest
//        setVibrate(true);
//        setVibrateIntensity(30);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("KEY", false);
            editor.apply();
        }else
        {
            myIntent();
        }
    }


    @Override
    public void onSkipPressed() {
        myIntent();
    }

    private void myIntent() {
        Intent intent = new Intent(AppIntro.this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed() {
        myIntent();
    }


}
