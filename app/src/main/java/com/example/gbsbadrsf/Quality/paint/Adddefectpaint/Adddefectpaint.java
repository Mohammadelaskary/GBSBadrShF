package com.example.gbsbadrsf.Quality.paint.Adddefectpaint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gbsbadrsf.AdddefectpaintCustomdialog;
import com.example.gbsbadrsf.databinding.FragmentAdddefectpaintBinding;


public class Adddefectpaint extends Fragment {

    FragmentAdddefectpaintBinding fragmentAdddefectpaintBinding;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAdddefectpaintBinding = FragmentAdddefectpaintBinding.inflate(inflater, container, false);
        initViews();

        return fragmentAdddefectpaintBinding.getRoot();

    }

    private void initViews() {
        fragmentAdddefectpaintBinding.plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdddefectpaintCustomdialog mycustomDialog=new AdddefectpaintCustomdialog();
                mycustomDialog.show(getChildFragmentManager(),"adddefectpaintcustomDialog");
            }
        });


    }
}