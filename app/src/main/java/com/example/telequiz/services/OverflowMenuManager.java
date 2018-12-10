package com.example.telequiz.services;

import android.view.Menu;

import com.example.telequiz.R;

import java.util.ArrayList;

public class OverflowMenuManager {
    ArrayList<Integer> overflowMenuGroupIds = new ArrayList<>();
    Menu menu;

    public OverflowMenuManager(Menu menu) {

        this.menu = menu;

        /*
        * Menu Group
        * */
        overflowMenuGroupIds.add(R.id.main_activity_menu_group);
        overflowMenuGroupIds.add(R.id.creator_studio_menu_group);
        overflowMenuGroupIds.add(R.id.play_quiz_menu_group);

        //Hide all groups at the beginning
        for (int i = 0; i < overflowMenuGroupIds.size(); i++) {
            menu.setGroupVisible(overflowMenuGroupIds.get(i), false);
        }
    }

    public void showGroup(int id) {
        menu.setGroupVisible(id, true);
    }

    public void hideGroup(int id) {
        menu.setGroupVisible(id, false);
    }

    public void showItem(int id) {
        menu.findItem(id).setVisible(true);
    }

    public void hideItem(int id) {
        menu.findItem(id).setVisible(false);
    }
}
