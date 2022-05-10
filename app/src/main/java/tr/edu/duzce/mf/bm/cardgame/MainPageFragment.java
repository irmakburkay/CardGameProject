package tr.edu.duzce.mf.bm.cardgame;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class MainPageFragment extends Fragment implements View.OnClickListener{

    private View view;

    private ArrayList<Cards> cards;
    private ArrayList<ImageView> images;
    private Cards first, second;
    private boolean isLevel;
    private GridLayout mainPageLayout;

    private FragmentManager fm;
    private Drawable play, easy, medium, hard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_page, container, false);

        InitComponents();

        createCard(mainPageLayout.getRowCount() * mainPageLayout.getColumnCount());

        return view;
    }

    private void InitComponents() {
        cards = new ArrayList<>();
        images = new ArrayList<>();
        first = new Cards.CardsBuilder().ID(-1).build();
        second = new Cards.CardsBuilder().ID(-1).build();
        fm = getParentFragmentManager();
        isLevel = false;
        mainPageLayout = view.findViewById(R.id.layout_mainPage);
        mainPageLayout.setRowCount(3);
        mainPageLayout.setColumnCount(3);
        setDrawables();
    } // eof InitComponents


    public void createCard(int number) {
        for (int i = 0; i < number; i++) {

            int margin = (int)getResources().getDimension(R.dimen.standard);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            params.width = (int)getResources().getDimension(R.dimen.mainCardW);
            params.height = (int)getResources().getDimension(R.dimen.mainCardH);
            params.setMargins(margin, margin, margin, margin);

            images.add(i, new ImageView(getContext()));
            images.get(i).setId(new Random().nextInt());
            images.get(i).setSoundEffectsEnabled(false);
            images.get(i).setOnClickListener(this);

            cards.add(i, new Cards.CardsBuilder()
                    .ID(images.get(i).getId())
                    .name(R.string.playButton)
                    .image(images.get(i))
                    .open(play)
                    .close(MainActivity.backDrawable)
                    .build());

            cards.get(i).animateClose(MainActivity.animationDelay.NO_DELAY);

            mainPageLayout.addView(cards.get(i).getImage(), params);
        }
    } // eof createCard


    @Override
    public void onClick(View v) {
        if (first.getID() == -1) {
            first = cards.get(images.indexOf(v));
            first.animateOpen();
        }
        else if (second.getID() == -1) {
            second = cards.get(images.indexOf(v));
            if (first.getImage() == second.getImage()) {
                if (isLevel)
                    startGame(first.getName());
                else
                    selectLevel();
                first =  new Cards.CardsBuilder().ID(-1).build();
            }
            else {
                second.animateOpen();
                first.animateClose(MainActivity.animationDelay.NO_DELAY);
                first = second;
            }
            second = new Cards.CardsBuilder().ID(-1).build();
        }
    } // eof onClick

    public void selectLevel () {
        isLevel = true;
        for (int i = 0; i < 9; i++){
            cards.get(i).animateClose(MainActivity.animationDelay.NO_DELAY);
            if(i<3){
                cards.get(i).setName(R.string.easy);
                cards.get(i).setOpen(easy);
            }
            else if(i<6){
                cards.get(i).setName(R.string.medium);
                cards.get(i).setOpen(medium);
            }
            else{
                cards.get(i).setName(R.string.hard);
                cards.get(i).setOpen(hard);
            }
        }
    } // eof selectLevel

    public void startGame(int level) {
        for (Cards card: cards)
            card.animateClose(MainActivity.animationDelay.NO_DELAY);
        MainActivity.callFragment(getParentFragmentManager(), new GameFragment(level));
    } // eof startGame

    public void setDrawables(){
        play = ResourcesCompat.getDrawable(MainActivity.resources, R.drawable.play, null);
        easy = ResourcesCompat.getDrawable(MainActivity.resources, R.drawable.easy, null);
        medium = ResourcesCompat.getDrawable(MainActivity.resources, R.drawable.medium, null);
        hard = ResourcesCompat.getDrawable(MainActivity.resources, R.drawable.hard, null);
    }

}