package objavi.samo.android.customrecyclerview;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ElementDao {

    @Insert
    void insert(Element element);

    @Update
    void update(Element element);

    @Delete
    void delete(Element element);

    @Query("DELETE FROM element_table")
    void deleteAllElements();

    @Query("SELECT * FROM element_table ORDER BY id DESC")
    LiveData<List<Element>> getAllElements();
}
