package th.ac.kku.cis.lab.todoapplication;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@androidx.room.Dao
public interface Dao {
    @Insert(onConflict = REPLACE)
    void insert(Data_activity dataActivity);

    @Delete
    void delete(Data_activity dataActivity);

    @Query("UPDATE Data SET text =:sText WHERE ID = :sID")
    void update(int sID,String sText);

    @Query("SELECT * FROM Data")
    List<Data_activity> getAll();

    void upate(int sID, String uText);
}
