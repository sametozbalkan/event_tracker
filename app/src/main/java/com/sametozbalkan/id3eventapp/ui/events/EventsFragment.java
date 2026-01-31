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
import com.sametozbalkan.id3eventapp.ui.detail.EventDetailActivity;

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

    private void setupChips() {

        binding.chipAll.setOnClickListener(v -> {
            binding.chipAll.setChecked(true);
            viewModel.showAll();
        });

        binding.chipToday.setOnClickListener(v -> {
            binding.chipToday.setChecked(true);
            viewModel.filterByType(EventType.TODAY);
        });

        binding.chipUpcoming.setOnClickListener(v -> {
            binding.chipUpcoming.setChecked(true);
            viewModel.filterByType(EventType.UPCOMING);
        });

        binding.chipPast.setOnClickListener(v -> {
            binding.chipPast.setChecked(true);
            viewModel.filterByType(EventType.PAST);
        });
    }
}
