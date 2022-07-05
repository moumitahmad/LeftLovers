package com.example.leftlovers.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leftlovers.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchbarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchbarFragment extends Fragment {


    public SearchbarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searchbar, container, false);
    }
}