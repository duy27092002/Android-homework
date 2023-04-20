package t3h.android.demofragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {
    // không khuyến khích dùng cách này
//    public FirstFragment(){
//        super(R.layout.first_fragment);
//    }

    // tạo view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.first_fragment, container, false);
        return root;
    }

    // khi view đã được tạo
    // tham số view chính là root, chính là first_fragment
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.textView);
        // lấy dữ liệu ban đầu
        Bundle agrs = requireArguments();
        String getMessage = agrs.getString("MESSAGE_KEY", "Hello First Fragment");
        textView.setText(getMessage);
    }
}
