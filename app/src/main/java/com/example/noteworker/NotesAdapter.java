package com.example.noteworker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {


    private List<Note> data;

    public NotesAdapter() {
    }

    public NotesAdapter(List<Note> data) {
        this.data = data;
    }

    public void setData(List<Note> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.nameText.setText(data.get(position).getName());
       holder.descriptionText.setText(data.get(position).getDescription());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
       holder.dateText.setText(data.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
  class ViewHolder extends RecyclerView.ViewHolder{


     private TextView nameText;
     private TextView descriptionText;
     private TextView dateText;
     private ImageButton deleteBtn;

     public ViewHolder(@NonNull View itemView) {
         super(itemView);
         nameText = itemView.findViewById(R.id.item_note_name);
         descriptionText = itemView.findViewById(R.id.item_note_description);
         dateText = itemView.findViewById(R.id.item_note_date);
         deleteBtn = itemView.findViewById(R.id.item_note_delete_btn);



     }
 }


}



























