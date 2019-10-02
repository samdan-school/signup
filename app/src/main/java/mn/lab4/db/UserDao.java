package mn.lab4.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;

@Dao
public interface UserDao {
    @Insert
    public abstract void insertUser(User user);

    @Query("SELECT * FROM user WHERE name IN (:userName) LIMIT 1")
    LiveData<User> findByName(String userName);

    @Update
    void UpdateUser(String name, int age, String sex, int phoneNumber, Date dob);
}
