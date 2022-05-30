package com.example.gbsbadrsf.welding.qualityscrapwe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.FragmentQualityscraplistBinding;
import com.example.gbsbadrsf.databinding.FragmentQualityscraplistweBinding;


public class qualityscraplistweFragment extends Fragment implements OnClick {

    FragmentQualityscraplistweBinding fragmentQualityscraplistweBinding;
    qualityscrapwelistAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        fragmentQualityscraplistweBinding = FragmentQualityscraplistweBinding.inflate(inflater, container, false);
        initViews();

        return fragmentQualityscraplistweBinding.getRoot();

    }
    private void initViews() {

        adapter = new qualityscrapwelistAdapter(this);
        fragmentQualityscraplistweBinding.qualityscrapRv.setAdapter(adapter);
        fragmentQualityscraplistweBinding.qualityscrapRv.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    @Override
    public void OnItemClickedListener(int position) {
        Navigation.findNavController(getView()).navigate(R.id.action_qualityscraplistweFragment_to_qualityscraprequestweFragment);

    }
}