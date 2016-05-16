package io.github.lingnanlu.gaoxiaolian.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by rico on 5/14/2016.
 * 这里的Bubble Time用来对在线用户的冒泡时间进行排序, 每有一个Online对象表示一个用户在线
 */

@AVClassName("Online")
public class Online extends AVObject{

    public static final String USER = "user";           //冒泡用户
    public static final String BUBBLE_TIME = "date";        //冒泡时间

}
