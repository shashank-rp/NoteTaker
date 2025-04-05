package com.rith.notetaker2.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.rith.notetaker2.R;
import com.rith.notetaker2.constants.UniversalConstants;
import com.rith.notetaker2.databinding.ActivityBidCreateBinding;
import com.rith.notetaker2.fragments.DialogBoxFragment;
import com.rith.notetaker2.model.TokenAdvanceModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class BidLotCreateActivity extends AppCompatActivity implements DialogBoxFragment.Listener{

    ActivityBidCreateBinding activityBidCreateBinding;
    CompositeDisposable compositeDisposable;
    MaterialAutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<TokenAdvanceModel> arrayAdapter;
    List<TokenAdvanceModel> tokenAdvanceModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBidCreateBinding = DataBindingUtil.setContentView(BidLotCreateActivity.this,R.layout.activity_bid_create);
        getIntentActivity();
        initUI();
        setupOnClickListeners();
        setupDisplayObservableAndObserver();
        setupDisplayTextChanges();
        publishSubject();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // make api calls here
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void getIntentActivity() {
        Toast.makeText(this,getIntent().getStringExtra(UniversalConstants.HELLO),Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
        tokenAdvanceModels.addAll(List.of(new TokenAdvanceModel(10,"aaa"),
                new TokenAdvanceModel(5,"baa"),
        new TokenAdvanceModel(6,"caa"),
        new TokenAdvanceModel(100,"daa")));

        Log.d("hello","hello");
        autoCompleteTextView = activityBidCreateBinding.txtAdvance;
        compositeDisposable = new CompositeDisposable();
        activityBidCreateBinding.txtHeadingBidTokenAdvance.setTextColor(getResources().getColor(R.color.white));
        arrayAdapter = new ArrayAdapter<>(getApplication().getApplicationContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item,tokenAdvanceModels);
        autoCompleteTextView.setAdapter(arrayAdapter);

    }

    private void setupOnClickListeners() {
        activityBidCreateBinding.btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBoxFragment dialogBoxFragment = new DialogBoxFragment(BidLotCreateActivity.this);
                dialogBoxFragment.show(getSupportFragmentManager(), "DIALOG");
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TokenAdvanceModel token = (TokenAdvanceModel) parent.getItemAtPosition(position);
                tokenAdvanceModels.forEach(
                        t->{
                            if(t.getId()==token.getId()){
                                Log.d("token",token+" token"+token.getTokenAmt()+" amt"+token.getId());
                            }
                        }
                );
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupDisplayObservableAndObserver() {
    }

    private void setupDisplayTextChanges() {
        Integer rate = 2;
        PublishSubject<Object> publishSubject = PublishSubject.create();
//        compositeDisposable.add(publishSubject.subscribe(
//                s->{
//                    Log.d("publish",s.toString());
//                }
//        ));
        TextInputLayout txtInputBidQty = activityBidCreateBinding.txtInputBidQuantity;
        TextInputLayout txtInputMark = activityBidCreateBinding.txtInputBuyerMark;

        compositeDisposable.add(
                RxTextView.textChanges(txtInputBidQty.getEditText())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                    .debounce(100, TimeUnit.MILLISECONDS)
                    .map(CharSequence::toString)
                    .subscribe(
                            s ->{
                                publishSubject.onNext(s);
                                Log.d("hello","hello"+ s);
                                if(StringUtils.isNotBlank(txtInputBidQty.getEditText().getText().toString())){
                                    Log.d("hello","hello"+ s);
                                }
                            }
                    )
        );

        compositeDisposable.add(
          publishSubject
                  .subscribeOn(AndroidSchedulers.mainThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(
            s ->{
                Log.d("publish",s.toString());
                String str = (String) s;
                if(NumberUtils.isParsable(str)){
                    int tot = (Integer.parseInt(str) * rate);
                    str = Integer.toString(tot);
                }
                activityBidCreateBinding.txtInputTotal.getEditText().setText(str);

            }
            ,
                  err->{
                        Log.d("err",err.getMessage());
                  }
          )
        );

        // text watcher

//        txtInputMark.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Logger.getLogger("hello "+s);
//                Log.d("hello","hello"+ s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        Observable<String> mark =
               RxTextView.textChanges(txtInputMark.getEditText())
                       .debounce(250,TimeUnit.MILLISECONDS)
                       .map(CharSequence::toString)
                       .map(StringUtils::upperCase)
                       .map(StringUtils::trim);

        compositeDisposable.add(
                mark.toFlowable(BackpressureStrategy.LATEST)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                s -> {
                                    Log.d("hello",s);
                                }
                        )
        );


    }

    void publishSubject(){
        PublishSubject<Object> publishSubject = PublishSubject.create();
        compositeDisposable.add(publishSubject.subscribe(
                s->{
                    Log.d("publish",s.toString());
                }
        ));
        publishSubject.onNext("hello");
        publishSubject.onNext("hi");
    }


    @Override
    public void onOkClick(String s) {
        Log.d("string",s);
        activityBidCreateBinding.txtInputAdditionalPrice.getEditText().setText(s);
    }
}
