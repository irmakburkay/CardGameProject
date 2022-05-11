package tr.edu.duzce.mf.bm.cardgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreTableRecyclerAdapter extends RecyclerView.Adapter<ScoreTableRecyclerAdapter.ScoreTableViewHolder> {

    private List<ScoreTable> scoreTableList;

    public ScoreTableRecyclerAdapter(List<ScoreTable> scoreTableList){
        this.scoreTableList = scoreTableList;
    }

    class ScoreTableViewHolder extends RecyclerView.ViewHolder {
        private TextView nicknameTextView, pointTextView;

        ScoreTableViewHolder(View view) {
            super(view);
            nicknameTextView = view.findViewById(R.id.nicknameTextView);
            pointTextView = view.findViewById(R.id.pointTextView);
        }
    }

    @NonNull
    @Override
    public ScoreTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_score_table,parent,false);
        return new ScoreTableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreTableViewHolder holder, int position) {
        ScoreTable scoreTable = scoreTableList.get(position);
        holder.nicknameTextView.setText(scoreTable.getNickname());
        holder.pointTextView.setText(String.valueOf(scoreTable.getScore()));
    }

    @Override
    public int getItemCount() {
        return scoreTableList.size();
    }

    @Override
    public long getItemId(int position) {
        return scoreTableList.get(position).getId();
    }
}
