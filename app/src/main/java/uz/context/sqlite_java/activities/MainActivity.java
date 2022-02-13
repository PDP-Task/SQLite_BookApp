package uz.context.sqlite_java.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uz.context.sqlite_java.R;
import uz.context.sqlite_java.adapter.CustomAdapter;
import uz.context.sqlite_java.database.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private FloatingActionButton addBtn;
    MyDatabaseHelper myDb;
    ArrayList<String> bookId, bookTitle, bookAuthor, bookPages;
    CustomAdapter customAdapter;
    ImageView emptyImg;
    TextView noDataText;
    AddActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = new AddActivity();
        addBtn = findViewById(R.id.add_btn);
        recyclerView = findViewById(R.id.recyclerview);
        emptyImg = findViewById(R.id.empty_image);
        noDataText = findViewById(R.id.no_data);
        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });
        myDb = new MyDatabaseHelper(MainActivity.this);
        bookId = new ArrayList<>();
        bookTitle = new ArrayList<>();
        bookAuthor = new ArrayList<>();
        bookPages = new ArrayList<>();

        storageDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this,bookId,bookTitle,bookAuthor,bookPages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storageDataInArrays() {
        Cursor cursor = myDb.readAllData();
        if (cursor.getCount() == 0) {
            emptyImg.setVisibility(View.VISIBLE);
            noDataText.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                bookId.add(cursor.getString(0));
                bookTitle.add(cursor.getString(1));
                bookAuthor.add(cursor.getString(2));
                bookPages.add(cursor.getString(3));
            }
            emptyImg.setVisibility(View.GONE);
            noDataText.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void toast() {
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?");
        builder.setMessage("Are you sure you want to delete all data?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            MyDatabaseHelper myDb = new MyDatabaseHelper(this);
            toast();
            myDb.deleteAllData();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", ((dialogInterface, i) -> {
        }));
        builder.create().show();
    }
}