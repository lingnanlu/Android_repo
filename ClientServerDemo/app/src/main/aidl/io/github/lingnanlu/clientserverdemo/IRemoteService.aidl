// IRemoteService.aidl
package io.github.lingnanlu.clientserverdemo;

//不要忘记import, 否则生成IRemoteService.java时会出错
import  io.github.lingnanlu.clientserverdemo.CustomRect;

// Declare any non-default types here with import statements

interface IRemoteService {

    int getPid();

    int calculatePerimeter(in CustomRect customRect);

    /**
     *
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
