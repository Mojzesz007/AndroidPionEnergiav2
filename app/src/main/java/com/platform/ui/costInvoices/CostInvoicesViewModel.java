package com.platform.ui.costInvoices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CostInvoicesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CostInvoicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is SellInvoicesFragment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}