package com.example.golo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Player.ScoringList;
import com.example.golo.GetDataService;
import com.example.golo.R;
import com.example.golo.RecyclerViewScorersAdapter;
import com.example.golo.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentScorers extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewScorersAdapter recyclerViewScorersAdapter;

    public FragmentScorers(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceBundle){
        View view = inflater.inflate(R.layout.scorers_fragment, container, false);
        recyclerView = view.findViewById(R.id.scorersRecyclerView);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);

        GetDataService apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        apiService.getScorers(getArguments().getString("compId")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ScoringList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ScoringList scoringList) {
                recyclerViewScorersAdapter = new RecyclerViewScorersAdapter(getContext(), scoringList.getScorers());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recyclerViewScorersAdapter);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(),"ERRO SCORERS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}