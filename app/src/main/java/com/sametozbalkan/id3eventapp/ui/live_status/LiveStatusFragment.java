package com.sametozbalkan.id3eventapp.ui.live_status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sametozbalkan.id3eventapp.R;
import com.sametozbalkan.id3eventapp.databinding.FragmentLiveStatusBinding;

public class LiveStatusFragment extends Fragment {

    private FragmentLiveStatusBinding binding;
    private LiveStatusViewModel viewModel;

    public LiveStatusFragment() {
        super(R.layout.fragment_live_status);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLiveStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this)
                .get(LiveStatusViewModel.class);

        observeState();

        binding.btnRefresh.setOnClickListener(v ->
                viewModel.refreshNow()
        );
    }

    private void observeState() {
        viewModel.getStatusText().observe(
                getViewLifecycleOwner(),
                text -> binding.txtStatusTitle.setText(text)
        );

        viewModel.getCountdown().observe(
                getViewLifecycleOwner(),
                seconds ->
                        binding.txtNextUpdate.setText(
                                "Next update in " + seconds + "s"
                        )
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
