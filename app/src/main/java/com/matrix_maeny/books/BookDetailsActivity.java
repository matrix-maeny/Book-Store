package com.matrix_maeny.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.matrix_maeny.books.books.BooksModel;
import com.matrix_maeny.books.databinding.ActivityBookDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity {

    ActivityBookDetailsBinding binding;

    ArrayList<BooksModel> list;
    BooksModel model = null;
    int position = -1;


    private String title;
    private String subtitle;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private String shortDescription;
    private long pageCount;
    private String thumbnail;
    private String previewLink;
    private String infoLink;
    private String buyLink;

    private String country;
    private String saleability;
    private String currencyCode;

    private long price;// = -1;
    private long averageRating;// = -1;
    private long ratingCount;// = -1;
    private boolean isEbook = false;
    private boolean isPdfAvailable = false;

    private String categoryList;
    private ArrayList<String> industryIdentifiers;
    private String bookLanguage;
    private String bookType;

    final Handler handler = new Handler();
    boolean imageFlag = true;
    Picasso picasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (BooksData.model == null) return;
        if (BooksData.position == -1) return;
        picasso = Picasso.get();
        picasso.setLoggingEnabled(true);
        initialize();



    }


    private void initialize() {
        model = BooksData.model;
        setupValues();
    }

    private void setupValues() {

        title = model.getTitle();
        subtitle = model.getSubtitle();
        authors = model.getAuthorsString();

        publisher = model.getPublisher();
        publishedDate = model.getPublishedDate();
        description = model.getDescription();
        shortDescription = model.getShortDescription();
        pageCount = model.getPageCount();
        thumbnail = model.getThumbnail();
        previewLink = model.getPreviewLink();
        buyLink = model.getBuyLink();

        country = model.getCountry();
        saleability = model.getSaleability();
        currencyCode = model.getCurrencyCode();

        price = model.getPrice();
        averageRating = model.getAverageRating();
        ratingCount = model.getRatingCount();

        categoryList = model.getCategory();
        industryIdentifiers = model.getIndustryIdentifiers();

        bookLanguage = model.getBookLanguage();
        bookType = model.getBookType();


        setValuesToUI();

    }

    View.OnClickListener previewBtnListener = v -> {
        Uri uri = Uri.parse(previewLink);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    };
    View.OnClickListener buyBtnListener = v -> {
        Uri uri = Uri.parse(buyLink);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    };


    private void setValuesToUI() {
        binding.bookNameSelected.setText(title);

        binding.authorsNameSelected.setText(authors);


        Context context = this;

//        handler.post(() -> Picasso.with(context).load(thumbnail).placeholder(R.drawable.ic_launcher_background).into(binding.bookImageSelected));

        thumbnail = thumbnail.replace("httpsss","https");
        picasso.load(thumbnail).placeholder(R.drawable.ic_launcher_background).into((ImageView) binding.bookImageSelected);



        if (saleability != null && !saleability.equals("")) {
            binding.tv132.setText(saleability);

            if(!saleability.equals("NOT_FOR_SALE")){
                if (price != -1) {
                    String totalPrice = "Price: " + price + "  " + currencyCode;
                    binding.bookPriceSelected.setText(totalPrice);
                } else {
                    binding.bookPriceSelected.setVisibility(View.GONE);
                }
            }else{
                binding.bookPriceSelected.setVisibility(View.GONE);
            }
        }

        if (buyLink != null && !buyLink.equals("")) {
            binding.buyBtn.setOnClickListener(buyBtnListener);

        } else {
            binding.buyBtn.setVisibility(View.GONE);
        }
        if (previewLink != null && !previewLink.equals("")) {
            binding.previewBtn.setOnClickListener(previewBtnListener);
        } else {
            binding.previewBtn.setVisibility(View.GONE);
        }

        if (description != null && !description.equals("")) {
            binding.descriptionContent.setText(description);

        }

        if (publisher != null && !publisher.equals("")) {
            binding.tv12.setText(publisher);

        }

        if (publishedDate != null && !publishedDate.equals("")) {
            binding.tv22.setText(publishedDate);

        }

        if (country != null && !country.equals("")) {
            binding.bookDetailsHead.setVisibility(View.VISIBLE);
            binding.tv32.setText(country);

        }
        if (bookLanguage != null && !bookLanguage.equals("")) {
            binding.tv62.setText(bookLanguage);
        }
        if (categoryList != null && !categoryList.equals("")) {
            binding.tv72.setText(categoryList);
        }
        if (pageCount > 0) {
            binding.tv82.setText(String.valueOf(pageCount));
        }
        if (bookType != null && !bookType.equals("")) {
            binding.tv92.setText(bookType);
        }

        binding.tv102.setText(model.getPdfAvailability());

        if (averageRating > 0) {
            binding.tv112.setText(String.valueOf(averageRating));
        }
        if (ratingCount > 0) {
            binding.tv122.setText(String.valueOf(ratingCount));
        }




        try {
            if (industryIdentifiers != null) {
                if (industryIdentifiers.get(0) != null) {
                    binding.tv41.setText(industryIdentifiers.get(0));
                    binding.tv42.setText(industryIdentifiers.get(1));
                }
                if (industryIdentifiers.get(2) != null) {
                    binding.tv51.setText(industryIdentifiers.get(2));
                    binding.tv52.setText(industryIdentifiers.get(3));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.progressBar2.setVisibility(View.GONE);



    }
}