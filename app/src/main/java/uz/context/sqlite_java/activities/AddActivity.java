package uz.context.sqlite_java.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uz.context.sqlite_java.R;
import uz.context.sqlite_java.database.MyDatabaseHelper;

public class AddActivity extends AppCompatActivity{

    EditText titleInput, authorInput, pagesInput;
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleInput = findViewById(R.id.title_input);
        authorInput = findViewById(R.id.author_input);
        pagesInput = findViewById(R.id.pages_input);
        addBtn = findViewById(R.id.add_button_save);

        addBtn.setOnClickListener(view -> {
            if (titleInput.getText().toString().trim().isEmpty()
                    || authorInput.getText().toString().trim().isEmpty()
                    || pagesInput.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Enter completely", Toast.LENGTH_SHORT).show();
            } else {
                String title = titleInput.getText().toString().trim();
                String author = authorInput.getText().toString().trim();
                int pages = Integer.parseInt(pagesInput.getText().toString().trim());
                MyDatabaseHelper myDb = new MyDatabaseHelper(AddActivity.this);
                myDb.addBook(title, author, pages);
            }
        });
    }
}