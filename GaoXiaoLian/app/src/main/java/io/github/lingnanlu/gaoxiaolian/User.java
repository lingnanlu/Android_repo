package io.github.lingnanlu.gaoxiaolian;

import com.avos.avoscloud.AVUser;

import java.io.File;
import java.util.Date;

/**
 * Created by rico on 5/2/2016.
 */
public class User extends AVUser {

    public static final Creator CREATOR = AVObjectCreator.instance;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String SEX = "sex"; //"female", "male"
    public static final String SCHOOL = "school";
    public static final String STATUS = "status"; //"undergraduate", "graduate", "worked"
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
    public static final String VISIT_NUM = "visit_num";     //value is integer
    public static final String LIKE_NUM = "like_num";       //value is integer


    //父类会再次调用子类的put, 导致stackoverflow, 所以该方法暂时不行

//    @Override
//    public void put(String key, Object value) {
//
//        if (key.equals(USERNAME)) {
//            //this.setUsername((String)value);
//            super.setUsername((String)value);
//            return;
//        }
//
//        if (key.equals(PASSWORD)) {
//           // this.setPassword((String) value);
//            super.setPassword((String) value);
//            return;
//        }
//
//        if (key.equals(EMAIL)) {
//            //this.setEmail((String) value);
//            super.setEmail((String) value);
//            return;
//        }
//
//
//        super.put(key, value);
//    }


    public void set(String key, Object value) {

        if (key.equals(USERNAME)) {
            this.setUsername((String)value);
            return;
        }

        if (key.equals(PASSWORD)) {
            this.setPassword((String) value);
            return;
        }

        if (key.equals(EMAIL)) {
            this.setEmail((String) value);
            return;
        }

        super.put(key, value);
    }
}
