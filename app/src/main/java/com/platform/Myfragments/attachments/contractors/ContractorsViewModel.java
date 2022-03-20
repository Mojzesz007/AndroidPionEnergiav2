package com.platform.Myfragments.attachments.contractors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContractorsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ContractorsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ContractorsFragment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}