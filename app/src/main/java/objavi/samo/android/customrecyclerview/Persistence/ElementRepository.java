package objavi.samo.android.customrecyclerview.Persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import objavi.samo.android.customrecyclerview.Model.Element;

public class ElementRepository {

    private ElementDao elementDao;
    private LiveData<List<Element>> allElements;

    public ElementRepository(Application application) {
        ElementDatabase database = ElementDatabase.getInstance(application);
        elementDao = database.elementDao();
        allElements = elementDao.getAllElements();
    }

    public void insert(Element element) {
        new InsertElementAsyncTask(elementDao).execute(element);
    }

    public void update(Element element) {
        new UpdateElementAsyncTask(elementDao).execute(element);
    }

    public void delete(Element element) {
        new DeleteElementAsyncTask(elementDao).execute(element);
    }

    public void deleteAllElements() {
        new DeleteAllElementAsyncTask(elementDao).execute();
    }

    public LiveData<List<Element>> getAllElements() {
        return allElements;
    }

    private static class InsertElementAsyncTask extends AsyncTask<Element, Void, Void> {
        private ElementDao elementDao;

        private InsertElementAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Element... elements) {
            elementDao.insert(elements[0]);
            return null;
        }
    }

    private static class UpdateElementAsyncTask extends AsyncTask<Element, Void, Void> {
        private ElementDao elementDao;

        private UpdateElementAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Element... elements) {
            elementDao.update(elements[0]);
            return null;
        }
    }

    private static class DeleteElementAsyncTask extends AsyncTask<Element, Void, Void> {
        private ElementDao elementDao;

        private DeleteElementAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Element... elements) {
            elementDao.delete(elements[0]);
            return null;
        }
    }

    private static class DeleteAllElementAsyncTask extends AsyncTask<Void, Void, Void> {
        private ElementDao elementDao;

        private DeleteAllElementAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            elementDao.deleteAllElements();
            return null;
        }
    }
}
