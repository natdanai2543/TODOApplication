package th.ac.kku.cis.lab.todoapplication;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Data_activity.class},version = 1,exportSchema = false)

public abstract class DataBase extends RoomDatabase {

    private static DataBase databse;

    private static String DATABASE_NAME="database";

    public  synchronized static DataBase getInstance(Context context){
        if(databse==null){
            databse= Room.databaseBuilder(context.getApplicationContext(), DataBase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return databse;
    }



    public abstract Dao mainDao();

}