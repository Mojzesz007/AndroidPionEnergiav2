package com.platform.Myfragments.attachments.viewOnlyAttachments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewOnlyAttachmentsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ViewOnlyAttachmentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is viewOnlyAttachments fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}