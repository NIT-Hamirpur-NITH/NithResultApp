package com.tominc.resultapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 8/6/2016.
 */
public class HistoryModel implements Parcelable {

    private String title;
    private String parameter;
    private String created_on;
    private String target;

    public HistoryModel(String title, String parameter, String created_on, String target) {
        this.title = title;
        this.parameter = parameter;
        this.created_on = created_on;
        this.target = target;
    }

    protected HistoryModel(Parcel in) {
        title = in.readString();
        parameter = in.readString();
        created_on = in.readString();
        target = in.readString();
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(parameter);
        parcel.writeString(created_on);
        parcel.writeString(target);
    }
}
