package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.DefectsListAdapter;
import com.example.gbsbadrsf.databinding.FragmentDisplayDefectDetailsBinding;

import java.util.ArrayList;
import java.util.List;


public class DisplayDefectDetailsFragment extends Fragment {


    private int sampleQty;

    public DisplayDefectDetailsFragment() {
        // Required empty public constructor
    }


    public static DisplayDefectDetailsFragment newInstance() {
        return new DisplayDefectDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentDisplayDefectDetailsBinding binding;
    List<Defect> foundDefects = new ArrayList<>();
    DefectsListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDisplayDefectDetailsBinding.inflate(inflater,container,false);
        setUpRecyclerView();
        getReceivedData();
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        adapter = new DefectsListAdapter(true);
        binding.defectsSelectList.setAdapter(adapter);
    }
    int defectedQty;
    private void getReceivedData() {
        if (getArguments()!=null) {
            sampleQty = getArguments().getInt("sampleQty");
            List<DefectsManufacturing> defectsManufacturingList = getArguments().getParcelableArrayList("defectsManufacturingList");
            for (DefectsManufacturing defectsManufacturing:defectsManufacturingList){
                int defectId = defectsManufacturing.getDefectId();
                String defectName = defectsManufacturing.getDefectDescription();
                Defect defect = new Defect();
                defect.setId(defectId);
                defect.setName(defectName);
                if (!foundDefects.contains(defect)) {
                    foundDefects.add(defect);
                    defectedQty = defectsManufacturing.getDeffectedQty();
                }
                LastMoveManufacturingBasket basketData = getArguments().getParcelable("basketData");
                fillData(basketData);
            }
            adapter.setDefectList(foundDefects);
            adapter.notifyDataSetChanged();
        }
    }

    private void fillData(LastMoveManufacturingBasket basketData) {
        String childCode = basketData.getChildCode();
        String childDesc = basketData.getChildDescription();
        String operationName = basketData.getOperationEnName();
        binding.childcode.setText(childCode);
        binding.childesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.defectedQtnEdt.setText(String.valueOf(defectedQty));
        binding.sampleQtnEdt.setText(String.valueOf(sampleQty));
    }
}