package com.example.noteworker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private static RecyclerView listNotes;
    private static List<Note> notes;
    private static NotesAdapter adapter;
    public static View.OnClickListener myOnClickListener;
    private static DBHelper db;
    private Gson gson = new Gson();
    private static final int REQUEST_ACCESS_TYPE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn=findViewById(R.id.add_btn);
        listNotes=findViewById(R.id.notes_list);
        listNotes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        db=new DBHelper(this);
        myOnClickListener=new MyOnClickListener(this);



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FillCardActivity.class);
                intent.putExtra("message","message");
                startActivityForResult(intent,REQUEST_ACCESS_TYPE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ACCESS_TYPE) {
            if (resultCode == RESULT_OK) {
                String name=data.getStringExtra("name");
                String desc=data.getStringExtra("description");
                Note note=new Note(name,desc,new Date().toString());
                db.insert(note);

                adapter.notifyDataSetChanged();
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes=db.getAllNotes();
        adapter = new NotesAdapter(notes);
        listNotes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public static void Update()
    {
        notes=db.getAllNotes();
        adapter = new NotesAdapter(notes);
        listNotes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private class MyOnClickListener implements View.OnClickListener {
        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View view) {
            try {
                int selectedItemPosition = listNotes.getChildPosition(view);
                RecyclerView.ViewHolder viewHolder = listNotes.findViewHolderForPosition(selectedItemPosition);
                Note note = MainActivity.notes.get(selectedItemPosition); // получить note нажатого
                Intent intent = new Intent(context, FillCardActivity.class);
                intent.putExtra("note", gson.toJson(note));
                adapter.notifyDataSetChanged();
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
