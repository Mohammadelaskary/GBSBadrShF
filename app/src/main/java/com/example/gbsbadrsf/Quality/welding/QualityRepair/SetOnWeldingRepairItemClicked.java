package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;

public interface SetOnWeldingRepairItemClicked {
    void onWeldingRepairItemClicked(WeldingDefect defectsWelding, int position);
}
