package com.luthfialfarisi.dicodingkamus.models;

import android.os.Parcel;
import android.os.Parcelable;


public class KamusItems implements Parcelable {
    private int id;
    private String word;
    private String desc;

    public KamusItems(String word, String desc){
        this.word = word;
        this.desc = desc;
    }

    public KamusItems(int id, String word, String desc){
        this.id = id;
        this.word = word;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.desc);
    }

    public KamusItems() {
    }

    protected KamusItems(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.desc = in.readString();
    }

    public static final Creator<KamusItems> CREATOR = new Creator<KamusItems>() {
        @Override
        public KamusItems createFromParcel(Parcel source) {
            return new KamusItems(source);
        }

        @Override
        public KamusItems[] newArray(int size) {
            return new KamusItems[size];
        }
    };
}
