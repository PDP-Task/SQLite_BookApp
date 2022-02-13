package uz.context.sqlite_java.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uz.context.sqlite_java.R;
import uz.context.sqlite_java.database.MyDatabaseHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText titleInput,authorInput,pagesInput;
    Button updateBtn, deleteBtn;
    String id, title, author, pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        titleInput = findViewById(R.id.title_input2);
        authorInput = findViewById(R.id.author_input2);
        pagesInput = findViewById(R.id.pages_input2);
        updateBtn = findViewById(R.id.update_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(view -> confirmDialog());

        getAndSetIntentData();
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(title);
        }
        updateBtn.setOnClickListener(view -> {
            MyDatabaseHelper myDb = new MyDatabaseHelper(this);
            title = titleInput.getText().toString().trim();
            author = authorInput.getText().toString().trim();
            pages = pagesInput.getText().toString().trim();
            if (title.isEmpty() || author.isEmpty() || pages.isEmpty()) {
                Toast.makeText(this, "Enter completely", Toast.LENGTH_SHORT).show();
            } else {
               myDb.updateData(id, title, author, pages);
            }
        });
    }
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
        getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            titleInput.setText(title);
            authorInput.setText(author);
            pagesInput.setText(pages);
        } else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.deleteOneRow(id);
            finish();
        });
        builder.setNegativeButton("No", ((dialogInterface, i) -> {
        }));
        builder.create().show();
    }
}