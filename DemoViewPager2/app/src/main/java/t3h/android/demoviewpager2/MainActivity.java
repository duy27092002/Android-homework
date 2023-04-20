package t3h.android.demoviewpager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tạo viewpager2
        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);

        // tạo danh sách fragment
        fragmentList = new ArrayList<>();
        fragmentList.add(new BlankFragment());
        fragmentList.add(new HelloFragment());
        fragmentList.add(new GoodByeFragment());

        // tạo apdater
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this, fragmentList);

        // setAdapter cho viewpager2
        viewPager2.setAdapter(fragmentAdapter);

        // vuốt theo chiều dọc
        //viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        // smoothScroll là hiệu ứng cuộn. true là có hiệu ứng, false là không
        //viewPager2.setCurrentItem(4, false);

        // tạo tablayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // set cách hiển thị cho các tab
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // set text cho các tab
                tab.setText(String.valueOf(position + 1));
            }
        }).attach();
        // attach() để liên kết tab và viewpager với nhau
    }
}