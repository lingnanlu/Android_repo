package io.github.lingnanlu.gaoxiaolian.core;

import com.avos.avoscloud.AVException;

/**
 * Created by rico on 5/15/2016.
 */
public interface CallBack<T> {

    void onResult(T result);
    void onError(AVException e);

}
