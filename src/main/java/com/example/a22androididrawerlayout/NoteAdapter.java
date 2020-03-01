package com.example.a22androididrawerlayout;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a22androididrawerlayout.Database.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder>
{
    private List<Note> noteList = new ArrayList<>();
    private OnItemClickListener mListener;
    ViewGroup parent1;

    public NoteAdapter()
    {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNameStudent().equals(newItem.getNameStudent())
                    && oldItem.getYearBirth().equals(newItem.getYearBirth())
                    && oldItem.getHomeTown().equals(newItem.getHomeTown())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };
    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void onItemClickUpdate(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        mListener = onItemClickListener;
    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, final int position) {
            Note note = getItem(position);
            holder.text_view_description.setText(String.valueOf(note.getYearBirth()));
            holder.text_view_priority.setText(String.valueOf(note.getPriority()));
            holder.text_view_title.setText(note.getNameStudent());
            holder.tv_hometown.setText(note.getHomeTown());
            holder.imv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null)
                    {
                        mListener.onItemClick(position);
                    }
                }
            });
    }

    public void setNotes(List<Note> notes)
    {
        this.noteList = notes;
        notifyDataSetChanged();
    }
    public Note getNoteAt(int position)
    {
        return getItem(position);
    }


    class NoteHolder extends RecyclerView.ViewHolder
    {
        private TextView text_view_priority;
        private TextView text_view_title;
        private TextView text_view_description;
        private TextView tv_hometown;
        private ImageView imv_delete;
        private ImageView imv_update;

        public NoteHolder(View itemView)
        {
            super(itemView);
            text_view_description = itemView.findViewById(R.id.text_view_description);
            text_view_priority = itemView.findViewById(R.id.text_view_priority);
            text_view_title = itemView.findViewById(R.id.text_view_title);
            tv_hometown = itemView.findViewById(R.id.tv_hometown);
            imv_delete = itemView.findViewById(R.id.imv_delete);
            imv_update = itemView.findViewById(R.id.imv_update);
            imv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(mListener!= null && position != RecyclerView.NO_POSITION)
                    {
                        mListener.onItemClickUpdate(getItem(position));
                    }
                }
            });
        }
    }
}
