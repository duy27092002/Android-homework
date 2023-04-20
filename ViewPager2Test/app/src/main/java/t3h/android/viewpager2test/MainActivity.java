package t3h.android.viewpager2test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tham chieu den viewpager trong giao dien
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);

        // tao danh sach fragment
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FirstFragment());
        fragmentList.add(new SecondFragment());
        fragmentList.add(new ThirdFragment());

        // tao ra adapter
        FragmentAdapter fragmentAdapter =
                new FragmentAdapter(MainActivity.this, fragmentList);

        // set adapter cho viewpager2
        viewPager2.setAdapter(fragmentAdapter);

        // TH1: vuot doc
        //viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        // Tablayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // set cách hiển thị cho các tab
        tabLayout.setTabMode(TabLayout.MODE_AUTO);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(String.valueOf(position + 1));
            }
        }).attach();
    }
}