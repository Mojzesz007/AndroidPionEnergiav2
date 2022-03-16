package com.platform.Myfragments.attachments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AttachmentsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AttachmentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is SellInvoicesFragment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}