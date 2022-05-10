package tr.edu.duzce.mf.bm.cardgame;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ScoreTable.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;
    public abstract IScoreDAO getScoreDAO();

    private static final String databaseName = "bm443.CardGame";

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase =
                    Room.databaseBuilder(context, AppDatabase.class, databaseName)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public static void destroyInstance() {appDatabase = null;}

}
