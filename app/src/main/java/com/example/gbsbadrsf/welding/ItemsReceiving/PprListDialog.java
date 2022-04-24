package com.example.gbsbadrsf.welding.ItemsReceiving;

import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.JOB_ORDER;
import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.PPR;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.PprListDialogLayoutBinding;

import java.util.List;

public class PprListDialog extends DialogFragment {
    private JobOrder jobOrder;
    private List<Ppr> pprList;



    public void setJobOrder(JobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }

    public void setPprList(List<Ppr> pprList) {
        this.pprList = pprList;
    }

    private PprListDialogLayoutBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PprListDialogLayoutBinding.inflate(getLayoutInflater());
        setUpRecyclerView();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PprListDialogLayoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    private PprAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new PprAdapter(getContext());
        adapter.setPprList(pprList);
        adapter.setJobOrder(jobOrder);
        binding.list.setAdapter(adapter);
        adapter.setOnPprItemClicked((ppr, jobOrder) -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(PPR,ppr);
            bundle.putParcelable(JOB_ORDER,jobOrder);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.ppr_list_dialog_to_child_to_basket_fragment,bundle);
        });
    }
}
