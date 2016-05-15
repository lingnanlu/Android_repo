package io.github.lingnanlu.gaoxiaolian.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by rico on 5/14/2016.
 */
@AVClassName("Visit")
public class Visit extends AVObject{

    public static final String FROM = "from";   //访问者
    public static final String TO = "to";       //被访问者

}
