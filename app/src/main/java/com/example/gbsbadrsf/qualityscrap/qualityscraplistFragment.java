package com.example.gbsbadrsf.qualityscrap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.FragmentQualityscraplistBinding;

public class qualityscraplistFragment extends Fragment implements OnClick {
    FragmentQualityscraplistBinding fragmentQualityscraplistBinding;
    qualityscraplistadapter adapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentQualityscraplistBinding = FragmentQualityscraplistBinding.inflate(inflater, container, false);
        initViews();

        return fragmentQualityscraplistBinding.getRoot();

    }
    private void initViews() {

        adapter = new qualityscraplistadapter(this);
        fragmentQualityscraplistBinding.qualityscrapRv.setAdapter(adapter);
        fragmentQualityscraplistBinding.qualityscrapRv.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    @Override
    public void OnItemClickedListener(int position) {
        Navigation.findNavController(getView()).navigate(R.id.action_qualityscraplistFragment_to_qualityScrapFragment);

    }
}