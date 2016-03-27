package io.github.lingnanlu.clientserverdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/27.
 *
 */
public class Person implements Parcelable{

    public String firstname;
    public String lastname;

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
    }

    public static Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

        @Override
        public Person createFromParcel(Parcel source) {
            String firstname = source.readString();
            String lastname = source.readString();
            return new Person(firstname, lastname);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
