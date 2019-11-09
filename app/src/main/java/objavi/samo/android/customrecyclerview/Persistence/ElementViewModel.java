package objavi.samo.android.customrecyclerview.Persistence;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import objavi.samo.android.customrecyclerview.Model.Element;

public class ElementViewModel extends AndroidViewModel {
    private ElementRepository repository;
    private LiveData<List<Element>> allElements;

    public ElementViewModel(@NonNull Application application) {
        super(application);
        repository = new ElementRepository(application);
        allElements = repository.getAllElements();
    }
    public  void insert(Element element){
        repository.insert(element);
    }
    public void update(Element element) {
        repository.update(element);
    }

    public void delete(Element element) {
        repository.delete(element);
    }

    public void deleteAllElements() {
        repository.deleteAllElements();
    }

    public LiveData<List<Element>> getAllElements() {
        return allElements;
    }
}
