package com.example.a22androididrawerlayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a22androididrawerlayout.Database.Note;
import com.example.a22androididrawerlayout.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public static final int VALUE = 1;
    public static final int UPDATE = 1;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBar actionBar;
    NavigationView navigationView;
    static int y;
    FragmentManager fragmentManager;
    ImageView imv_vietnam;
    ImageView imv_hoaky;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    TextView tv_list;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view);
        imv_vietnam = findViewById(R.id.imv_vietnam);
        imv_hoaky = findViewById(R.id.imv_hoaky);
        tv_list = findViewById(R.id.tv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // update recyc
                noteAdapter.submitList(notes);
            }
        });
        setSupportActionBar(toolbar);
        // kích hoạt hiện nút 3 gạch
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // dùng để hiện nút 3 gạch nhưng cần actionBar(true) ở trên để kích hoạt
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        /// thiết lập chức năng mở navigation cho nút 3 dấu gạch ngang
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // mở ra navigation
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        // setting icon đổi hình 3 gạch thành android =))
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        setEventClickFloat();
        setEventScroll();
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                noteViewModel.delete(noteAdapter.getNoteAt(position));
                Toast.makeText(MainActivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClickUpdate(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNote.class);
                intent.putExtra(AddEditNote.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNote.EXTRA_TITLE, note.getNameStudent());
                intent.putExtra(AddEditNote.EXTRA_DESCRIPTION, note.getYearBirth());
                intent.putExtra(AddEditNote.EXTRA_HOME, note.getHomeTown());
                intent.putExtra(AddEditNote.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, UPDATE);
            }
        });
        changeLanguage();
    }

    private void changeLanguage()
    {
        imv_vietnam.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chuyenNgonNgu("vi");
            }
        });
        imv_hoaky.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chuyenNgonNgu("en");
            }
        });
    }
    public void chuyenNgonNgu(String ngonNgu)
    {
        Locale locale = new Locale(ngonNgu);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                configuration,
                getBaseContext().getResources().getDisplayMetrics()
        );
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void setEventScroll()
    {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //  super.onScrolled(recyclerView, dx, dy);
                y = dy;
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.SCROLL_STATE_DRAGGING==newState){
                    floatingActionButton.setVisibility(View.GONE);
                }
                if(recyclerView.SCROLL_STATE_IDLE==newState){
                    if(y<=0){
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }
                    else
                        {
                        y=0;
                        floatingActionButton.setVisibility(View.GONE);
                    }}
            }
        });
    }

    private void setEventClickFloat()
    {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivityForResult(intent, VALUE);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
  //      noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
    }

    // thiết lập chức năng click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.item_insert:
            {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivityForResult(intent, VALUE);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                break;
            }
            case R.id.item_update:
            {
                break;
            }
            case R.id.item_delete:
            {
                noteViewModel.deleteAllNote();
                Toast.makeText(MainActivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VALUE && resultCode == RESULT_OK && data != null)
        {
            String nameStudent = data.getStringExtra(AddNote.NAME);
            String yearBirth = data.getStringExtra(AddNote.YEAR);
            String homeTown = data.getStringExtra(AddNote.HOME);
            int priority = data.getIntExtra(AddNote.PRIORITY, 1);
            Note note = new Note(nameStudent, yearBirth, homeTown, priority);
            noteViewModel.insert(note);
            Toast.makeText(MainActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == UPDATE && resultCode == RESULT_OK && data != null)
        {
            int id = data.getIntExtra(AddEditNote.EXTRA_ID, -1);
            if(id == -1)
            {
                Toast.makeText(MainActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                return;
            }
            String nameStudent = data.getStringExtra(AddEditNote.EXTRA_TITLE);
            String yearBirth = data.getStringExtra(AddEditNote.EXTRA_DESCRIPTION);
            String homeTown = data.getStringExtra(AddEditNote.EXTRA_HOME);
            int priority = data.getIntExtra(AddEditNote.EXTRA_PRIORITY, 1);
            Note note = new Note(nameStudent, yearBirth, homeTown, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
        }
    }
}
