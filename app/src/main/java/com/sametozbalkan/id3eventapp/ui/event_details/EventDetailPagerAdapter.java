package com.sametozbalkan.id3eventapp.ui.event_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sametozbalkan.id3eventapp.ui.comments.CommentsFragment;
import com.sametozbalkan.id3eventapp.ui.live_status.LiveStatusFragment;
import com.sametozbalkan.id3eventapp.ui.participants.ParticipantsFragment;

public class EventDetailPagerAdapter extends FragmentStateAdapter {

    public EventDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ParticipantsFragment();
            case 1:
                return new CommentsFragment();
            case 2:
                return new LiveStatusFragment();
            default:
                throw new IllegalStateException("Unexpected position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

