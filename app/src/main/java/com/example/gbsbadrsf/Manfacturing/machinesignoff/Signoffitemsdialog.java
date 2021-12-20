package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.Constant;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.productionsequence.productionsequenceadapter;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.gbsbadrsf.MainActivity.getBarcodeObject;

public class Signoffitemsdialog extends DialogFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, productionsequenceadapter.onCheckedChangedListener {
    private static final String TAG = "MyCustomDialog";

    public interface OnInputSelected{
       // void sendInput(String input);
        void sendlist(List<Basketcodelst> input);


    }
    public OnInputSelected mOnInputSelected;

    Constant constant = new Constant();
    private RecyclerView recyclerView;
    private ProductionSignoffAdapter productionSignoffadapter;
    private com.honeywell.aidc.BarcodeReader barcodeReader;
    EditText editText,totalqtn,basketcode;
    Button save;
    TextView childdesc,signoffqty;
    List<Basketcodelst> basketcodelstList;
    public Integer totalQty = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.signoffcustomdialog,container,false);
        Switch simpleSwitch = view.findViewById(R.id.simpleSwitch); // initiate Switch
        recyclerView = view.findViewById(R.id.basketcode_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        basketcodelstList = new ArrayList<>();
        setUpRecyclerView();
        save=view.findViewById(R.id.save_btn);
        totalqtn=view.findViewById(R.id.totalqtn_edt);
        basketcode=view.findViewById(R.id.basketcode_edt);
        childdesc=view.findViewById(R.id.childdesc);
        signoffqty=view.findViewById(R.id.signoffqty);

        Bundle mArgs = getArguments();
        childdesc.setText( mArgs.getString("childdesc"));
        signoffqty.setText( mArgs.getString("loadingqty"));




        clickinginsave();
        //open and close switch
simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // do something when check is selected
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            //do something when unchecked
            recyclerView.setVisibility(View.GONE);

        }

    }
})      ;


//        productionSignoffadapter =new ProductionSignoffAdapter(basketcodelstList);
//        recyclerView.setAdapter(productionSignoffadapter);

        barcodeReader = getBarcodeObject();


        if (barcodeReader != null) {

            // register bar code event listener
            barcodeReader.addBarcodeListener(this);

            // set the trigger mode to client control
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
            } catch (UnsupportedPropertyException e) {
                // Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_SHORT).show();
            }
            // register trigger state change listener
            barcodeReader.addTriggerListener(this);

            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, true);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 30);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Disable bad read response, handle in onFailureEvent
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
            // Apply the settings
            properties.put(BarcodeReader.PROPERTY_EAN_13_CHECK_DIGIT_TRANSMIT_ENABLED, true);
            barcodeReader.setProperties(properties);
        }
        editText=view.findViewById(R.id.basketcode_edt);


        return view;
    }



    @Override
    public void onCheckedChanged(int position, boolean isChecked, Ppr item) {

    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        // editText.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
        Log.d("barcode",String.valueOf(barcodeReadEvent.getBarcodeData()));
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // update the ui from here
                if (totalqtn.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "please enter quantity ", Toast.LENGTH_SHORT).show();
                }else {
                    editText.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));

                   // Basketcodelst nwItem = new Basketcodelst(String.valueOf(barcodeReadEvent.getBarcodeData()), Integer.valueOf(signoffqty.getText().toString()));
                    Basketcodelst nwItem = new Basketcodelst(String.valueOf(barcodeReadEvent.getBarcodeData()), (constant.getTotalQtyVar()));
                    if (!productionSignoffadapter.getproductionsequencelist().contains(nwItem)) {
                        basketcodelstList.add(nwItem);
                        productionSignoffadapter.notifyDataSetChanged();
                    }
                }
            }
        });




    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        try {
            // only handle trigger presses
            // turn on/off aimer, illumination and decoding
            barcodeReader.aim(triggerStateChangeEvent.getState());
            barcodeReader.light(triggerStateChangeEvent.getState());
            barcodeReader.decode(triggerStateChangeEvent.getState());

        } catch (ScannerNotClaimedException e) {
            e.printStackTrace();
        } catch (ScannerUnavailableException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.release();
        }
    }

    private void setUpRecyclerView() {
        productionSignoffadapter = new ProductionSignoffAdapter(basketcodelstList);
        productionSignoffadapter.getproductionsequencelist(basketcodelstList);
        recyclerView.setAdapter(productionSignoffadapter);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    private void clickinginsave() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalQty = basketcodelstList.get(0).getQty();
                constant.incrementTotalQty(totalQty);
                //Log.d("****"+TAG, "TotalQty: "+constant.getTotalQtyVar());
                String input = totalqtn.getText().toString();
                String basketcodeinput=new Gson().toJson(basketcodelstList);
                //list

//                    if(!input.equals("")){
//
//                   // mOnInputSelected.sendInput(input);
//                }
                //for list
                    mOnInputSelected.sendlist(basketcodelstList);

                getDialog().dismiss();

            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }

}
