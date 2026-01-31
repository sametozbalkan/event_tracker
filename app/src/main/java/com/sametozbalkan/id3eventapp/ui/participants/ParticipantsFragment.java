package com.sametozbalkan.id3eventapp.ui.participants;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sametozbalkan.id3eventapp.databinding.FragmentParticipantsBinding;
import com.sametozbalkan.id3eventapp.ui.detail.EventDetailViewModel;

import java.util.ArrayList;

public class ParticipantsFragment extends Fragment {

    private FragmentParticipantsBinding binding;
    private ParticipantsAdapter adapter;
    private EventDetailViewModel viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentParticipantsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity())
                .get(EventDetailViewModel.class);

        adapter = new ParticipantsAdapter(new ArrayList<>());
        binding.rvParticipants.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        binding.rvParticipants.setAdapter(adapter);

        viewModel.getParticipants().observe(
                getViewLifecycleOwner(),
                list -> adapter.updateData(list)
        );

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.filterParticipants(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
