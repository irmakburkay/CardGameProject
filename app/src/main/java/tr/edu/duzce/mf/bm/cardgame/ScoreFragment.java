package tr.edu.duzce.mf.bm.cardgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Random;


public class ScoreFragment extends Fragment {

    private View view;
    private AppDatabase appDatabase;
    private IScoreDAO scoreDAO;

    private TextInputLayout nicknameLayout;
    private TextInputEditText nicknameWrapper, scoreWrapper;
    private Button saveButton;
    private int score;

    public ScoreFragment(int score){this.score = score;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_score, container, false);

        InitComponents();
        RegisterEventHandlers();

        return view;
    }

    public void InitComponents() {
        appDatabase = AppDatabase.getAppDatabase(getContext());
        scoreDAO = appDatabase.getScoreDAO();
        nicknameWrapper = view.findViewById(R.id.nicknameWrapper);
        scoreWrapper = view.findViewById(R.id.scoreWrapper);
        saveButton = view.findViewById(R.id.saveButton);
        nicknameLayout = view.findViewById(R.id.nicknameLayout);
        scoreWrapper.setText(String.valueOf(score));
    }

    public void RegisterEventHandlers() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nicknameWrapper.getText().toString().length() < 1 || nicknameWrapper.getText().toString().length() > 15)
                    nicknameLayout.setError(MainActivity.resources.getString(R.string.invalidAlias));
                else{
                    nicknameLayout.setError(null);
                    ScoreTable scoreTable = new ScoreTable();
                    scoreTable.setScore(Integer.parseInt(scoreWrapper.getText().toString()));
                    scoreTable.setNickname(nicknameWrapper.getText().toString());
                    scoreDAO.insertScoreTable(scoreTable);
                    MainActivity.callFragment(getParentFragmentManager(), new MainPageFragment());
                }
            }
        });
    }


}