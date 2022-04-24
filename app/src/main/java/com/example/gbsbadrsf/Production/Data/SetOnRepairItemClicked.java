package com.example.gbsbadrsf.Production.Data;

import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;

public interface SetOnRepairItemClicked {
    void onRepairItemClicked(ManufacturingDefect defectsManufacturing, int position, int pending);
}
