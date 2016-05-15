package io.github.lingnanlu.gaoxiaolian.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by rico on 5/14/2016.
 */

@AVClassName("Like")
public class Like extends AVObject{

    public static final String FROM = "from";      //点赞者
    public static final String TO = "to";          //被赞者
}
