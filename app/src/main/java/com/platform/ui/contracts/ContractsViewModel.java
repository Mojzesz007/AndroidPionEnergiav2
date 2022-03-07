package com.platform.ui.contracts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContractsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContractsViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}