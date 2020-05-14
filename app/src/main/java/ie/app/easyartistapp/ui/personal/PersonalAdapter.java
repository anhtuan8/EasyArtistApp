package ie.app.easyartistapp.ui.personal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import ie.app.easyartistapp.ui.personal.favourite.PersonalFavouriteFragment;

public class PersonalAdapter extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PersonalAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: {
                Fragment fragment = new PersonalFavouriteFragment();
                return fragment;
            }
            default:{
                Fragment fragment = new DemoPersonalFragment();
                Bundle args = new Bundle();
                args.putInt(DemoPersonalFragment.ARG_OBJECT, position+1);
                fragment.setArguments(args);
                return fragment;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
