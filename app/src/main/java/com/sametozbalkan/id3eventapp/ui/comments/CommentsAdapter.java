package com.sametozbalkan.id3eventapp.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sametozbalkan.id3eventapp.data.model.Comment;
import com.sametozbalkan.id3eventapp.databinding.ItemCommentBinding;

import java.util.List;

public class CommentsAdapter
        extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private final List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CommentViewHolder holder,
            int position
    ) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void updateData(List<Comment> newComments) {
        comments.clear();
        comments.addAll(newComments);
        notifyDataSetChanged();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        private final ItemCommentBinding binding;

        CommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Comment comment) {
            binding.txtUsername.setText(comment.getUsername());
            binding.txtMessage.setText(comment.getMessage());
            binding.txtTime.setText(comment.getTime());
            binding.txtLikeCount.setText(String.valueOf(comment.getLikeCount()));

            Glide.with(binding.imgAvatar.getContext())
                    .load("https://i.pravatar.cc/150?u=" + comment.getUsername())
                    .circleCrop()
                    .into(binding.imgAvatar);

            Comment reply = comment.getReply();
            if (reply == null) {
                binding.replyContainer.setVisibility(View.GONE);
            } else {
                binding.replyContainer.setVisibility(View.VISIBLE);
                binding.txtReplyUsername.setText(reply.getUsername());
                binding.txtReplyTime.setText(reply.getTime());
                binding.txtReplyMessage.setText(reply.getMessage());
                Glide.with(binding.imgAvatar.getContext())
                        .load("https://i.pravatar.cc/150?u=" + comment.getUsername())
                        .circleCrop()
                        .into(binding.imgAvatar);
            }
        }
    }
}

