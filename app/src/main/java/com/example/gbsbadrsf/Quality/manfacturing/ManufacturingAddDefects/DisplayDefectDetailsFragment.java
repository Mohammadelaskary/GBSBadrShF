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
        adapter = new DefectsListAdapter(getContext(), new SetOnManufacturingAddDefectDetailsButtonClicked() {
            @Override
            public void onManufacturingAddDefectDetailsButtonClicked(List<Integer> defectsIds) {

            }
        });
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
                    defectedQty = defectsManufacturing.getQtyDefected();
                }
                LastMoveManufacturingBasket basketData = getArguments().getParcelable("basketData");
            fillData(basketData);
            }
            adapter.setDefectList(foundDefects);
            adapter.notifyDataSetChanged();
        }
    }

    private void fillData(LastMoveManufacturingBasket basketData) {
        String childDesc = basketData.getChildDescription();
        String operationName = basketData.getOperationEnName();
        String jobOrderName  = basketData.getJobOrderName();
        int    jonOrderQty   = basketData.getJobOrderQty();
        binding.childesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.defectedQty.getEditText().setText(String.valueOf(defectedQty));
        binding.sampleQty.getEditText().setText(String.valueOf(sampleQty));
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jonOrderQty));
    }
}