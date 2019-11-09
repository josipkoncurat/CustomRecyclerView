package objavi.samo.android.customrecyclerview.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import objavi.samo.android.customrecyclerview.R;
import objavi.samo.android.customrecyclerview.Util.Tools;

public class AddEditElementActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ID =
            "objavi.samo.android.customrecyclerview.EXTRA_ID";
    public static final String EXTRA_NAZIV =
            "objavi.samo.android.customrecyclerview.EXTRA_NAZIV";
    public static final String EXTRA_POCETAK =
            "objavi.samo.android.customrecyclerview.EXTRA_POCETAK";
    public static final String EXTRA_KRAJ =
            "objavi.samo.android.customrecyclerview.EXTRA_KRAJ";
    public static final String EXTRA_TAG =
            "objavi.samo.android.customrecyclerview.EXTRA_TAG";

    private EditText editTextNaziv;
    private Button btnPocetak;
    private Button btnKraj;
    private EditText editTextTag;

    private String naziv;
    private long pocetak;
    private long kraj;
    private String tag;

    private boolean dateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_element);

        editTextNaziv = findViewById(R.id.edit_text_naziv);
        btnPocetak = findViewById(R.id.btn_pocetak);
        btnKraj = findViewById(R.id.btn_kraj);
        editTextTag = findViewById(R.id.edit_text_tag);

        btnPocetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFlag = true;
                showDatePickerDialog();

            }
        });

        btnKraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFlag = false;
                showDatePickerDialog();

            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Element");
            editTextNaziv.setText(intent.getStringExtra(EXTRA_NAZIV));
            btnPocetak.setText(Tools.getFormattedDateSimple(intent.getLongExtra(EXTRA_POCETAK,-2)));
            pocetak = intent.getLongExtra(EXTRA_POCETAK,-2);
            kraj = intent.getLongExtra(EXTRA_KRAJ,-2);
            btnKraj.setText(Tools.getFormattedDateSimple(intent.getLongExtra(EXTRA_KRAJ,-2)));
            btnKraj.setText(Tools.getFormattedDateSimple(intent.getLongExtra(EXTRA_KRAJ,-2)));
            editTextTag.setText(intent.getStringExtra(EXTRA_TAG));
        } else {
            setTitle("Add Element");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_element_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveElement();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveElement() {
        naziv = editTextNaziv.getText().toString();
        tag = editTextTag.getText().toString();

        if (naziv.trim().isEmpty() || pocetak == -2 || kraj == -2 || tag.trim().isEmpty()){
            Toast.makeText(this, "Please insert the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_NAZIV, naziv);
        data.putExtra(EXTRA_POCETAK, pocetak);
        data.putExtra(EXTRA_KRAJ, kraj);
        data.putExtra(EXTRA_TAG, tag);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        long date_ship_millis = calendar.getTimeInMillis();

        if(dateFlag){
            btnPocetak.setText(Tools.getFormattedDateSimple(date_ship_millis));
            pocetak = date_ship_millis;
        }else{
            btnKraj.setText(Tools.getFormattedDateSimple(date_ship_millis));
            kraj = date_ship_millis;
        }
    }
    private void showDatePickerDialog(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
