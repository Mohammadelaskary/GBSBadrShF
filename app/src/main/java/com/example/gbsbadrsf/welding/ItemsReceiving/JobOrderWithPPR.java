package com.example.gbsbadrsf.welding.ItemsReceiving;

import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;

import java.util.List;

public class JobOrderWithPPR {
    private JobOrder jobOrder;
    private List<Ppr> listOfPpr;

    public JobOrderWithPPR(JobOrder jobOrder, List<Ppr> listOfPpr) {
        this.jobOrder = jobOrder;
        this.listOfPpr = listOfPpr;
    }

    public List<Ppr> getListOfPpr() {
        return listOfPpr;
    }

    public void setListOfPpr(List<Ppr> listOfPpr) {
        this.listOfPpr = listOfPpr;
    }

    public JobOrder getJobOrder() {
        return jobOrder;
    }

    public void setJobOrder(JobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }
}
