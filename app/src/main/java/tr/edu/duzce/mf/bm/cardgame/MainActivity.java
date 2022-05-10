package tr.edu.duzce.mf.bm.cardgame;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public enum animationDelay {
        NO_DELAY,
        DELAY
    }

    public static PreferenceClass preferenceClass;

    public static String fragment;
    public static TextView text;
    public static Context context;
    public static Resources resources;
    public static ArrayList<Drawable> pcDrawable, schoolDrawable, toolsDrawable, frontDrawables;
    public static Drawable backDrawable;

    public static MaterialButton backButton, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_CardGame);
        setContentView(R.layout.activity_main);

        InitComponents();
        RegisterEventHandlers();

        callFragment(getSupportFragmentManager(), new MainPageFragment());

    } // eof onCreate

    @Override
    protected void onResume() {
        super.onResume();
        preferenceClass.NotifyChanges();
    }

    @Override
    protected void onStop() {
        super.onStop();
        preferenceClass.setMusic(false, 0);
        preferenceClass.setEffect(false, 0);
    }

    /**
     * Description :
     * This method creates and binds resources and values to objects.
     */
    public void InitComponents() {
        context = this;
        resources = context.getResources();
        preferenceClass = new PreferenceClass(this);
        text = findViewById(R.id.text_actionBar);
        backButton = findViewById(R.id.backButton);
        settingsButton = findViewById(R.id.settingsButton);
        pcDrawable = new ArrayList<>();
        schoolDrawable = new ArrayList<>();
        toolsDrawable = new ArrayList<>();
        loadAllDrawables();
    } // eof InitComponents

    /**
     * Description :
     * This method sets click action of the buttons.
     */
    public void RegisterEventHandlers() {
        backButton.setOnClickListener(this::onBackClick);
        settingsButton.setOnClickListener(this::onSettingsClick);
    } // eof RegisterEventHandlers

    public void loadAllDrawables(){
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.android));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.bluetooth));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.camera));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.klavye));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.laptop));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.mouse));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.sdcard));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.telefon));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.usb));
        pcDrawable.add(AppCompatResources.getDrawable(this, R.drawable.yildirim));

        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.zil));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.takvim));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.okul));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.kitap));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.firca));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.canta));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.calculator));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.buyutec));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.kalem));
        schoolDrawable.add(AppCompatResources.getDrawable(this, R.drawable.bilim));

        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.anahtar));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.askilik));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.attach));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.capa));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.fener));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.kablo));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.pergel));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.pusula));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.termometre));
        toolsDrawable.add(AppCompatResources.getDrawable(this, R.drawable.testere));
    }

    /**
     * Description :
     * This method calls {@code fragment} parameter into {@code layout_frameLayout} and sets onBackAction as {@code onBackAction.BACK} or {@code onBackAction.FINISH}.
     *
     * @param fragmentManager This is the FragmentManager object that will do the replacement.
     * @param fragment This is the new fragment you want to pass.
     */
    public static void callFragment(FragmentManager fragmentManager, Fragment fragment) {
        changeFragmentCondition(fragment.getClass().getSimpleName());
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.layout_frameLayout, fragment, fragment.getClass().getSimpleName())
                .commit();
    } // eof callFragment


    /**
     * Description :
     * This method changes {@code MainActivity.fragment}'s value.
     * Values to be entered:
     * <ul>
     *  <li><i>MainPageFragment</i></li>
     *  <li><i>GameFragment</i></li>
     *  <li><i>SettingsFragment</i></li>
     * </ul>
     *
     * @param fragment
     */
    public static void changeFragmentCondition(String fragment) {
        MainActivity.fragment = fragment;
    } // eof changeFragmentCondition

    /**
     * Description :
     * This is the function of the back button.
     * <p>If {@code MainActivity.fragment} is <b>MainPageFragment</b>, app will be closed.</p>
     * <p>If {@code MainActivity.fragment} is <b>GameFragment</b>, app will show new {@code MainPageFragment}.</p>
     * <p>If {@code MainActivity.fragment} is <b>Settings Fragment</b>, app will show new {@code MainPageFragment}.</p>
     * <p>For default, app will show new {@code MainPageFragment}.</p>
     *
     * @param v View object. In this case MaterialButton.
     */
    public void onBackClick(View v) {
        onKeyDown(KeyEvent.KEYCODE_BACK, null);
    } // eof onBackClick

    /**
     * Description :
     * This is the function of the settings button.
     * It'll call a new {@code SettingsFragment}.
     *
     * @param v View object. In this case MaterialButton.
     */
    public void onSettingsClick(View v) {
        settingsButton.setClickable(false);
        Intent intent = new Intent(this, SettingsPopActivity.class);
        startActivity(intent);
    } // eof onSettingsClick

    public static void clearScore() {
        text.setText(null);
    }

    public static void setScore(long time, int point) {
        text.setText(
                resources.getString(R.string.time)
                        + " : " + time/1000 + "\n"
                        + resources.getString(R.string.score)
                        + " : " + point);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Fragment f = getSupportFragmentManager().findFragmentByTag(fragment);
            if(f.getClass() == MainPageFragment.class)
                finish();
            else if (f.getClass() == GameFragment.class)
                callFragment(getSupportFragmentManager(), new MainPageFragment());
            else if (f.getClass() == ScoreFragment.class)
                callFragment(getSupportFragmentManager(), new MainPageFragment());
            else
                return super.onKeyDown(keyCode,event);
        }
        return super.onKeyDown(keyCode,event);
    }

} // eof MainActivity