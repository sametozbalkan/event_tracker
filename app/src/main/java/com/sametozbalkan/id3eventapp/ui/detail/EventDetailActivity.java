package com.sametozbalkan.id3eventapp.ui.detail;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sametozbalkan.id3eventapp.data.model.Event;
import com.sametozbalkan.id3eventapp.databinding.FragmentEventDetailBinding;

public class EventDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT_ID = "extra_event_id";

    private FragmentEventDetailBinding binding;
    private EventDetailViewModel viewModel;
    private Event currentEvent;

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

        ViewCompat.setOnApplyWindowInsetsListener(
                binding.getRoot(),
                (v, insets) -> {
                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        setupTabs();

        viewModel.getEvent().observe(this, event -> {
            if (event == null) return;

            currentEvent = event;
            binding.toolbar.setTitle(event.getTitle());
            updateHeader(binding.viewPager.getCurrentItem());
        });
    }

    private void setupTabs() {
        EventDetailPagerAdapter adapter = new EventDetailPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(
                binding.tabLayout,
                binding.viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("Participants");
                    else if (position == 1) tab.setText("Comments");
                    else tab.setText("Live Status");
                }
        ).attach();

        binding.viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        updateHeader(position);
                    }
                }
        );
    }

    private void updateHeader(int position) {
        if (currentEvent == null) return;

        switch (position) {
            case 0:
                binding.txtHeaderTitle.setText(currentEvent.getTitle());
                binding.txtHeaderSubtitle.setText(
                        currentEvent.getEventDate() + " • " +
                                currentEvent.getDescription()
                );
                break;

            case 1:
                binding.txtHeaderTitle.setText("Comments");
                binding.txtHeaderSubtitle.setText(
                        "Discussion about " + currentEvent.getTitle()
                );
                break;

            case 2:
                binding.txtHeaderTitle.setText(currentEvent.getTitle());
                binding.txtHeaderSubtitle.setText(
                        "Live updates from the event"
                );
                break;
        }
    }
}
