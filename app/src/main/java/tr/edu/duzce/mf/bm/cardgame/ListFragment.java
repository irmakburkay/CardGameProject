package tr.edu.duzce.mf.bm.cardgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private List<ScoreTable> scoreTableList;

    public ListFragment(List<ScoreTable> scoreTableList){
        this.scoreTableList = scoreTableList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        InitComponents();
        loadData(scoreTableList);

        return view;
    }

    public void InitComponents() {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    public  void loadData(List<ScoreTable> scoreTableList){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        ScoreTableRecyclerAdapter adapter = new ScoreTableRecyclerAdapter(scoreTableList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

}