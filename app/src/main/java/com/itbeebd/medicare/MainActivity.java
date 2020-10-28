package com.itbeebd.medicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.itbeebd.medicare.userProfile.UserProfileActivity;

public class MainActivity extends AppCompatActivity implements BubbleNavigationChangeListener {

    private BubbleNavigationLinearView bubbleNavigation;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bubbleNavigation = findViewById(R.id.bottom_navigation_view_linear);
        //bubbleNavigation.setCurrentActiveItem(2);
        bubbleNavigation.setNavigationChangeListener(this);

        // get fragment manager
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerId, new DashBoardActivity());
        fragmentTransaction.commit();

/*
// replace
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.your_placehodler, new YourFragment());
        ft.commit();

// remove
        Fragment fragment = fm.findFragmentById(R.id.your_placehodler);
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();

 */
    }

    @Override
    public void onNavigationChanged(View view, int position) {
        //navigation changed, do something
       // bubbleNavigation.setCurrentActiveItem(position);
        System.out.println(">>>>>>>>> clicked " + view.getId() + "     " + position);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.l_item_home:
                fragmentTransaction.replace(R.id.fragmentContainerId, new DashBoardActivity());
                fragmentTransaction.commit();
                break;

            case R.id.l_item_search:
                System.out.println(">>>>>>>>>search");
                break;

            case R.id.l_item_profile_list:
                System.out.println(">>>>>>>>>love");
                break;

            case R.id.l_item_notification:
                System.out.println(">>>>>>>>>notification");
                break;

            case R.id.l_item_profile:
                fragmentTransaction.replace(R.id.fragmentContainerId, new UserProfileActivity());
                fragmentTransaction.commit();
                break;
        }
    }
}