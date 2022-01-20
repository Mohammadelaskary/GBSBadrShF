package com.example.gbsbadrsf;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.databinding.CustomAlertDialogWithChoicesBinding;

public class CustomChoiceDialog extends Dialog {
    private String title;
    private String message;
    private OnOkClicked onOkClicked;
    private OnCancelClicked onCancelClicked;
    public CustomChoiceDialog(@NonNull Context context,String title,String message) {
        super(context);
        this.title = title;
        this.message = message;
    }
    CustomAlertDialogWithChoicesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CustomAlertDialogWithChoicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.title.setText(title);
        binding.message.setText(message);
        binding.yes.setOnClickListener(v ->onOkClicked.onOkClicked());
        binding.cancel.setOnClickListener(v->{
            dismiss();
            onCancelClicked.onCancelClicked();
        });
    }

    public void setOnOkClicked(OnOkClicked onOkClicked) {
        this.onOkClicked = onOkClicked;
        dismiss();
    }

    public void setOnCancelClicked(OnCancelClicked onCancelClicked) {
        this.onCancelClicked = onCancelClicked;
    }

    public interface OnOkClicked{
        void onOkClicked();
    }
    public interface OnCancelClicked{
        void onCancelClicked();
    }
}
