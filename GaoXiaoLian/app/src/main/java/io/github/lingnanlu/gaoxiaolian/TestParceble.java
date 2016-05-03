package io.github.lingnanlu.gaoxiaolian;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/3.
 */
public class TestParceble implements Parcelable {


    public TestParceble(Parcel in) {
    }

    public static final Creator<TestParceble> CREATOR = new Creator<TestParceble>() {
        @Override
        public TestParceble createFromParcel(Parcel in) {
            return new TestParceble(in);
        }

        @Override
        public TestParceble[] newArray(int size) {
            return new TestParceble[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
