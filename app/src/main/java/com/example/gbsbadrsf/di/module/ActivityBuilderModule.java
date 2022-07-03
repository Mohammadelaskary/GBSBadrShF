//package com.example.gbsbadrsf.di.module;
//
//import com.example.gbsbadrsf.ApprovalRejectionRequest.ApprovalRejectionRequestsListFragment;
//import com.example.gbsbadrsf.ApprovalRejectionRequest.RejectionRequestClosingFragment;
//import com.example.gbsbadrsf.ChangeBaseUrlFragment;
//import com.example.gbsbadrsf.DeclineRejectionRequest.DeclineRejectionRequestDecisionFragment;
//import com.example.gbsbadrsf.DeclineRejectionRequest.DeclineRejectionRequestFragment;
//import com.example.gbsbadrsf.Manfacturing.BasketInfo.BasketInfoFragment;
//import com.example.gbsbadrsf.Manfacturing.Counting.ManufacturingCountingFragment;
//import com.example.gbsbadrsf.Manfacturing.machineloading.ContinueLoading;
//import com.example.gbsbadrsf.Manfacturing.machinesignoff.ProductionSignoffFragment;
//import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffFragment;
//import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListFragment;
//import com.example.gbsbadrsf.Paint.machineloadingpaint.MachineloadingpaintFragment;
//import com.example.gbsbadrsf.Paint.paintstation.Paintdstation;
//import com.example.gbsbadrsf.Paint.paintwip.MainPaintWip;
//import com.example.gbsbadrsf.Production.PaintProductionRepair.PaintProductionDefectRepairFragment;
//import com.example.gbsbadrsf.Production.PaintProductionRepair.PaintProductionRepairFragment;
//import com.example.gbsbadrsf.Production.ProductionDefectRepairFragment;
//import com.example.gbsbadrsf.Production.ProductionRepairFragment;
//import com.example.gbsbadrsf.Production.WeldingQuality.WeldingProductionDefectRepairFragment;
//import com.example.gbsbadrsf.Production.WeldingQuality.WeldingProductionRepairFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.ProductionRejectionFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.ManufacturingAddDefectDetailsFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.ManufacturingAddDefectsFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest.ProductionRejectionRequestFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest.ProductionRejectionRequestsListQualityFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.QualityDecision.QualityDecisionFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.QualityRepair.QualityDefectRepairFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.QualityRepair.QualityRepairFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception.RandomQualityInceptionFragment;
//import com.example.gbsbadrsf.Quality.manfacturing.SignOffBaskets.SignOffBasketsFragment;
//import com.example.gbsbadrsf.Quality.paint.AddDefects.PaintAddDefectDetailsFragment;
//import com.example.gbsbadrsf.Quality.paint.AddDefects.PaintAddDefectsFragment;
//import com.example.gbsbadrsf.Quality.paint.AddDefects.PaintDisplayDefectDetailsFragment;
//import com.example.gbsbadrsf.Quality.paint.PaintQualityOperationFragment;
//import com.example.gbsbadrsf.Quality.paint.QualityPaintFragment;
//import com.example.gbsbadrsf.Quality.paint.QualityRepair.PaintQualityDefectRepairFragment;
//import com.example.gbsbadrsf.Quality.paint.QualityRepair.PaintQualityRepairFragment;
//import com.example.gbsbadrsf.Quality.paint.QualitySignOff.PaintQualityDecisionFragment;
//import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PaintRandomQualityInceptionFragment;
//import com.example.gbsbadrsf.Quality.paint.RejectionRequest.PaintRejectionRequestFragment;
//import com.example.gbsbadrsf.Quality.paint.RejectionRequestsList.PaintRejectionRequestDetailsFragment;
//import com.example.gbsbadrsf.Quality.paint.RejectionRequestsList.PaintRejectionRequestsListQualityFragment;
//import com.example.gbsbadrsf.Quality.paint.SignOffBaskets.PaintSignOffBasketsFragment;
//import com.example.gbsbadrsf.Quality.welding.QualityRepair.WeldingQualityDefectRepairFragment;
//import com.example.gbsbadrsf.Quality.welding.QualityRepair.WeldingQualityRepairFragment;
//import com.example.gbsbadrsf.Quality.welding.QualitySignOff.WeldingQualityDecisionFragment;
//import com.example.gbsbadrsf.Quality.welding.RandomQualityInception.WeldingRandomQualityInceptionFragment;
//import com.example.gbsbadrsf.Quality.welding.RejectionRequest.WeldingRejectionRequestFragment;
//import com.example.gbsbadrsf.Quality.welding.RejectionRequestsList.WeldingRejectionRequestDetailsFragment;
//import com.example.gbsbadrsf.Quality.welding.RejectionRequestsList.WeldingRejectionRequestsListQualityFragment;
//import com.example.gbsbadrsf.Quality.welding.SignOffBaskets.WeldingSignOffBasketsFragment;
//import com.example.gbsbadrsf.Quality.welding.WeldingAddDefects.WeldingAddDefectDetailsFragment;
//import com.example.gbsbadrsf.Quality.welding.WeldingAddDefects.WeldingAddDefectsFragment;
//import com.example.gbsbadrsf.Quality.welding.WeldingQualityOperationFragment;
//import com.example.gbsbadrsf.machineloading.MachineLoadingFragment;
//import com.example.gbsbadrsf.machinewip.MachineWipMainFragment;
//import com.example.gbsbadrsf.productionsequence.ProductionSequence;
//import com.example.gbsbadrsf.signin.ChangePasswordFragment;
//import com.example.gbsbadrsf.signin.SigninFragment;
//import com.example.gbsbadrsf.warhouse.counting.CountingFragment;
//import com.example.gbsbadrsf.warhouse.warehouse.WarehouseFragment;
//import com.example.gbsbadrsf.welding.Counting.WeldingCountingFragment;
//import com.example.gbsbadrsf.welding.ItemsReceiving.ChildToBasketFragment;
//import com.example.gbsbadrsf.welding.ItemsReceiving.DisplayJobOrderDataFragment;
//import com.example.gbsbadrsf.welding.ItemsReceiving.ItemsReceivingFragment;
//import com.example.gbsbadrsf.welding.machineloadingwe.MachineloadingweFragment;
//import com.example.gbsbadrsf.welding.weldingsignoff.SignoffweFragment;
//import com.example.gbsbadrsf.welding.weldingwip.MainWeldingWip;
//import com.example.gbsbadrsf.weldingsequence.WeldingSequence;
//
//import dagger.Module;
//import dagger.android.ContributesAndroidInjector;
//
//@Module
//public abstract class ActivityBuilderModule {
//    @ContributesAndroidInjector
//    abstract ProductionSequence contributeproductionsequencefragment();
//    @ContributesAndroidInjector
//    abstract SigninFragment signinFragment();
//    @ContributesAndroidInjector
//    abstract MachineLoadingFragment machineLoadingFragment();
//    @ContributesAndroidInjector
//    abstract ProductionSignoffFragment productionSignoffFragment();
//    @ContributesAndroidInjector
//    abstract ManufacturingQualityOperationFragment contributeQualityManufacturingOperationFragment();
//    @ContributesAndroidInjector
//    abstract ManufacturingAddDefectsFragment contributeManufacturingAddDefectsFragment();
//    @ContributesAndroidInjector
//    abstract ManufacturingAddDefectDetailsFragment contributeManufacturingAddDefectDetailsFragment();
//    @ContributesAndroidInjector
//    abstract ProductionDefectRepairFragment contributeProductionDefectRepairFragment();
//    @ContributesAndroidInjector
//    abstract ProductionRepairFragment contributeProductionRepairFragment();
//    @ContributesAndroidInjector
//    abstract QualityDefectRepairFragment contributeQualityDefectRepairFragment();
//    @ContributesAndroidInjector
//    abstract QualityRepairFragment contributeQualityRepairFragment();
//    @ContributesAndroidInjector
//    abstract RandomQualityInceptionFragment contributeRandomQualityInceptionFragment();
//    @ContributesAndroidInjector
//    abstract ContinueLoading continueloadingFragment();
//    @ContributesAndroidInjector
//    abstract WeldingSequence weldingsequenceFragment();
//    @ContributesAndroidInjector
//    abstract MachineloadingweFragment machineloadingweFragment();
//    @ContributesAndroidInjector
//    abstract MachineWipMainFragment machinewipFragment();
//    @ContributesAndroidInjector
//    abstract SignoffweFragment signoffweFragment();
//    @ContributesAndroidInjector
//    abstract MainWeldingWip weldingwip();
//    @ContributesAndroidInjector
//    abstract ProductionRejectionFragment contributeProductionRejectionFragment();
//    @ContributesAndroidInjector
//    abstract QualityDecisionFragment contributeQualityDecisionFragment();
//    @ContributesAndroidInjector
//    abstract ProductionRejectionRequestsListQualityFragment contributeProductionRejectionRequestsListQualityFragment();
//    @ContributesAndroidInjector
//    abstract ProductionRejectionRequestFragment contributeProductionscraprequestqcFragment();
//    @ContributesAndroidInjector
//    abstract WeldingQualityOperationFragment contributeWeldingQualityOperationFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingAddDefectsFragment contributeWeldingAddDefectsFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingAddDefectDetailsFragment contributeWeldingAddDefectDetailsFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingQualityDefectRepairFragment contributeWeldingQualityDefectRepairFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingQualityRepairFragment contributeWeldingQualityRepairFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingProductionDefectRepairFragment contributeWeldingProductionDefectRepairFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingProductionRepairFragment contributeWeldingProductionRepairFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingRejectionRequestFragment contributeWeldingRejectionRequestFragment ();
//    @ContributesAndroidInjector
//    abstract CountingFragment contributecountingFragment ();
//    @ContributesAndroidInjector
//    abstract WarehouseFragment  contributewarehouseFragment ();
//    @ContributesAndroidInjector
//    abstract Paintdstation  contributepaintstationFragment ();
//    @ContributesAndroidInjector
//    abstract MainPaintWip contributepaintwipFragment ();
//
//
//    @ContributesAndroidInjector
//    abstract WeldingRejectionRequestsListQualityFragment contributeWeldingRejectionRequestsListQualityFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingRejectionRequestDetailsFragment contributeWeldingRejectionRequestDetailsFragment ();
//
//    @ContributesAndroidInjector
//    abstract WeldingQualityDecisionFragment contributeWeldingQualityDecisionFragment ();
//    @ContributesAndroidInjector
//    abstract PaintQualityDefectRepairFragment contributePaintQualityDefectRepairFragment ();
//    @ContributesAndroidInjector
//    abstract PaintQualityRepairFragment contributePaintQualityRepairFragment ();
//    @ContributesAndroidInjector
//    abstract PaintQualityDecisionFragment contributePaintQualityDecisionFragment ();
//    @ContributesAndroidInjector
//    abstract PaintRejectionRequestFragment contributePaintRejectionRequestFragment ();
//    @ContributesAndroidInjector
//    abstract PaintRejectionRequestDetailsFragment contributePaintRejectionRequestDetailsFragment ();
//    @ContributesAndroidInjector
//    abstract PaintRejectionRequestsListQualityFragment contributePaintRejectionRequestsListQualityFragment ();
//    @ContributesAndroidInjector
//    abstract PaintQualityOperationFragment contributePaintQualityOperationFragment ();
//    @ContributesAndroidInjector
//    abstract QualityPaintFragment contributeQualityPaintFragment ();
//    @ContributesAndroidInjector
//    abstract PaintAddDefectsFragment contributePaintAddDefectsFragment ();
//    @ContributesAndroidInjector
//    abstract PaintAddDefectDetailsFragment contributePaintAddDefectDetailsFragment ();
//    @ContributesAndroidInjector
//    abstract PaintDisplayDefectDetailsFragment contributePaintDisplayDefectDetailsFragment ();
//    @ContributesAndroidInjector
//    abstract PaintProductionRepairFragment contributePaintProductionRepairFragment ();
//    @ContributesAndroidInjector
//    abstract PaintProductionDefectRepairFragment contributePaintProductionDefectRepairFragment ();
//    @ContributesAndroidInjector
//    abstract PaintRandomQualityInceptionFragment contributePaintRandomQualityInceptionFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingRandomQualityInceptionFragment contributeWeldingRandomQualityInceptionFragment ();
//    @ContributesAndroidInjector
//    abstract MachineloadingpaintFragment contributeMachineloadingpaintFragment ();
//
//    @ContributesAndroidInjector
//    abstract ChangeBaseUrlFragment contributeChangeBaseUrlFragment ();
//
//    @ContributesAndroidInjector
//    abstract BasketInfoFragment contributeBasketInfoFragment ();
//    @ContributesAndroidInjector
//    abstract ChangePasswordFragment contributeChangePasswordFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingSignOffBasketsFragment contributeWeldingSignOffBasketsFragment ();
//    @ContributesAndroidInjector
//    abstract SignOffBasketsFragment contributeSignOffBasketsFragment ();
//    @ContributesAndroidInjector
//    abstract PaintSignOffBasketsFragment contributePaintSignOffBasketsFragment ();
//    @ContributesAndroidInjector
//    abstract ItemsReceivingFragment contributeItemsReceivingFragment ();
//    @ContributesAndroidInjector
//    abstract ChildToBasketFragment contributeChildToBasketFragment ();
//    @ContributesAndroidInjector
//    abstract ApprovalRejectionRequestsListFragment contributeApprovalRejectionRequestsListFragment ();
//    @ContributesAndroidInjector
//    abstract RejectionRequestClosingFragment contributeRejectionRequestClosingFragment ();
//    @ContributesAndroidInjector
//    abstract DeclineRejectionRequestDecisionFragment contributeDeclineRejectionRequestDecisionFragment ();
//    @ContributesAndroidInjector
//    abstract DeclineRejectionRequestFragment contributeDeclineRejectionRequestFragment ();
//    @ContributesAndroidInjector
//    abstract DisplayJobOrderDataFragment contributeDisplayJobOrderDataFragment ();
//    @ContributesAndroidInjector
//    abstract PaintSignOffPprListFragment contributePaintSignOffPprListFragment ();
//    @ContributesAndroidInjector
//    abstract PaintSignOffFragment contributePaintSignOffFragment ();
//    @ContributesAndroidInjector
//    abstract ManufacturingCountingFragment contributeManufacturingCountingFragment ();
//    @ContributesAndroidInjector
//    abstract WeldingCountingFragment contributeWeldingCountingFragment ();
//
//
//
//
//
//}
