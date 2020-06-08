package com.example.noteworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Date;
import java.util.logging.FileHandler;

public class FillCardActivity extends AppCompatActivity {
    private TextInputLayout name, description;
    private Button save;
    private Note mNote;
    private Gson gson = new Gson();
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_card);
        name = findViewById(R.id.name_activity_fil_card);
        description = findViewById(R.id.description_activity_fil_card);
        save = findViewById(R.id.btn_save_note_activity_fil_card);
        final Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            if(!extras.getString("note","").equals("")) {
                init();
                name.getEditText().setText(mNote.getName());
                description.getEditText().setText(mNote.getDescription());
            }

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(extras!=null&&!extras.getString("message","").equals(""))
               {
                   Intent data = new Intent();
                   data.putExtra("name",name.getEditText().getText().toString());
                   data.putExtra("description",description.getEditText().getText().toString());
                   setResult(RESULT_OK,data);
               }
                if(extras!=null&&!extras.getString("note","").equals("")) {
                    mNote.setName(name.getEditText().getText().toString());
                    mNote.setDescription(description.getEditText().getText().toString());
                    db.update(mNote);
                }
               finish();
            }
        });

    }
    private void init(){
        mNote = gson.fromJson(getIntent().getStringExtra("note"), Note.class);
        db = new DBHelper(FillCardActivity.this);
    }
}