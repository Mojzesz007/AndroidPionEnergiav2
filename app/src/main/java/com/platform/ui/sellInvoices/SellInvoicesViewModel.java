package com.platform.ui.sellInvoices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellInvoicesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SellInvoicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is SellInvoicesFragment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}