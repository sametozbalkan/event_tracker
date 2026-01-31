package com.sametozbalkan.id3eventapp.ui.participants;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sametozbalkan.id3eventapp.data.model.Participant;
import com.sametozbalkan.id3eventapp.databinding.ItemParticipantBinding;

import java.util.List;

public class ParticipantsAdapter
        extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantViewHolder> {

    private final List<Participant> participants;

    public ParticipantsAdapter(List<Participant> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        ItemParticipantBinding binding = ItemParticipantBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ParticipantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ParticipantViewHolder holder,
            int position
    ) {
        holder.bind(participants.get(position));
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public void updateData(List<Participant> newList) {
        participants.clear();
        participants.addAll(newList);
        notifyDataSetChanged();
    }

    static class ParticipantViewHolder extends RecyclerView.ViewHolder {

        private final ItemParticipantBinding binding;

        ParticipantViewHolder(ItemParticipantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Participant participant) {
            binding.txtName.setText(participant.getName());
            binding.txtRole.setText(participant.getRole());

            Glide.with(binding.imgAvatar.getContext())
                    .load(participant.getAvatarUrl())
                    .circleCrop()
                    .into(binding.imgAvatar);
        }
    }
}

