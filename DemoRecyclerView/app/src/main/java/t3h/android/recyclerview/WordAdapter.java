package t3h.android.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Kết nối dữ liệu vào RecyclerView
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private final LinkedList<String> mWordList;

    // To create a View for a list item
    // the WordAdapter needs to inflate the XML for a list item
    // Đối tượng LayoutInflater được sử dụng để inflate (tạo ra) các
    // đối tượng View từ các tập tin layout XML
    private LayoutInflater mInflater;

    // Tạo LayoutInflater và lấy dữ liệu được đưa vào để chuẩn bị xử lý
    public WordAdapter(Context context, LinkedList<String> wordList) {
        // khởi tạo LayoutInflater từ đối tượng Context cho Activity|Fragment hiện tại
        mInflater = LayoutInflater.from(context);
        // gắn dữ liệu được đưa vào
        this.mWordList = wordList;
    }

    // Inflates the item layout, and returns a ViewHolder with the layout and the adapter
    // Được gọi bởi RecyclerView để tạo ra obj ViewHolder cho 1 item trong ds
    // Mỗi khi RecyclerView hiển thị 1 item trong ds, method này sẽ được gọi để tạo
    // ra 1 obj ViewHolder mới
    // ViewGroup parent: là ViewGroup chứa view cần được hiển thị bởi ViewHolder
    // thường được sử dụng để xác định cách thức hiển thị layout cho các ViewHolder
    // ---> layout manager
    // int viewType: là kiểu của ViewHolder muốn tạo ra
    // giúp RecyclerView biết ViewHolder nào cần được tạo ra tại thời điểm hiện tại
    // nếu chỉ có 1 loại ViewHolder thì bỏ qua tham số này bằng các set = false
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // tạo ra đối tượng view cho item từ word_item layout
        View mItemView = mInflater.inflate(R.layout.word_item, parent, false);
        // Trả về một đối tượng ViewHolder chứa đối tượng View này (view của item)
        return new WordViewHolder(mItemView, this);
    }

    // đổ data vào ViewHolder đã được tạo từ onCreateViewHolder tại vị trí hiện tại
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        // lấy dữ liệu ở vị trí hiện tại
        String mCurrent = mWordList.get(position);
        // đổ dữ liệu vào TextView trong ViewHolder
        holder.wordItemView.setText(mCurrent);
    }

    // tính tổng số phần tử của danh sách
    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    // Là lớp đại diện cho các thành phần giao diện của 1 item trong ds
    // giúp tối ưu hóa việc hiển thị ds bằng cách lưu trữ các tham chiếu đến
    // các thành phần giao diện để tránh việc tìm lại chúng trong quá trình cuộn ds
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Thành phần giao diện của item
        // trong case này chỉ có text
        public final TextView wordItemView;
        final WordAdapter mAdapter;

        public WordViewHolder(View itemView, WordAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word_item);
            // liên kết với adapter
            this.mAdapter = adapter;
            // thêm sự kiện click cho phần tử trong danh sách
            itemView.setOnClickListener(this);
        }

        // Xử lý sự kiện click các phần tử trong danh sách
        @Override
        public void onClick(View view) {
            // Lấy vị trí item được click
            int mPosition = getLayoutPosition();
            // Sử dụng vị trí đó để truy cập vào item trong danh sách
            String element = mWordList.get(mPosition);
            // Thay đổi text
            mWordList.set(mPosition, "Clicked! " + element);
            // Thông báo cho adapter biết dữ liệu đã thay đổi để nó có thể cập nhật
            // RecyclerView để hiển thị dữ liệu
            mAdapter.notifyDataSetChanged();
        }
    }
}
