package com.example.gbsbadrsf.Quality.welding.productionscraprequestwe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.ProductionscraplstWeBinding;

public class ProductionscraplistAdapter extends RecyclerView.Adapter<ProductionscraplistAdapter.ProductionscraplistViewHolder> {
    ProductionscraplstWeBinding productionscraplstWeBinding;
    final OnClick onClick;

    public ProductionscraplistAdapter(OnClick onClick) {
        this.onClick = onClick;
    }
    @NonNull
    @Override
    public ProductionscraplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productionscraplstWeBinding = ProductionscraplstWeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductionscraplistViewHolder(productionscraplstWeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionscraplistViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.OnItemClickedListener(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    class ProductionscraplistViewHolder extends RecyclerView.ViewHolder{
        TextView jobordernametxt,jobordername,Deptxt,Dep,operationtxt,operation,scrapquantitytxt,scrapquantity;

        public ProductionscraplistViewHolder(@NonNull ProductionscraplstWeBinding itemView) {
            super(itemView.getRoot());
            jobordernametxt=itemView.jobordernumTxt;
            jobordername=itemView.jobordername;
            Deptxt=itemView.depTxt;
            Dep=itemView.department;
            operationtxt=itemView.operationTxt;
            operation=itemView.operation;
            scrapquantitytxt=itemView.scrapqtnTxt;
            scrapquantity=itemView.scrapqtn;







        }
    }

}
