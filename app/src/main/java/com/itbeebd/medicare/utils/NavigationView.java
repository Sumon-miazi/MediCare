package com.itbeebd.medicare.utils;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.itbeebd.medicare.DashBoardActivity;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.userProfile.UserProfileActivity;

public class NavigationView extends AppCompatActivity implements BubbleNavigationChangeListener {
    private BubbleNavigationLinearView bubbleNavigation;

    protected void onCreateDrawer() {
        System.out.println(">>>>>>>onCreateDrawer " + this.getClass().getSimpleName());

        bubbleNavigation = findViewById(R.id.bottom_navigation_view_linear);
        //bubbleNavigation.setCurrentActiveItem(2);
        bubbleNavigation.setNavigationChangeListener(this);
        setActiveItem();
    }

    private void setActiveItem() {
        System.out.println(">>>>>>>>.. current active " + bubbleNavigation.getCurrentActiveItemPosition());
        System.out.println(">>>>>>>>.. current activity " + this.getClass().getSimpleName());
        switch (this.getClass().getSimpleName()) {
            case "DashBoardActivity":
                bubbleNavigation.setCurrentActiveItem(0);
                break;

            case "UserProfileActivity":
                bubbleNavigation.setCurrentActiveItem(4);
                break;
        }
    }

    @Override
    public void onNavigationChanged(View view, int position) {
        //navigation changed, do something
        bubbleNavigation.setCurrentActiveItem(position);
        System.out.println(">>>>>>>>> clicked " + view.getId() + "     " + position);

        Intent intent = null;

        switch (view.getId()) {
            case R.id.l_item_home:
                System.out.println(">>>>>>>>>home");
                intent = new Intent(this, DashBoardActivity.class);
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
                System.out.println(">>>>>>>>>profile");
                intent = new Intent(this, UserProfileActivity.class);
                break;
        }

        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}