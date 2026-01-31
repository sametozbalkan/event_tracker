package com.sametozbalkan.id3eventapp.ui.events;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sametozbalkan.id3eventapp.R;
import com.sametozbalkan.id3eventapp.data.model.Event;
import com.sametozbalkan.id3eventapp.databinding.ItemEventBinding;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    private final List<Event> events;
    private final OnEventClickListener listener;

    public EventsAdapter(List<Event> events, OnEventClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull EventViewHolder holder,
            int position
    ) {
        Event event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateData(List<Event> newEvents) {
        events.clear();
        events.addAll(newEvents);
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private final ItemEventBinding binding;

        EventViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void updateBookmarkIcon(boolean saved) {
            binding.btnBookmark.setImageResource(
                    saved
                            ? R.drawable.ic_bookmark_added
                            : R.drawable.ic_bookmark
            );
        }

        void bind(Event event) {
            binding.txtTitle.setText(event.getTitle());
            binding.txtDescription.setText(event.getDescription());
            binding.txtDate.setText(event.getEventDate());
            binding.txtHoursAgo.setText(event.getHoursAgo());

            Glide.with(binding.imgEvent.getContext())
                    .load(event.getImageUrl())
                    .into(binding.imgEvent);

            updateBookmarkIcon(event.isSaved());

            binding.btnBookmark.setOnClickListener(v -> {
                event.setSaved(!event.isSaved());
                updateBookmarkIcon(event.isSaved());
            });

            binding.getRoot().setOnClickListener(v ->
                    listener.onEventClick(event)
            );
        }
    }
}

