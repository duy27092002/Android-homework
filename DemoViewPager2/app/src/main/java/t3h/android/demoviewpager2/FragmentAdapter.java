package t3h.android.demoviewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

// vì là hiển thị fragment lên activity nên kế thừa từ FragmentStateAdapter
// nếu chỉ muốn hiển thị view thì có thể kế thừa từ Adapter của RecyclerView
public class FragmentAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        Fragment fragment = new BlankFragment();
//        // tạo dữ liệu
//        Bundle bundle = new Bundle();
//        bundle.putInt("POSITION_KEY", position);
//        // truyền dữ liệu vào fragment
//        fragment.setArguments(bundle);
        return fragmentList.get(position);
    }

    // số lượng fragment muốn hiển thị
    // và tham số position ở method trên: position < getItemCount()
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
