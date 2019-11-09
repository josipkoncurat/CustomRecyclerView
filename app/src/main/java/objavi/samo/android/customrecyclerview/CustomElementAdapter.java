package objavi.samo.android.customrecyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomElementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DragItemTouchHelper.MoveHelperAdapter  {

    private final int limit = 9;
    private List<Element> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnStartDragListener mDragStartListener = null;

    public CustomElementAdapter(Context ctx) {
        this.ctx = ctx;
    }
    public interface OnItemClickListener {
        void onItemClick(Element obj);
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setDragListener(OnStartDragListener dragStartListener) {
        this.mDragStartListener = dragStartListener;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements DragItemTouchHelper.TouchViewHolder {
        public TextView naziv;
        public TextView pocetak;
        public TextView kraj;
        public ImageButton dragIcon;
        public View lyt_parent;


        public OriginalViewHolder(View v) {
            super(v);

            naziv = v.findViewById(R.id.naziv);
            pocetak = v.findViewById(R.id.pocetak);
            kraj = v.findViewById(R.id.kraj);

            lyt_parent = v.findViewById(R.id.lyt_parent);
            dragIcon = v.findViewById(R.id.drag);

        }

        @Override
        public void onItemSelected() {
            //itemView.setBackgroundColor(Color.LTGRAY);
        };

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // max broj itema je 8
        v.getLayoutParams().height = (int) (getScreenHeight()/ limit);
        vh = new OriginalViewHolder(v);
        return vh;
    }
    private int getScreenHeight() {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final Element element = items.get(position);

            view.naziv.setText(element.getNaziv());
            view.pocetak.setText(Tools.getFormattedDateSimple(element.getPocetak()));
            view.kraj.setText(Tools.getFormattedDateSimple(element.getKraj()));

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(element);
                    }
                }
            });

            view.dragIcon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN && mDragStartListener != null) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setElements(List<Element> elements){
        this.items = elements;
        notifyDataSetChanged();
    }
    public Element getElementAt(int position){
        return items.get(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}
