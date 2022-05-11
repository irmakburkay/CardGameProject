package tr.edu.duzce.mf.bm.cardgame;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;


public class SettingsFragment extends PreferenceFragmentCompat {

    private PreferenceClass preferences;

    private SwitchPreference musicState, effectState, clearDatabase, scores;
    private SeekBarPreference musicBar, effectBar;
    private ListPreference cardTheme, theme, language;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName("preferences");
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
        addPreferencesFromResource(R.xml.settings_preferences);

        InitComponents();
        RegisterEventHandlers();

    }

    public void InitComponents() {
        musicBar = findPreference("pref_music_value");
        musicState = findPreference("pref_music_state");
        effectBar = findPreference("pref_fx_value");
        effectState = findPreference("pref_fx_state");
        clearDatabase = findPreference("pref_clear_database");
        cardTheme = findPreference("pref_card_theme");
        theme = findPreference("pref_theme");
        language = findPreference("pref_language");
        scores = findPreference("pref_scores");
        preferences = MainActivity.preferenceClass;
    }

    public void RegisterEventHandlers () {
        musicState.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                preferences.setMusic((boolean) newValue, musicBar.getValue());
                return true;
            }
        });

        musicBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                preferences.setMusic(musicState.isChecked(), (Integer) newValue);
                return true;
            }
        });

        effectState.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                preferences.setEffect((boolean) newValue, effectBar.getValue());
                return true;
            }
        });

        effectBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                preferences.setEffect(effectState.isChecked(), (Integer) newValue);
                return true;
            }
        });

        clearDatabase.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                Toast.makeText(getContext(), MainActivity.resources.getString(R.string.scoresCleared), Toast.LENGTH_SHORT).show();
                new AsyncTask<Void,Void,Void>(){

                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                AppDatabase.getAppDatabase(SettingsFragment.super.getContext()).getScoreDAO().deleteAllScoreTables();
                                Thread.sleep(500);
                            } catch (Exception e) {
                                Log.e(this.getClass().getSimpleName(), "doInBackground: " + e.getLocalizedMessage());
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            clearDatabase.setChecked(false);
                            super.onPostExecute(unused);
                        }
                    }.execute();
                return true;
            }
        });

        cardTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                    MainActivity.preferenceClass.setCardTheme((String) newValue);
                return true;
            }
        });

        theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                MainActivity.preferenceClass.setTheme((String) newValue);
                return true;
            }
        });

        language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                MainActivity.preferenceClass.setLanguage((String) newValue);
                return true;
            }
        });

        scores.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            SettingsPopActivity.callFragment(
                                    getParentFragmentManager(),
                                    new ListFragment(AppDatabase.getAppDatabase(SettingsFragment.super.getContext()).getScoreDAO().loadAllScoreTables()));
                            Thread.sleep(500);
                        } catch (Exception e) {
                            Log.e(this.getClass().getSimpleName(), "doInBackground: " + e.getLocalizedMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void unused) {
                        scores.setChecked(false);
                        super.onPostExecute(unused);
                    }
                }.execute();
                return true;
            }
        });

    }

}