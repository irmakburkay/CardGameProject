package tr.edu.duzce.mf.bm.cardgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;


public class Cards {

    private int ID;
    private int name;
    private ImageView image;

    private Drawable open, close;

    public Cards() {}

    public Cards(CardsBuilder cardsBuilder) {
        this.name = cardsBuilder.name;
        this.ID = cardsBuilder.ID;
        this.image = cardsBuilder.image;
        this.open = cardsBuilder.open;
        this.close = cardsBuilder.close;
    }

    public int getID() { return ID; }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getName() { return name; }

    public void setName(int name) { this.name = name; }

    public void animateOpen() {
        animate(open);
    }

    public void animateClose(MainActivity.animationDelay delay) {
        if(delay.equals(MainActivity.animationDelay.DELAY))
           new Animate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
        else
            animate(close);
    }

    public void animate(Drawable drawable) {
        image.setRotationY(0);
        image.animate().rotationY(90).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                image.setBackground(drawable);
                image.setRotationY(270);
                image.animate().rotationY(360).setListener(null);
            }
        });
    }

    public Drawable getOpen() {
        return open;
    }

    public void setOpen(Drawable open) {
        this.open = open;
    }

    public Drawable getClose() {
        return close;
    }

    public void setClose(Drawable close) {
        this.close = close;
    }

    public static class CardsBuilder {

        private int ID;
        private int name;
        private ImageView image;
        private Drawable open, close;

        public CardsBuilder() {}

        public CardsBuilder ID(int ID) {
            this.ID = ID;
            return this;
        }

        public CardsBuilder name(int name) {
            this.name = name;
            return this;
        }

        public CardsBuilder image(ImageView image) {
            this.image = image;
            return this;
        }

        public CardsBuilder open(Drawable open) {
            this.open = open;
            return this;
        }

        public CardsBuilder close(Drawable close) {
            this.close = close;
            return this;
        }

        public Cards build() {
            //image.setBackgroundColor(R.color.light_theme);
            return new Cards(this);
        }

    }


}
