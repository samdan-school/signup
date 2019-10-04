package mn.lab4.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String name;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "sex")
    public String sex;

    @ColumnInfo(name = "phone_number")
    public int phoneNumber;

    @ColumnInfo(name = "dob")
    public Date  dob;

    public User() {

    }
}
