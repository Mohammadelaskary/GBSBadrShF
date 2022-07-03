//package com.example.gbsbadrsf.di.module;
//
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.example.gbsbadrsf.ApprovalRejectionRequest.ApprovalRejectionRequestsListViewModel;
//import com.example.gbsbadrsf.ApprovalRejectionRequest.RejectionRequestClosingViewModel;
//import com.example.gbsbadrsf.ChangeIpViewModel;
//import com.example.gbsbadrsf.DeclineRejectionRequest.DeclineRejectionRequestDecisionViewModel;
//import com.example.gbsbadrsf.DeclineRejectionRequest.DeclineRejectionRequestViewModel;
//import com.example.gbsbadrsf.Manfacturing.BasketInfo.BasketInfoViewModel;
//import com.example.gbsbadrsf.Manfacturing.Counting.ManufacturingCountingViewModel;
//import com.example.gbsbadrsf.Manfacturing.machineloading.ContinueLoadingViewModel;
//import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachinesignoffViewModel;
//import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
//import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffViewModel;
//import com.example.gbsbadrsf.Paint.machineloadingpaint.SavepaintViewModel;
//import com.example.gbsbadrsf.Paint.paintstation.InfoForSelectedPaintViewModel;
//import com.example.gbsbadrsf.Paint.paintstation.PaintstationViewModel;
//import com.example.gbsbadrsf.Paint.paintwip.PaintViewModel;
//import com.example.gbsbadrsf.Production.Data.ProductionDefectRepairViewModel;
//import com.example.gbsbadrsf.Production.Data.ProductionRejectionViewModel;
//import com.example.gbsbadrsf.Production.Data.ProductionRepairViewModel;
//import com.example.gbsbadrsf.Production.PaintProductionRepair.ViewModel.PaintProductionDefectRepairViewModel;
//import com.example.gbsbadrsf.Production.PaintProductionRepair.ViewModel.PaintProductionRepairViewModel;
//import com.example.gbsbadrsf.Production.WeldingQuality.ViewModel.WeldingProductionDefectRepairViewModel;
//import com.example.gbsbadrsf.Production.WeldingQuality.ViewModel.WeldingProductionRepairViewModel;
//import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsDetailsViewModel;
//import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsViewModel;
//import com.example.gbsbadrsf.Quality.Data.ManufacturingQualityOperationViewModel;
//import com.example.gbsbadrsf.Quality.Data.QualityDecisionViewModel;
//import com.example.gbsbadrsf.Quality.Data.QualityDefectRepairViewModel;
//import com.example.gbsbadrsf.Quality.Data.RandomQualityInceptionViewModel;
//import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.QualityRepairViewModel;
//import com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest.ProductionRejectionRequestViewModel;
//import com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest.ProductionRejectionRequestsListQualityViewModel;
//import com.example.gbsbadrsf.Quality.manfacturing.SignOffBaskets.SignOffBasketsViewModel;
//import com.example.gbsbadrsf.Quality.paint.QualitySignOff.PaintQualityDecisionViewModel;
//import com.example.gbsbadrsf.Quality.paint.RejectionRequest.PaintRejectionRequestFragment;
//import com.example.gbsbadrsf.Quality.paint.RejectionRequestsList.PaintRejectionRequestDetailsViewModel;
//import com.example.gbsbadrsf.Quality.paint.RejectionRequestsList.PaintRejectionRequestsListQualityViewModel;
//import com.example.gbsbadrsf.Quality.paint.SignOffBaskets.PaintSignOffBasketsViewModel;
//import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintAddDefectsDetailsViewModel;
//import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
//import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityRepairViewModel;
//import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintRandomQualityInceptionViewModel;
//import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintRejectionRequestViewModel;
//import com.example.gbsbadrsf.Quality.welding.QualitySignOff.WeldingQualityDecisionViewModel;
//import com.example.gbsbadrsf.Quality.welding.RejectionRequestsList.WeldingRejectionRequestDetailsViewModel;
//import com.example.gbsbadrsf.Quality.welding.RejectionRequestsList.WeldingRejectionRequestsListQualityViewModel;
//import com.example.gbsbadrsf.Quality.welding.SignOffBaskets.WeldingSignOffBasketsViewModel;
//import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingAddDefectsDetailsViewModel;
//import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingAddDefectsViewModel;
//import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityDefectRepairViewModel;
//import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityOperationViewModel;
//import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityRepairViewModel;
//import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingRandomQualityInceptionViewModel;
//import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingRejectionRequestViewModel;
//import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
//import com.example.gbsbadrsf.di.ViewModelKey;
//import com.example.gbsbadrsf.machineloading.MachineloadingViewModel;
//import com.example.gbsbadrsf.machinewip.MachinewipViewModel;
//import com.example.gbsbadrsf.productionsequence.ProductionsequenceViewModel;
//import com.example.gbsbadrsf.productionsequence.SelectedLoadinsequenceinfoViewModel;
//import com.example.gbsbadrsf.signin.ChangePasswordViewModel;
//import com.example.gbsbadrsf.signin.SignInViewModel;
//import com.example.gbsbadrsf.warhouse.counting.CountingViewModel;
//import com.example.gbsbadrsf.warhouse.warehouse.WarehouseViewModel;
//import com.example.gbsbadrsf.welding.Counting.WeldingCountingViewModel;
//import com.example.gbsbadrsf.welding.ItemsReceiving.ChildToBasketViewModel;
//import com.example.gbsbadrsf.welding.ItemsReceiving.DisplayJobOrderDataViewModel;
//import com.example.gbsbadrsf.welding.ItemsReceiving.ItemsReceivingViewModel;
//import com.example.gbsbadrsf.welding.machineloadingwe.SaveweldingViewModel;
//import com.example.gbsbadrsf.welding.weldingsignoff.SignoffweViewModel;
//import com.example.gbsbadrsf.welding.weldingwip.WeldingvieModel;
//import com.example.gbsbadrsf.weldingsequence.InfoForSelectedStationViewModel;
//import com.example.gbsbadrsf.weldingsequence.WeldingsequenceViewModel;
//
//import dagger.Binds;
//import dagger.Module;
//import dagger.multibindings.IntoMap;
//
//@Module
//public abstract class ViewModelModule {
//    @Binds
//    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductionsequenceViewModel.class)
//    public abstract ViewModel bindProductionseqViewModel (ProductionsequenceViewModel productionsequenceViewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(SignInViewModel.class)
//    public abstract ViewModel viewModel (SignInViewModel signInViewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(SelectedLoadinsequenceinfoViewModel.class)
//    public abstract ViewModel selectedviemodel (SelectedLoadinsequenceinfoViewModel selectedLoadinsequenceinfoViewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(MachineloadingViewModel.class)
//    public abstract ViewModel productionsequenceviewmodel (MachineloadingViewModel machineloadingViewModel );
//    @Binds
//    @IntoMap
//    @ViewModelKey(MachinesignoffViewModel.class)
//    public abstract ViewModel machinesignoffviemodel (MachinesignoffViewModel machinesignoffViewModel );
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ManufacturingQualityOperationViewModel.class)
//    public abstract ViewModel bindManufacturingQualityOperationViewModel (ManufacturingQualityOperationViewModel manfacturingViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ManufacturingAddDefectsViewModel.class)
//    public abstract ViewModel bindManufacturingAddDefectsViewModel (ManufacturingAddDefectsViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ManufacturingAddDefectsDetailsViewModel.class)
//    public abstract ViewModel bindManufacturingAddDefectsDetailsViewModel (ManufacturingAddDefectsDetailsViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductionRepairViewModel.class)
//    public abstract ViewModel bindProductionRepairViewModel (ProductionRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductionDefectRepairViewModel.class)
//    public abstract ViewModel bindProductionDefectRepairViewModel (ProductionDefectRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(QualityRepairViewModel.class)
//    public abstract ViewModel bindQualityRepairViewModel (QualityRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(QualityDefectRepairViewModel.class)
//    public abstract ViewModel bindQualityDefectRepairViewModel (QualityDefectRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(RandomQualityInceptionViewModel.class)
//    public abstract ViewModel bindRandomQualityInceptionViewModel (RandomQualityInceptionViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ContinueLoadingViewModel.class)
//    public abstract ViewModel bindcontinueloadingViewmodel (ContinueLoadingViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingsequenceViewModel.class)
//    public abstract ViewModel bindweldingsequenceViewmodel (WeldingsequenceViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(InfoForSelectedStationViewModel.class)
//    public abstract ViewModel bindinfoforselectedstationviewmodel (InfoForSelectedStationViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(SaveweldingViewModel.class)
//    public abstract ViewModel bindsaveweldingviewmodel (SaveweldingViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(MachinewipViewModel.class)
//    public abstract ViewModel bindmachinewipviewmodel (MachinewipViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(SignoffweViewModel.class)
//    public abstract ViewModel bindsignoffviewmodel (SignoffweViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingvieModel.class)
//    public abstract ViewModel bindweldingwipviewmodel (WeldingvieModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductionRejectionViewModel.class)
//    public abstract ViewModel bindProductionRejectionViewModel (ProductionRejectionViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductionRejectionRequestsListQualityViewModel.class)
//    public abstract ViewModel bindProductionRejectionRequestsListQualityViewModel (ProductionRejectionRequestsListQualityViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(QualityDecisionViewModel.class)
//    public abstract ViewModel bindQualityDecisionViewModel (QualityDecisionViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductionRejectionRequestViewModel.class)
//    public abstract ViewModel bindProductionRejectionRequestViewModel (ProductionRejectionRequestViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingQualityOperationViewModel.class)
//    public abstract ViewModel bindWeldingQualityOperationViewModel (WeldingQualityOperationViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingAddDefectsViewModel.class)
//    public abstract ViewModel bindWeldingAddDefectsViewModel (WeldingAddDefectsViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingAddDefectsDetailsViewModel.class)
//    public abstract ViewModel bindWeldingAddDefectsDetailsViewModel (WeldingAddDefectsDetailsViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingQualityDefectRepairViewModel.class)
//    public abstract ViewModel bindWeldingQualityDefectRepairViewModel (WeldingQualityDefectRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingQualityRepairViewModel.class)
//    public abstract ViewModel bindWeldingQualityRepairViewModel (WeldingQualityRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingProductionRepairViewModel.class)
//    public abstract ViewModel bindWeldingProductionRepairViewModel (WeldingProductionRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingProductionDefectRepairViewModel.class)
//    public abstract ViewModel bindWeldingProductionDefectRepairViewModel (WeldingProductionDefectRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingRejectionRequestViewModel.class)
//    public abstract ViewModel bindWeldingRejectionRequestViewModel (WeldingRejectionRequestViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingRejectionRequestsListQualityViewModel.class)
//    public abstract ViewModel bindWeldingRejectionRequestsListQualityViewModel (WeldingRejectionRequestsListQualityViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingRejectionRequestDetailsViewModel.class)
//    public abstract ViewModel bindWeldingRejectionRequestDetailsViewModel (WeldingRejectionRequestDetailsViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingQualityDecisionViewModel.class)
//    public abstract ViewModel bindWeldingQualityDecisionViewModel (WeldingQualityDecisionViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintQualityDecisionViewModel.class)
//    public abstract ViewModel bindPaintQualityDecisionViewModel (PaintQualityDecisionViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintRejectionRequestDetailsViewModel.class)
//    public abstract ViewModel bindPaintRejectionRequestFragment (PaintRejectionRequestDetailsViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintRejectionRequestsListQualityViewModel.class)
//    public abstract ViewModel bindPaintRejectionRequestsListQualityViewModel (PaintRejectionRequestsListQualityViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintQualityOperationViewModel.class)
//    public abstract ViewModel bindPaintQualityOperationViewModel (PaintQualityOperationViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintRejectionRequestViewModel.class)
//    public abstract ViewModel bindPaintRejectionRequestViewModel (PaintRejectionRequestViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintQualityRepairViewModel.class)
//    public abstract ViewModel bindPaintQualityRepairViewModel (PaintQualityRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintProductionDefectRepairViewModel.class)
//    public abstract ViewModel bindPaintProductionDefectRepairViewModel (PaintProductionDefectRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintProductionRepairViewModel.class)
//    public abstract ViewModel bindPaintProductionRepairViewModel (PaintProductionRepairViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintRandomQualityInceptionViewModel.class)
//    public abstract ViewModel bindPaintRandomQualityInceptionViewModel (PaintRandomQualityInceptionViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingRandomQualityInceptionViewModel.class)
//    public abstract ViewModel bindWeldingRandomQualityInceptionViewModel (WeldingRandomQualityInceptionViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintstationViewModel.class)
//    public abstract ViewModel bindPaintstationViewModel (PaintstationViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(InfoForSelectedPaintViewModel.class)
//    public abstract ViewModel bindinfopaintViewModel (InfoForSelectedPaintViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(SavepaintViewModel.class)
//    public abstract ViewModel bindsaveViewModel (SavepaintViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(CountingViewModel.class)
//    public abstract ViewModel bindcountingViewModel (CountingViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WarehouseViewModel.class)
//
//    public abstract ViewModel bindwarehouseViewModel (WarehouseViewModel viewModel);
////    @Binds
////    @IntoMap
////    @ViewModelKey(PaintstationViewModel.class)
////    public abstract ViewModel bindpaintViewModel (PaintstationViewModel viewModel);
////    @Binds
////    @IntoMap
////    @ViewModelKey(InfoForSelectedPaintViewModel.class)
////    public abstract ViewModel bindpaintinfoViewModel (InfoForSelectedPaintViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintViewModel.class)
//    public abstract ViewModel bindpaintwipVieWModel (PaintViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ChangeIpViewModel.class)
//    public abstract ViewModel bindChangeIpViewModel (ChangeIpViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(BasketInfoViewModel.class)
//    public abstract ViewModel bindBasketInfoViewModel (BasketInfoViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ChangePasswordViewModel.class)
//    public abstract ViewModel bindChangePasswordViewModel (ChangePasswordViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(SignOffBasketsViewModel.class)
//    public abstract ViewModel bindSignOffBasketsViewModel (SignOffBasketsViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingSignOffBasketsViewModel.class)
//    public abstract ViewModel bindWeldingSignOffBasketsViewModel (WeldingSignOffBasketsViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintSignOffBasketsViewModel.class)
//    public abstract ViewModel bindPaintSignOffBasketsViewModel (PaintSignOffBasketsViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ItemsReceivingViewModel.class)
//    public abstract ViewModel bindItemsReceivingViewModel (ItemsReceivingViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ChildToBasketViewModel.class)
//    public abstract ViewModel bindChildToBasketViewModel (ChildToBasketViewModel viewModel);
//@Binds
//@IntoMap
//@ViewModelKey(PaintAddDefectsDetailsViewModel.class)
//public abstract ViewModel bindPaintAddDefectsDetailsViewModel (PaintAddDefectsDetailsViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ApprovalRejectionRequestsListViewModel.class)
//    public abstract ViewModel bindApprovalRejectionRequestsListViewModel (ApprovalRejectionRequestsListViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(RejectionRequestClosingViewModel.class)
//    public abstract ViewModel bindRejectionRequestClosingViewModel (RejectionRequestClosingViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(DeclineRejectionRequestDecisionViewModel.class)
//    public abstract ViewModel bindDeclineRejectionRequestDecisionViewModel (DeclineRejectionRequestDecisionViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(DeclineRejectionRequestViewModel.class)
//    public abstract ViewModel bindDeclineRejectionRequestViewModel (DeclineRejectionRequestViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(DisplayJobOrderDataViewModel.class)
//    public abstract ViewModel bindDisplayJobOrderDataViewModel (DisplayJobOrderDataViewModel viewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintSignOffPprListViewModel.class)
//    public abstract ViewModel bindPaintSignOffPprListViewModel (PaintSignOffPprListViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(PaintSignOffViewModel.class)
//    public abstract ViewModel bindPaintSignOffViewModel (PaintSignOffViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(ManufacturingCountingViewModel.class)
//    public abstract ViewModel bindManufacturingCountingViewModel (ManufacturingCountingViewModel viewModel);
//    @Binds
//    @IntoMap
//    @ViewModelKey(WeldingCountingViewModel.class)
//    public abstract ViewModel bindWeldingCountingViewModel (WeldingCountingViewModel viewModel);
//
//
//
//}
