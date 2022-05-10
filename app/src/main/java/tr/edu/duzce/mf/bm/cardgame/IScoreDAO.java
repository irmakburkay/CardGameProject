package tr.edu.duzce.mf.bm.cardgame;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IScoreDAO {

    @Insert
    void insertScoreTable(ScoreTable scoreTable);

    @Update
    void updateScoreTable(ScoreTable scoreTable);

    @Delete
    void deleteScoreTable(ScoreTable scoreTable);

    @Query("SELECT * FROM Scores ORDER BY score DESC")
    List<ScoreTable> loadAllScoreTables();

    @Query("DELETE FROM Scores")
    void deleteAllScoreTables();

}
