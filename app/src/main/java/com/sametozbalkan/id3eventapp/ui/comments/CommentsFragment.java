package com.sametozbalkan.id3eventapp.ui.comments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sametozbalkan.id3eventapp.databinding.FragmentCommentsBinding;
import com.sametozbalkan.id3eventapp.ui.event_details.EventDetailViewModel;

import java.util.ArrayList;

public class CommentsFragment extends Fragment {

    private FragmentCommentsBinding binding;
    private CommentsAdapter adapter;
    private EventDetailViewModel viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCommentsBinding.inflate(inflater, container, false);
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

        adapter = new CommentsAdapter(new ArrayList<>());
        binding.rvComments.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        binding.rvComments.setAdapter(adapter);

        viewModel.getComments().observe(
                getViewLifecycleOwner(),
                list -> adapter.updateData(list)
        );

        binding.btnSend.setOnClickListener(v -> {
            String message = binding.edtComment.getText().toString().trim();
            if (message.isEmpty()) return;

            viewModel.addComment(message);
            binding.edtComment.setText("");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
