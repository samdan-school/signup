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
    void insertUser(User user);

    @Query("SELECT * FROM user WHERE name IN (:userName)")
    User findByName(String userName);

    @Query("SELECT * FROM user")
    User[] loadAllUsers();

    @Update
    void updateUser(User user);
}
