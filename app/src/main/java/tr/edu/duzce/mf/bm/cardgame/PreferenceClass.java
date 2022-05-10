package tr.edu.duzce.mf.bm.cardgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class PreferenceClass  {

    private Context context;
    private SharedPreferences preferences;

    private boolean musicState, effectState;
    private Integer musicValue, effectValue;
    public MediaPlayer musicPlayer, effectPlayer;
    public String cardTheme, theme, language;

    public PreferenceClass(Context context) {
        this.context = context;

        InitComponents();
        NotifyChanges();
    }

    public void InitComponents() {
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        effectPlayer = MediaPlayer.create(context, R.raw.ding);
        musicPlayer = MediaPlayer.create(context, R.raw.gametrack);
    }

    public void NotifyChanges() {
        musicState = (boolean) preferences.getBoolean("pref_music_state", true);
        musicValue = (Integer) preferences.getInt("pref_music_value", 50);
        effectState = (boolean) preferences.getBoolean("pref_fx_state", true);
        effectValue = (Integer) preferences.getInt("pref_fx_value", 50);
        cardTheme = (String) preferences.getString("pref_card_theme", "1");
        theme = (String) preferences.getString("pref_theme", "1");

        setTheme(theme);
        setCardTheme(cardTheme);
        setMusic(musicState, musicValue);
        setEffect(effectState, effectValue);

        //diğer olayları kontrol ettir
    }

    public void setMusic(boolean state, Integer value) {
        //sesi kontrol et
        musicState = state;
        musicValue = value;
        float formattedValue = (float)Math.pow(1.0965,musicValue-50)/100;
        if(state){
            musicPlayer.setLooping(true);
            musicPlayer.setVolume(formattedValue, formattedValue);
            musicPlayer.start();
        }
        else
            musicPlayer.pause();
    }

    public void setEffect(boolean state, Integer value) {
        effectState = state;
        effectValue = value;
        effectPlayer.setVolume((float) effectValue / 100, (float) effectValue / 100);
        if(state)
            effectPlayer.start();
    }

    public void setCardTheme(String value){
        switch (value){
            case "1":
                MainActivity.frontDrawables = MainActivity.pcDrawable;
                MainActivity.backDrawable = ResourcesCompat.getDrawable(MainActivity.resources, R.drawable.pc, null);
                break;
            case "2":
                MainActivity.frontDrawables = MainActivity.schoolDrawable;
                MainActivity.backDrawable = ResourcesCompat.getDrawable(MainActivity.resources, R.drawable.okul2, null);
                break;
            case "3":
                MainActivity.frontDrawables = MainActivity.toolsDrawable;
                MainActivity.backDrawable = ResourcesCompat.getDrawable(MainActivity.resources, R.drawable.aracgerec, null);
                break;
        }
    }

    public void setTheme(String value){
        switch (value){
            case "1":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "2":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }

    public void setLanguage(String value){
        value = value.equals("1")?"en":"tr";
        Locale locale = new Locale(value);
        Resources res = MainActivity.resources;
        DisplayMetrics display = res.getDisplayMetrics();
        Configuration configuration = res.getConfiguration();
        configuration.setLocale(locale);
        res.updateConfiguration(configuration, display);
        Snackbar snackbar = Snackbar.make(SettingsPopActivity.popView, R.string.snackbarText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}