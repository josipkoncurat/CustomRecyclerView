package objavi.samo.android.customrecyclerview;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

@Database(entities = {Element.class}, version = 1)
public abstract class ElementDatabase extends RoomDatabase {

    private static ElementDatabase instance;

    public abstract ElementDao elementDao();

    public static synchronized ElementDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ElementDatabase.class, "element_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ElementDao elementDao;

        private PopulateDbAsyncTask(ElementDatabase db){
            elementDao = db.elementDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            final List<Element> items = DataGenerator.getElementData();
            for (Element element : items) {
                elementDao.insert(element);
            }
            return null;
        }
    }

}
