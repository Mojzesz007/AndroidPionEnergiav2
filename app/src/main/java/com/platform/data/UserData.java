package com.platform.data;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {
    private Long id;
    private int version;
    private boolean draft;
    private String fullname;
    private String initials;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public String getInitials() { return initials; }

    public void setInitials(String initials) { this.initials = initials; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.version);
        dest.writeByte(this.draft ? (byte) 1 : (byte) 0);
        dest.writeString(this.fullname);
        dest.writeString(this.initials);
    }

    public UserData() {
    }

    protected UserData(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.version = in.readInt();
        this.draft = in.readByte() != 0;
        this.fullname = in.readString();
        this.initials = in.readString();
    }

    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}

