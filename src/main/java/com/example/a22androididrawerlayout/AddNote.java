package com.example.a22androididrawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNote extends AppCompatActivity
{
    public static final String NAME = "NAME";
    public static final String YEAR = "YEAR";
    public static final String HOME = "HOME";
    public static final String PRIORITY = "PRIORITY";
    private EditText edit_text_title;
    private EditText edit_text_desscription;
    private EditText edt_home_town;
    private NumberPicker numberPicker;
    private ImageView imv_back;
    private Button add_note;
    private Button btn_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note2);
        edit_text_desscription = findViewById(R.id.edit_text_desscription);
        edit_text_title = findViewById(R.id.edit_text_title);
        edt_home_town = findViewById(R.id.edt_home_town);
        numberPicker = findViewById(R.id.number_picker_priority);
        add_note = findViewById(R.id.save_note);
        imv_back = findViewById(R.id.imv_back);
        btn_delete = findViewById(R.id.btn_delete);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_text_desscription.setText("");
                edit_text_title.setText("");
                edt_home_town.setText("");
                numberPicker.setValue(1);
            }
        });
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }
    private void saveNote()
    {
        String nameStudent =  edit_text_title.getText().toString();
        String yearBirth = edit_text_desscription.getText().toString();
        String homeTown = edt_home_town.getText().toString();
        int priority = numberPicker.getValue();
        if(nameStudent.equals("") || homeTown.equals("") && yearBirth.equals("") )
        {
            Toast.makeText(AddNote.this, "Bạn Chưa Nhập Thông Tin", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Intent intent = new Intent();
            intent.putExtra(NAME, nameStudent);
            intent.putExtra(YEAR, yearBirth);
            intent.putExtra(HOME, homeTown);
            intent.putExtra(PRIORITY, priority);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
