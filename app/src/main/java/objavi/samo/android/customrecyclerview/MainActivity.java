package objavi.samo.android.customrecyclerview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_ELEMENT_REQUEST = 1;
    public static final int EDIT_ELEMENT_REQUEST = 2;

    private ElementViewModel elementViewModel;

    private View parent_view;
    private Button btn_add;

    private RecyclerView recyclerView;
    private CustomElementAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent_view = findViewById(android.R.id.content);

        initComponent();

        elementViewModel = ViewModelProviders.of(this).get(ElementViewModel.class);
        elementViewModel.getAllElements().observe(this, new Observer<List<Element>>() {
            @Override
            public void onChanged(@Nullable List<Element> elements) {
                adapter.setElements(elements);
            }
        });

    }
    private void initComponent() {
        recyclerView = findViewById(R.id.recyclerView);
        btn_add = findViewById(R.id.btn_add);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        adapter = new CustomElementAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.setDragListener(new CustomElementAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });
        adapter.setOnItemClickListener(new CustomElementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Element obj) {
                Intent intent = new Intent(MainActivity.this, AddEditElementActivity.class);
                intent.putExtra(AddEditElementActivity.EXTRA_ID, obj.getId());
                intent.putExtra(AddEditElementActivity.EXTRA_NAZIV, obj.getNaziv());
                intent.putExtra(AddEditElementActivity.EXTRA_POCETAK, obj.getPocetak());
                intent.putExtra(AddEditElementActivity.EXTRA_KRAJ, obj.getKraj());
                intent.putExtra(AddEditElementActivity.EXTRA_TAG, obj.getTag());

                startActivityForResult(intent, EDIT_ELEMENT_REQUEST);

            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditElementActivity.class);
                startActivityForResult(intent, ADD_ELEMENT_REQUEST);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                elementViewModel.delete(adapter.getElementAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Element deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        ItemTouchHelper.Callback callback = new DragItemTouchHelper(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_ELEMENT_REQUEST && resultCode == RESULT_OK){
            String naziv = data.getStringExtra(AddEditElementActivity.EXTRA_NAZIV);
            long pocetak = data.getLongExtra(AddEditElementActivity.EXTRA_POCETAK, 0);
            long kraj = data.getLongExtra(AddEditElementActivity.EXTRA_KRAJ, 0);
            String tag = data.getStringExtra(AddEditElementActivity.EXTRA_TAG);


            Element element = new Element(naziv, pocetak, kraj, tag);
            elementViewModel.insert(element); //inserting element into the database

            Toast.makeText(this, "Element saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_ELEMENT_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditElementActivity.EXTRA_ID, -1);
            
            if(id == -1) {
                Toast.makeText(this, "Element can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String naziv = data.getStringExtra(AddEditElementActivity.EXTRA_NAZIV);
            long pocetak = data.getLongExtra(AddEditElementActivity.EXTRA_POCETAK, 0);
            long kraj = data.getLongExtra(AddEditElementActivity.EXTRA_KRAJ, 0);
            String tag = data.getStringExtra(AddEditElementActivity.EXTRA_TAG);
            Element element = new Element(naziv, pocetak, kraj, tag);
            element.setId(id);
            elementViewModel.update(element);
            Toast.makeText(this, "Element updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Element not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
