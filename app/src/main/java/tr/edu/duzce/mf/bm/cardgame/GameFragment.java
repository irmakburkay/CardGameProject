package tr.edu.duzce.mf.bm.cardgame;


import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;


public class GameFragment extends Fragment implements  View.OnClickListener{

    //--------------------Fragment Config---------------------
    private View view;
    private GridLayout gameLayout;
    private int row, column;
    private ArrayList<Cards> cards;
    private ArrayList<ImageView> images;
    private ArrayList<Integer> IDs;
    //--------------------------------------------------------

    //--------------------Game Config-------------------------
    private int level;
    private int point;
    private int isSolved;
    private long timeLeft;
    private Cards first, second;
    private CountDownTimer timer;
    private ArrayList<Drawable> drawables;
    private Drawable drawable;
    //--------------------------------------------------------

    public GameFragment(int level) {
        this.level = level;
        point = 0;
        column = 4;
        if (this.level == R.string.easy)
            row = 3;
        else if(this.level == R.string.medium)
            row = 4;
        else
            row = 5;
        timeLeft = row * 30000 + 1500;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        InitComponents();

        createCard(row * column);

        return view;
    }

    @Override
    public void onResume() {
        startTimer(timeLeft);
        super.onResume();
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        MainActivity.clearScore();
        super.onDestroy();
    }

    public void InitComponents() {
        isSolved = row * 2;
        gameLayout = view.findViewById(R.id.layout_game);
        gameLayout.setRowCount(row);
        gameLayout.setColumnCount(column);
        cards = new ArrayList<>();
        images = new ArrayList<>();
        IDs = new ArrayList<>();
        drawables = new ArrayList<>();
        drawable = MainActivity.backDrawable;
        first = new Cards.CardsBuilder().ID(-1).build();
        second = new Cards.CardsBuilder().ID(-1).build();
        for (int i = 0; i < row * column / 2; i++){
            IDs.add(i);
            IDs.add(i);
            drawables.add(MainActivity.frontDrawables.get(i));
            drawables.add(MainActivity.frontDrawables.get(i));
        }
        shuffle(IDs, drawables);
    }

    public void createCard(int number) {

        for (int i = 0; i < number; i++) {

            int margin = (int)getResources().getDimension(R.dimen.standard);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            params.width = (int)getResources().getDimension(R.dimen.playCardW);
            params.height = (int)getResources().getDimension(R.dimen.playCardH);
            params.setMargins(margin, margin, margin, margin);

            images.add(i, new ImageView(getContext()));
            images.get(i).setId(IDs.get(i));
            images.get(i).setSoundEffectsEnabled(false);
            images.get(i).setOnClickListener(this);

            cards.add(i, new Cards.CardsBuilder()
                    .ID(images.get(i).getId())
                    .image(images.get(i))
                    .open(drawables.get(i))
                    .close(drawable)
                    .build());

            gameLayout.addView(cards.get(i).getImage(), params);

            cards.get(i).animateOpen();

            cards.get(i).animateClose(MainActivity.animationDelay.DELAY);
        }

    }

    public void match() {
        MainActivity.preferenceClass.effectPlayer.start();
        switch (gameLayout.getRowCount()){
            case 3:
                point += 50;
                break;
            case 4:
                point += 100;
                break;
            default:
                point += 150;
                break;
        }
        if(--isSolved == 0){
            gameLayout.removeAllViews();
            InitComponents();
            createCard(row * column);
        }

    }

    public <T1,T2> void shuffle(ArrayList<T1> a, ArrayList<T2> b)
    {
        Random r = new Random();
        for(int i=a.size()-1;i>=0;i--)
        {
            int j = r.nextInt(i+1);
            T1 temp1 = a.get(i);
            a.set(i, a.get(j));
            a.set(j, temp1);
            T2 temp2 = b.get(i);
            b.set(i, b.get(j));
            b.set(j, temp2);
        }
    } // eof shuffle

    public void startTimer(long time){
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                MainActivity.setScore(timeLeft, point);
            }

            @Override
            public void onFinish() {
                cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(MainActivity.resources.getString(R.string.timesUp));
                builder.setMessage(MainActivity.resources.getString(R.string.totalScore) + " : " + point);
                builder.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_timer_24, null));
                builder.setPositiveButton(
                        MainActivity.resources.getString(R.string.save),
                        (dialog, which) -> {MainActivity.callFragment(getParentFragmentManager(), new ScoreFragment(point));});
                builder.setNegativeButton(
                        MainActivity.resources.getString(R.string.playAgain),
                        (dialog, which) -> {MainActivity.callFragment(getParentFragmentManager(), new GameFragment(level));});
                builder.setNeutralButton(
                        MainActivity.resources.getString(R.string.exit),
                        (dialog, which) -> {MainActivity.callFragment(getParentFragmentManager(), new MainPageFragment());});
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        if (first.getID() == -1) {
            first = cards.get(images.indexOf(v));
            first.animateOpen();
        }
        else if (second.getID() == -1) {
            second = cards.get(images.indexOf(v));
            if (first.getImage() == second.getImage())
                second.animateClose(MainActivity.animationDelay.NO_DELAY);
            else if (first.getID() == second.getID()) {
                second.animateOpen();
                first.getImage().setEnabled(false);
                second.getImage().setEnabled(false);
                match();
            }
            else {
                second.animateOpen();
                first.animateClose(MainActivity.animationDelay.DELAY);
                second.animateClose(MainActivity.animationDelay.DELAY);
            }
            first =  new Cards.CardsBuilder().ID(-1).build();
            second =  new Cards.CardsBuilder().ID(-1).build();
        }

    }

}