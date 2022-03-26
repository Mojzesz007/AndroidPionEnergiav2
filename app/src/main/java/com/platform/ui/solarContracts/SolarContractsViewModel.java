package com.platform.ui.solarContracts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SolarContractsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SolarContractsViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}