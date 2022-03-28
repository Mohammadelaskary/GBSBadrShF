package com.example.gbsbadrsf.Quality.paint.AddDefects;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.DefectsListAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnManufacturingAddDefectDetailsButtonClicked;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingDefectsPerQtyAdapter;
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.databinding.FragmentPaintDisplayDefectDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class PaintDisplayDefectDetailsFragment extends Fragment {


    private int sampleQty;

    public PaintDisplayDefectDetailsFragment() {
        // Required empty public constructor
    }


    public static PaintDisplayDefectDetailsFragment newInstance() {
        return new PaintDisplayDefectDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentPaintDisplayDefectDetailsBinding binding;
    List<Defect> foundDefects = new ArrayList<>();
    DefectsListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintDisplayDefectDetailsBinding.inflate(inflater,container,false);
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
            List<DefectsPainting> defectsPaintingList = getArguments().getParcelableArrayList("defectsPaintingList");
            for (DefectsPainting defectsPainting :defectsPaintingList){
                int defectId = defectsPainting.getDefectId();
                String defectName = defectsPainting.getDefectId()+"";
                Defect defect = new Defect();
                defect.setId(defectId);
                defect.setName(defectName);
                if (!foundDefects.contains(defect)) {
                    foundDefects.add(defect);
                    defectedQty = defectsPainting.getQtyDefected();
                }
                LastMoveWeldingBasket basketData = getArguments().getParcelable("basketData");
                fillData(basketData);
            }
            adapter.setDefectList(foundDefects);
            adapter.notifyDataSetChanged();
        }
    }

    private void fillData(LastMoveWeldingBasket basketData) {
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedQty.getEditText().setText(String.valueOf(defectedQty));
        binding.sampleQty.getEditText().setText(String.valueOf(sampleQty));
    }
}
