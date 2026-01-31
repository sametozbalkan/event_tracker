package com.sametozbalkan.id3eventapp.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sametozbalkan.id3eventapp.data.model.EventType;
import com.sametozbalkan.id3eventapp.databinding.FragmentEventsBinding;
import com.sametozbalkan.id3eventapp.ui.event_details.EventDetailActivity;

import java.util.ArrayList;

public class EventsFragment extends Fragment {

    private FragmentEventsBinding binding;
    private EventsAdapter adapter;
    private EventsViewModel viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this)
                .get(EventsViewModel.class);

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

        setupRecyclerView();
        setupChips();
        observeEvents();
        observeSelectedFilter();
    }

    private void setupRecyclerView() {
        binding.rvEvents.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        adapter = new EventsAdapter(new ArrayList<>(), event -> {
            Intent intent = new Intent(
                    requireContext(),
                    EventDetailActivity.class
            );
            intent.putExtra(
                    EventDetailActivity.EXTRA_EVENT_ID,
                    event.getId()
            );
            startActivity(intent);
        });

        binding.rvEvents.setAdapter(adapter);
    }

    private void observeEvents() {
        viewModel.getEvents().observe(
                getViewLifecycleOwner(),
                events -> adapter.updateData(events)
        );
    }

    private void observeSelectedFilter() {
        viewModel.getSelectedFilter().observe(
                getViewLifecycleOwner(),
                type -> {
                    binding.chipAll.setChecked(type == null);
                    binding.chipToday.setChecked(type == EventType.TODAY);
                    binding.chipUpcoming.setChecked(type == EventType.UPCOMING);
                    binding.chipPast.setChecked(type == EventType.PAST);
                }
        );
    }

    private void setupChips() {
        binding.chipAll.setOnClickListener(v ->
                viewModel.setFilter(null)
        );

        binding.chipToday.setOnClickListener(v ->
                viewModel.setFilter(EventType.TODAY)
        );

        binding.chipUpcoming.setOnClickListener(v ->
                viewModel.setFilter(EventType.UPCOMING)
        );

        binding.chipPast.setOnClickListener(v ->
                viewModel.setFilter(EventType.PAST)
        );
    }
}
