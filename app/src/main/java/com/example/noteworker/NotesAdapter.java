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


    private DBHelper dbHelper;
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
        dbHelper = new DBHelper(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_note, parent, false);
        view.setOnClickListener(MainActivity.myOnClickListener);
        NotesAdapter.ViewHolder myViewHolder = new NotesAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name = data.get(position).getName();
        if (name.length()>18)
            name=name.substring(0,18)+"...";
       holder.nameText.setText(name);

       String description = data.get(position).getDescription();
       if (description.length()>11)
           description = description.substring(0,11)+"...";
       holder.descriptionText.setText(description);

        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
       holder.dateText.setText(data.get(position).getDate());
    holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbHelper.deleteNote(data.get(position));
            MainActivity.Update();
        }
    });
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



























