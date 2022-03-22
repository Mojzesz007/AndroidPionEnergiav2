package com.platform.Myfragments.attachments.sellInvoiceItems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellInvoiceItemsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SellInvoiceItemsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is viewOnlyAttachments fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}