package com.matrix_maeny.books.books;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.matrix_maeny.books.BookDetailsActivity;
import com.matrix_maeny.books.BooksData;
import com.matrix_maeny.books.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.viewHolder> {

    Context context;
    ArrayList<BooksModel> list;

    public static ArrayList<Integer> places = null;


    public BooksAdapter(Context context, ArrayList<BooksModel> list) {
        this.context = context;
        this.list = list;
        places = new ArrayList<>();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.books_model, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        BooksModel model = list.get(position);

        holder.bookTitle.setText(model.getTitle());
        holder.bookAuthors.setText(model.getAuthorsString());
        holder.bookRatings.setText(model.getRatingsAndCount());
        holder.bookType.setText(model.getBookType());
        holder.bookSaleability.setText(model.getSaleability());

        holder.bookPdf.setText(model.getPdfAvailability());

        if (!places.contains(position)) {
            Picasso.get().load(model.getThumbnail()).placeholder(R.drawable.ic_launcher_background).noFade().into(holder.bookImage);
            places.add(position);
            BooksData.tempImage = model.getThumbnail();
        }

        holder.cardView.setOnClickListener(v -> {
            BooksData.position = holder.getAdapterPosition();
            BooksData.model = model;
            context.startActivity(new Intent(context.getApplicationContext(), BookDetailsActivity.class));
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView bookTitle, bookAuthors, bookRatings, bookType, bookSaleability,bookPdf;
        ImageView bookImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthors = itemView.findViewById(R.id.bookAuthors);
            bookRatings = itemView.findViewById(R.id.bookRatings);
            bookType = itemView.findViewById(R.id.bookType);
            bookSaleability = itemView.findViewById(R.id.bookSaleability);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookPdf = itemView.findViewById(R.id.bookPdf);
        }
    }
}
