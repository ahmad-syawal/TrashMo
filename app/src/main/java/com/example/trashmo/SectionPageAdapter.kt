package com.example.trashmo

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPageAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
//        if (position == 1) {
//            val fragment = MenungguFragment()
//            return fragment
         if (position == 1) {
            val fragment = DiprosesFragment()
            return fragment
        } else if (position == 2) {
            val fragment = SelesaiFragment()
            return fragment
        } else if (position == 3){
            return DibatalkanFragment()
        } else {
            val fragment = MenungguFragment()
            return fragment
        }
    }
}