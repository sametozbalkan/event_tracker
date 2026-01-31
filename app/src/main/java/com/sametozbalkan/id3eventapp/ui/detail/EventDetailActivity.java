package com.sametozbalkan.id3eventapp.ui.detail;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sametozbalkan.id3eventapp.databinding.FragmentEventDetailBinding;

public class EventDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT_ID = "extra_event_id";
    private FragmentEventDetailBinding binding;
    private EventDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = FragmentEventDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this)
                .get(EventDetailViewModel.class);

        long eventId = getIntent().getLongExtra(EXTRA_EVENT_ID, -1);
        if (eventId == -1) {
            finish();
            return;
        }

        viewModel.setEventId(eventId);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        setupTabs();
    }

    private void setupTabs() {
        EventDetailPagerAdapter adapter = new EventDetailPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(
                binding.tabLayout,
                binding.viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText("Participants"); break;
                        case 1: tab.setText("Comments"); break;
                        case 2: tab.setText("Live Status"); break;
                    }
                }
        ).attach();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateHeader(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        updateHeader(0);
    }

    private void updateHeader(int position) {
        switch (position) {
            case 0:
                binding.txtHeaderTitle.setText("Participants");
                binding.txtHeaderSubtitle.setText(
                        "Meet the people attending this event"
                );
                break;

            case 1:
                binding.txtHeaderTitle.setText("Comments");
                binding.txtHeaderSubtitle.setText(
                        "Join the discussion and share your thoughts"
                );
                break;

            case 2:
                binding.txtHeaderTitle.setText("Live Status");
                binding.txtHeaderSubtitle.setText(
                        "Live updates from the event"
                );
                break;
        }
    }
}

