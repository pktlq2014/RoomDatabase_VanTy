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

public class AddEditNote extends AppCompatActivity {
    public static final String EXTRA_TITLE = "NAME";
    public static final String EXTRA_DESCRIPTION = "YEAR";
    public static final String EXTRA_HOME = "HOME";
    public static final String EXTRA_PRIORITY = "PRIORITY";
    public static final String EXTRA_ID = "ID";
    private EditText edit_text_title1;
    private EditText edit_text_desscription1;
    private EditText edt_home_town1;
    private NumberPicker numberPicker1;
    private ImageView imv_back1;
    private Button add_note1;
    private Button btn_delete1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        edit_text_desscription1 = findViewById(R.id.edit_text_desscription1);
        edit_text_title1 = findViewById(R.id.edit_text_title1);
        edt_home_town1 = findViewById(R.id.edt_home_town1);
        numberPicker1 = findViewById(R.id.number_picker_priority1);
        add_note1 = findViewById(R.id.save_note1);
        imv_back1 = findViewById(R.id.imv_back1);
        btn_delete1 = findViewById(R.id.btn_delete1);

        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(10);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID))
        {
            edit_text_title1.setText(intent.getStringExtra(EXTRA_TITLE));
            edit_text_desscription1.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            edt_home_town1.setText(intent.getStringExtra(EXTRA_HOME));
            numberPicker1.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }
        else
        {

        }
        imv_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_text_desscription1.setText("");
                edit_text_title1.setText("");
                edt_home_town1.setText("");
                numberPicker1.setValue(1);
            }
        });
        add_note1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }
    private void saveNote()
    {
        String nameStudent =  edit_text_title1.getText().toString();
        String yearBirth = edit_text_desscription1.getText().toString();
        String homeTown = edt_home_town1.getText().toString();
        int priority = numberPicker1.getValue();
        if(nameStudent.equals("") || homeTown.equals("") && yearBirth.equals("") )
        {
            Toast.makeText(AddEditNote.this, "Bạn Chưa Nhập Thông Tin", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITLE, nameStudent);
            intent.putExtra(EXTRA_DESCRIPTION, yearBirth);
            intent.putExtra(EXTRA_HOME, homeTown);
            intent.putExtra(EXTRA_PRIORITY, priority);
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if(id != -1)
            {
                intent.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
