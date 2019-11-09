package objavi.samo.android.customrecyclerview;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "element_table")
public class Element {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String naziv;
    private long pocetak;
    private long kraj;
    private String tag;

    public Element(String naziv, long pocetak, long kraj, String tag) {
        this.naziv = naziv;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.tag = tag;
    }
    public Element() {}

    public Element(String naziv) {
        this.naziv = naziv;
    }


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public long getPocetak() {
        return pocetak;
    }

    public void setPocetak(long pocetak) {
        this.pocetak = pocetak;
    }

    public long getKraj() {
        return kraj;
    }

    public void setKraj(long kraj) {
        this.kraj = kraj;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
