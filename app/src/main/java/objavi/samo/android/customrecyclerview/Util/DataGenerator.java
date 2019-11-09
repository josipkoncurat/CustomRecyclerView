package objavi.samo.android.customrecyclerview.Util;

import java.util.ArrayList;
import java.util.List;

import objavi.samo.android.customrecyclerview.Model.Element;

public class DataGenerator {


    // util method for inserting 24 empty elements
    public static List<Element> getElementData() {
        List<Element> items = new ArrayList<>();

        for (int i = 24; i > 0; i--) {
            Element obj = new Element();
            items.add(obj);
        }
        return items;
    }
}
