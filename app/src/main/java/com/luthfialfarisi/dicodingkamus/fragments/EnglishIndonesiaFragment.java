package com.luthfialfarisi.dicodingkamus.fragments;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.luthfialfarisi.dicodingkamus.R;
import com.luthfialfarisi.dicodingkamus.adapters.KamusAdapter;
import com.luthfialfarisi.dicodingkamus.models.KamusItems;
import com.luthfialfarisi.dicodingkamus.utils.KamusHelper;

import java.util.ArrayList;


public class EnglishIndonesiaFragment extends Fragment {

    private View view;
    private KamusHelper helper;
    private ArrayList<KamusItems> kamusList = new ArrayList<>();
    private KamusAdapter adapter;
    private String language = "EN_ID";

    public EnglishIndonesiaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView sv = view.findViewById(R.id.keyword);
        sv.onActionViewExpanded();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText);
                return false;
            }
        });

        helper = new KamusHelper(getContext());
        adapter = new KamusAdapter(kamusList, getActivity().getApplicationContext());

        RecyclerView rv = view.findViewById(R.id.rvKamus);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        return view;
    }

    private void getData(String keyword) {
        try {
            helper.open();
            if (keyword.isEmpty()) {
                kamusList = helper.getAllData(language);
            } else {
                kamusList = helper.getDataByWord(keyword, language);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }
        adapter.setData(kamusList);
    }

}
