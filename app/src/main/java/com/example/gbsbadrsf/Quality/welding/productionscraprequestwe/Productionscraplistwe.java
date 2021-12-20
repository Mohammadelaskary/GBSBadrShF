package com.example.gbsbadrsf.Quality.welding.productionscraprequestwe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.FragmentProductionscraplistweBinding;


public class Productionscraplistwe extends Fragment implements OnClick {
FragmentProductionscraplistweBinding fragmentProductionscraplistweBinding;
ProductionscraplistAdapter productionscraplistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductionscraplistweBinding = FragmentProductionscraplistweBinding.inflate(inflater, container, false);
        initViews();

        return fragmentProductionscraplistweBinding.getRoot();

    }

    private void initViews() {
        productionscraplistAdapter = new ProductionscraplistAdapter(this);
        fragmentProductionscraplistweBinding.productionscrapscrapRv.setAdapter(productionscraplistAdapter);
        fragmentProductionscraplistweBinding.productionscrapscrapRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void OnItemClickedListener(int position) {
        Navigation.findNavController(getView()).navigate(R.id.action_productionscraplistwe_to_productionScraprequestwe);

    }
}