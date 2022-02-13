package uz.context.sqlite_java.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uz.context.sqlite_java.R;
import uz.context.sqlite_java.activities.UpdateActivity;
import uz.context.sqlite_java.database.MyDatabaseHelper;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList bookId, bookTitle, bookAuthor, bookPages;
    Activity activity;
    Animation translateAnim;

    public CustomAdapter(Activity activity, Context context, ArrayList bookId, ArrayList bookTitle, ArrayList bookAuthor, ArrayList bookPages) {
        this.activity = activity;
        this.context = context;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPages = bookPages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bookIdTxt.setText(String.valueOf(bookId.get(position)));
        holder.bookTitleTxt.setText(String.valueOf(bookTitle.get(position)));
        holder.bookAuthorTxt.setText(String.valueOf(bookAuthor.get(position)));
        holder.bookPagesTxt.setText(String.valueOf(bookPages.get(position)));
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id",String.valueOf(bookId.get(position)));
            intent.putExtra("title",String.valueOf(bookTitle.get(position)));
            intent.putExtra("author",String.valueOf(bookAuthor.get(position)));
            intent.putExtra("pages",String.valueOf(bookPages.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }

    @Override
    public int getItemCount() {
        return bookId.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bookIdTxt,bookTitleTxt,bookAuthorTxt,bookPagesTxt;
        CardView mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookIdTxt = itemView.findViewById(R.id.book_id_txt);
            bookTitleTxt = itemView.findViewById(R.id.book_title_txt);
            bookAuthorTxt = itemView.findViewById(R.id.book_author_txt);
            bookPagesTxt = itemView.findViewById(R.id.book_pager_txt);
            mainLayout = itemView.findViewById(R.id.main_layout);
            translateAnim = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
