package com.example.gbsbadrsf.Quality.welding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentWeldingQualityOperationBinding;


public class WeldingQualityOperationFragment extends Fragment implements View.OnClickListener {



    public WeldingQualityOperationFragment() {
        // Required empty public constructor
    }


    public static WeldingQualityOperationFragment newInstance(String param1, String param2) {
        WeldingQualityOperationFragment fragment = new WeldingQualityOperationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentWeldingQualityOperationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeldingQualityOperationBinding.inflate(
                inflater,
                container,
                false
        );
        attachListners();
        return binding.getRoot();
    }

    private void attachListners() {
        binding.addDefectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.add_defect_button:{
                Bundle bundle = new Bundle();
                bundle.putInt("basket_code",123456);
                Navigation.findNavController(v).navigate(R.id.action_welding_quality_operation_fragment_to_welding_add_defect_fragment,bundle);
            } break;
        }
    }
}