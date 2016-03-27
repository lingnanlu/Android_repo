package io.github.lingnanlu.clientserverdemo;

/**
 * Created by Administrator on 2016/3/27.
 */
public class PersonNotParcelable {

    public String firstname;
    public String lastname;

    public PersonNotParcelable(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
