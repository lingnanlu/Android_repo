package io.github.lingnanlu.clientserverdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/27.
 */
public class CustomRect implements Parcelable {

    public int left;
    public int top;
    public int right;
    public int bottom;

    public static final Parcelable.Creator<CustomRect> CREATOR = new Parcelable
            .Creator<CustomRect>() {

        @Override
        public CustomRect createFromParcel(Parcel source) {
            return new CustomRect(source);
        }

        @Override
        public CustomRect[] newArray(int size) {
            return new CustomRect[size];
        }
    };

    public CustomRect() {
    }

    public CustomRect(Parcel in) {
        readFromParcel(in);
    }


    public void readFromParcel(Parcel in) {
        left = in.readInt();
        top = in.readInt();
        right = in.readInt();
        bottom = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(left);
        dest.writeInt(top);
        dest.writeInt(right);
        dest.writeInt(bottom);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
