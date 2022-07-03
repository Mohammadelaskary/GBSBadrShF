package com.example.gbsbadrsf;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.databinding.BasketsDialogBinding;

import java.util.List;

public class WeldingSignInBasketsDialog extends Dialog {
    public WeldingSignInBasketsDialog(@NonNull Context context) {
        super(context);
        adapter = new WeldingSignInBasketsAdapter();
    }

    public void setBaskets(List<Baskets> baskets) {
        adapter.setBaskets(baskets);

    }

    private BasketsDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BasketsDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUpRecyclerView();
        binding.close.setOnClickListener(v -> dismiss());
    }

    private WeldingSignInBasketsAdapter adapter;

    private void setUpRecyclerView() {
        binding.baskets.setAdapter(adapter);
    }
}
