package t3h.android.demointent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// kết nối dữ liệu vào RecyclerView
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<String> stringList;

    public ItemListAdapter() {
        stringList = new ArrayList<>();
    }

    public void setStringList(List<String> list) {
        stringList = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_layout,
                parent,
                false
        );
        return new ItemViewHolder(view);
    }

    // hiển thị dữ liệu cho danh sách
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindView(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    // nắm giữ thông tin của từng item trong danh sách RecyclerView
    // ---> đại diện cho 1 item
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);

            // bắt sự kiện click của item trong danh sách
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(itemView, getAdapterPosition());
                    }
                }
            });
        }

        // hiển thị dữ liệu lên giao diện
        public void bindView(String content) {
            txtTitle.setText(content);
        }
    }
}
