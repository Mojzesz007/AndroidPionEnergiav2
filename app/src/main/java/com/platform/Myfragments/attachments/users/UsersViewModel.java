package com.platform.Myfragments.attachments.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsersViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public UsersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ContractorsFragment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}