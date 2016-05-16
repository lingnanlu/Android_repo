package io.github.lingnanlu.gaoxiaolian.model;

import android.os.Parcel;

import com.avos.avoscloud.AVUser;

/**
 * Created by rico on 5/2/2016.
 */
public class User extends AVUser {

    public static final Creator<User> CREATOR = new User.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };

    private User(Parcel source) {
        super(source);
    }

    public User() {}

    public static final String SEX = "sex"; //"female", "male"
    public static final String SCHOOL = "school";
    public static final String KIND = "kind"; //"undergraduate", "graduate", "worked"
    public static final String SN = "sn";
    public static final String COLLEGE = "college";
    public static final String HOMETOWN = "hometown"; //"provinence-city"
    public static final String BIRTHDAY = "birthday"; //"yeah-month"
    public static final String HEIGHT = "height";
    public static final String INTEREST = "interest";
    public static final String MOTTO = "motto";
    public static final String BRIEF = "brief";
    public static final String CONTACT = "contact";
    public static final String PRIVATE = "private";

    //表示用户在冒泡时间, 即上线时间
    public static final String BUBBLE_TIME = "bubble_time"; //date类型

    public static final String STATUS = "status";
    public static final int STATUS_OFFLINE = 0;
    public static final int STATUS_ONLINE = 1;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ObjectId:" + getObjectId() + "\n");
        sb.append("UserName:" + getUsername() + "\n");
        sb.append(SEX + ":" + getString(SEX) + "\n");
        return sb.toString();
    }

}
