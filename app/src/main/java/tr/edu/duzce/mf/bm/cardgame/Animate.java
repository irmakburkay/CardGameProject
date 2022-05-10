package tr.edu.duzce.mf.bm.cardgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.util.Log;

public class Animate extends AsyncTask<Cards, Void, Cards> {

    @Override
    protected Cards doInBackground(Cards... cards) {
        cards[0].getImage().setClickable(false);
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "doInBackground: " + e.getLocalizedMessage());
        }
        return cards[0];
    }

    @Override
    protected void onPostExecute(Cards cards) {
        cards.getImage().setRotationY(0);
        cards.getImage().animate().rotationY(90).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cards.getImage().setBackground(cards.getClose());
                cards.getImage().setRotationY(270);
                cards.getImage().animate().rotationY(360).setListener(null);
            }
        });
        cards.getImage().setClickable(true);
        super.onPostExecute(cards);
    }
}