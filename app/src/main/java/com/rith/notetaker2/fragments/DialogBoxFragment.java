package com.rith.notetaker2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.rith.notetaker2.R;
import com.rith.notetaker2.databinding.ItemDialogBoxBinding;

import java.util.List;


public class DialogBoxFragment extends DialogFragment {
    ItemDialogBoxBinding itemDialogBoxBinding;
    Listener listener;
    public DialogBoxFragment(Listener listener){
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        initUI();
        setUpOnClickListeners();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemDialogBoxBinding = DataBindingUtil.inflate(inflater, R.layout.item_dialog_box,container,false);
        return itemDialogBoxBinding.getRoot();
    }


    private void initUI() {
    }

    private void setUpOnClickListeners() {
        itemDialogBoxBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = itemDialogBoxBinding.textInputLayout.getEditText().getText().toString();
                listener.onOkClick(s);
                Log.d("string",s);
                dismiss();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }

    public interface Listener{
        void onOkClick(String s);
    }
}
