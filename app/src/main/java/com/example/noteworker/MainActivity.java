package com.example.noteworker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private RecyclerView listNotes;
    private List<Note> notes;
    private NotesAdapter adapter;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn=findViewById(R.id.add_btn);
        listNotes=findViewById(R.id.notes_list);
        listNotes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        db=new DBHelper(this);
        notes=db.getAllNotes();
        adapter = new NotesAdapter(notes);
        listNotes.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Note note = new Note("Name_" + new Date().getTime(), "Description.........", new Date().toString());
            db.insert(note);
            notes.add(note);
            adapter.notifyDataSetChanged();
            }
        });
    }
}
