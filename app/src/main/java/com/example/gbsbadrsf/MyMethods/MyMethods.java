package com.example.gbsbadrsf.MyMethods;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.CustomDialog;
import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Model.DefectsPerQty;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.productionsequence.ProductionSequence;
import com.google.android.material.textfield.TextInputLayout;
import com.tapadoo.alerter.Alerter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MyMethods {
    public static boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    public static ProgressDialog loadingProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        return progressDialog;
    }
    public static void hideToolBar(MainActivity mainActivity) {
        mainActivity.getSupportActionBar().hide();
    }
    public static void showToolBar(MainActivity mainActivity) {
        mainActivity.getSupportActionBar().show();
    }
    public static void changeTitle(String mainTitle, MainActivity mainActivity) {
        mainActivity.getSupportActionBar().setTitle(mainTitle);
    }

    public static void warningDialog(Context context,String message){
        new CustomDialog(context,message, R.drawable.ic_warning_alert).show();
    }
    public static void successDialog(Context context,String message){
        new CustomDialog(context,message, R.drawable.ic_done).show();
    }
    public static void  back(Fragment fragment){
        NavController navController = NavHostFragment.findNavController(fragment);
        navController.popBackStack();
    }
    public static void  clearInputLayoutError(TextInputLayout inputLayout){
        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputLayout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputLayout.setError(null);
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        if (activity!=null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager)   activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public static void activateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,1.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    public static void deactivateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,0.4f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    public static long getRemainingTime(String expectedSignOut) {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(expectedSignOut);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d.getTime() - currentDate.getTime();
    }

    public static void startRemainingTimeTimer(long remainingTime, TextView remainingTimeTv){
        if (remainingTime>0) {
            new CountDownTimer(remainingTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTimeTv.setText(convertToTimeFormat(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    remainingTimeTv.setText("Operation Finished");
                }
            }.start();
        } else
            remainingTimeTv.setText("Operation Finished");
    }

    public static String convertToTimeFormat(long millisUntilFinished) {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);
        return formatter.format(millisUntilFinished);
    }
    public static String getEditTextText(EditText editText){
        return editText.getText().toString().trim();
    }

    public static void showSuccessAlerter(String message,Activity activity){
       Alerter.create(activity).setText(message)
                .setIcon(R.drawable.ic_done)
                .setBackgroundColorInt(activity.getResources().getColor(R.color.alerter_success_color))
                .setDuration(1000)
                .setTextAppearance(R.style.alerter_text_appearance)
                .setEnterAnimation(R.anim.alerter_slide_in_from_top)
                .setExitAnimation(R.anim.alerter_slide_out_to_top)
                .show();
    }
    public static String getCurrentDate() {
        return new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
    }

    public static <T> boolean compareList(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static List<DefectsPerQty> getDefectsPerQtyList(List<ManufacturingDefect> manufacturingDefects) {
        List<DefectsPerQty> defects = new ArrayList<>();
        int id = -1 ;
        for (ManufacturingDefect manufacturingDefect:manufacturingDefects){
            if (manufacturingDefect.getDefectGroupId()!=id){
                int currentId = manufacturingDefect.getDefectGroupId();
                int defectedQty;
                if (!manufacturingDefect.getIsRejectedQty())
                    defectedQty = manufacturingDefect.getQtyDefected();
                else
                    defectedQty = manufacturingDefect.getQtyRejected();
                boolean isRejected = manufacturingDefect.getIsRejectedQty();
                DefectsPerQty qtyDefectsQtyDefected = new DefectsPerQty(currentId,defectedQty,getDefectsNames(currentId,manufacturingDefects),getDefectsIds(currentId,manufacturingDefects),isRejected);
                defects.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return defects;
    }
    //    private int qty=0;
//    private List<DefectsPerQty> groupDefectsByDefectsList(List<DefectsPerQty>defects){
//        List<DefectsPerQty> resultDefectsList = new ArrayList<>();
//        List<String> defectsList = new ArrayList<>();
//        DefectsPerQty defectsPerQty1 = null;
//        for (int i = 0; i < defects.size() ; i++) {
//            if (defectsPerQty1==null) {
//                qty = defects.get(i).getQty();
//               defectsPerQty1 = new DefectsPerQty(defects.get(i).getId(),qty,defects.get(i).getDefects(),defects.get(i).isRejected());
//            } else {
//                if (!compareList(defects.get(i).getDefects(), defectsList)) {
//                    resultDefectsList.add(defectsPerQty1);
//                    qty = defects.get(i).getQty();
//                    defectsPerQty1 = new DefectsPerQty(defects.get(i).getId(),qty,defects.get(i).getDefects(),defects.get(i).isRejected());
//                } else {
//                    qty+=defects.get(i).getQty();
//                    if (i==defects.size()-1){
//                        defectsPerQty1.setQty(qty);
//                        resultDefectsList.add(defectsPerQty1);
//                    } else {
//                        defectsPerQty1.setQty(qty);
//                    }
//                }
//            }
//        }
//        return resultDefectsList;
//    }
    public static List<String> getDefectsNames(int currentId,List<ManufacturingDefect> manufacturingDefects) {
        List<String> defectsNames = new ArrayList<>();
        for (ManufacturingDefect defectsManufacturing:manufacturingDefects){
            if (defectsManufacturing.getDefectGroupId()==currentId) {
                Defect defect = new Defect(defectsManufacturing.getDefectId(),defectsManufacturing.getDefectDescription());
                defectsNames.add(defect.getName());
            }
        }
        return  defectsNames;
    }
    public static List<Integer> getDefectsIds(int currentId,List<ManufacturingDefect> manufacturingDefects) {
        List<Integer> defectsIds = new ArrayList<>();
        for (ManufacturingDefect defectsManufacturing:manufacturingDefects){
            if (defectsManufacturing.getDefectGroupId()==currentId) {
                Defect defect = new Defect(defectsManufacturing.getDefectId(),defectsManufacturing.getDefectDescription());
                defectsIds.add(defect.getId());
            }
        }
        return  defectsIds;
    }

}
