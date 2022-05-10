package tr.edu.duzce.mf.bm.cardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class SettingsPopActivity extends AppCompatActivity {

    private static Fragment fragment;
    public static View popView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pop);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.9));
        WindowManager.LayoutParams params= getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        popView = findViewById(R.id.pop_view);

        callFragment(getSupportFragmentManager(), new SettingsFragment());

    }

    @Override
    public void finish() {
        MainActivity.settingsButton.setClickable(true);
        super.finish();
    }

    public static void changeFragmentCondition(Fragment frag) {
        fragment = frag;
    } // eof changeFragmentCondition

    public static void callFragment(FragmentManager fragmentManager, Fragment fragment) {
        changeFragmentCondition(fragment);
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.popContainer, fragment, fragment.getClass().getSimpleName())
                .commit();
    } // eof callFragment



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(fragment.getClass().equals(SettingsFragment.class))
                finish();
            else if(fragment.getClass().equals(ListFragment.class))
                callFragment(getSupportFragmentManager(), new SettingsFragment());
            else
                finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
