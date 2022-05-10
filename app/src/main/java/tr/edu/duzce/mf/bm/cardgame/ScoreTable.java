package tr.edu.duzce.mf.bm.cardgame;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Scores")
public class ScoreTable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "nickname")
    private String nickname;
    @ColumnInfo(name = "score")
    private int score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
