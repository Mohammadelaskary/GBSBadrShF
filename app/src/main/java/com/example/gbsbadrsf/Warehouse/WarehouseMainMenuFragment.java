package com.example.gbsbadrsf.Warehouse;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentWarehouseMainMenuBinding;

import java.util.Objects;

public class WarehouseMainMenuFragment extends Fragment {



    public WarehouseMainMenuFragment() {
        // Required empty public constructor
    }


    public static WarehouseMainMenuFragment newInstance() {
        return new WarehouseMainMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private FragmentWarehouseMainMenuBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWarehouseMainMenuBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.warehouseReceiving.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_warehouseMainMenuFragment_to_warehouseFragment));
        binding.weldingItemReceiving.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_warehouseMainMenuFragment_to_items_receiving_fragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.warehouse),(MainActivity) requireActivity());
    }
}