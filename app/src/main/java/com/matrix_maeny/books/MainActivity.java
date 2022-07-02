package com.matrix_maeny.books;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.matrix_maeny.books.books.BooksAdapter;
import com.matrix_maeny.books.books.BooksModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BooksModel> list = null;
    BooksAdapter booksAdapter = null;
    AppCompatButton searchBtn;
    EditText searchText;
    ProgressBar progressBar;
    TextView nothingFound;

    volatile boolean continueInitialLoadFlag = true;
    final Handler handler = new Handler();
    final Random random = new Random();

    StartSearch startSearch = null;

    RequestQueue requestQueue;
    private String requestedBookName = null;
    private final String[] randomBooks = {"motivation", "love life", "life", "lust", "programming", "computers", "world", "biology", "books", "science", "brain", "technology", "artificial intelligence", " bro bakth singh", "elon musk", "jef bezos", "bill gates", "inventions", "time"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initialize();
    }

    private void initialize() {

        searchBtn = findViewById(R.id.searchBtn);
        searchText = findViewById(R.id.searchText);
        progressBar = findViewById(R.id.progressBar);
        nothingFound = findViewById(R.id.nothingFound);
        nothingFound.setVisibility(View.GONE);


        recyclerView = findViewById(R.id.recyclerView);
        searchBtn.setOnClickListener(searchBtnListener);
        searchText.setOnClickListener(searchTextListener);

        list = new ArrayList<>();
        booksAdapter = new BooksAdapter(MainActivity.this, list);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(booksAdapter);

        int num = random.nextInt(randomBooks.length);

        requestedBookName = randomBooks[num];

        startSearch = new StartSearch();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadInfo() {

        if (!list.isEmpty()) {
            BooksAdapter.places.clear();
            BooksData.list = list;

        }

        try {

            handler.post(() -> booksAdapter.notifyDataSetChanged());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // on click listener for components
    View.OnClickListener searchBtnListener = v -> {

        try {
            requestedBookName = searchText.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            tempToast("Please enter book name to search", 0);
            return;
        }

        if (requestedBookName.equals("")) {
            tempToast("Please enter book name to search", 0);
            return;
        }

        continueInitialLoadFlag = false;

        if (startSearch != null && !startSearch.isAlive()) {
            continueInitialLoadFlag = true;
            progressBar.setVisibility(View.VISIBLE);
            getBooksInfo();
        }


    };
    View.OnClickListener searchTextListener = v -> progressBar.setVisibility(View.GONE);

    private void getBooksInfo() {

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        requestQueue.getCache().clear();
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + requestedBookName;
        String url2 = "http://api.brainshop.ai/get?bid=165038&key=G5VRevX6xZxY9blF&uid=[uid]&msg=" + requestedBookName;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            list.clear();

            handler.post(() -> progressBar.setVisibility(View.VISIBLE));
            try {
                JSONArray array = response.getJSONArray("items");

                JSONObject bookObject, volumeObj = null, saleInfoObj = null, retailPriceObj, imageLinks, accessInfoObj;
                String title = null;
                String subtitle = null;
                JSONArray authorsArray = null, industryIdentifiers = null, categories = null;
                String publisher = null, shortDescription = null, buyLink, country = null, saleability = null, thumbnail = null, previewLink = null, infoLink = null, publishedDate = null, description = null;

                String language = null;

                long pageCount = -1;
                long price = -1;
                String currencyCode = null;

                long averageRating = -1;
                long ratingCount = -1;
                boolean isEbook = false;
                boolean isPdfAvailable = false;


                for (int i = 0; i < array.length(); i++) {

                    if (!continueInitialLoadFlag) {
                        break;
                    }

                    // taking each book , if it is caught by exception then need not to take volume obj
                    try {
                        bookObject = array.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        continue;
                    }


                    try {
                        if (bookObject != null) {
                            volumeObj = bookObject.getJSONObject("volumeInfo");
                            saleInfoObj = bookObject.optJSONObject("saleInfo");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (volumeObj != null) {
                        title = volumeObj.optString("title");
                        subtitle = volumeObj.optString("subtitle");

                        try {

                            authorsArray = volumeObj.getJSONArray("authors");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {

                            industryIdentifiers = volumeObj.getJSONArray("industryIdentifiers");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {

                            categories = volumeObj.getJSONArray("categories");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//


                        publisher = volumeObj.optString("publisher");
                        publishedDate = volumeObj.optString("publishedDate");
                        description = volumeObj.optString("description");
                        pageCount = volumeObj.optLong("pageCount");
                        previewLink = volumeObj.optString("previewLink");

                        imageLinks = volumeObj.optJSONObject("imageLinks");
                        infoLink = volumeObj.optString("infoLink");

                        averageRating = volumeObj.optLong("averageRating");
                        ratingCount = volumeObj.optLong("ratingsCount");
                        language = volumeObj.optString("language");

                        if (imageLinks != null) {
                            thumbnail = imageLinks.optString("thumbnail");
                        }
                    } // getting volume info


                    buyLink = null;// saleInfoObj.optString("buyLink");

                    if (saleInfoObj != null) {
                        buyLink = saleInfoObj.optString("buyLink");
                        isEbook = saleInfoObj.optBoolean("isEbook");
                        country = saleInfoObj.optString("country");
                        saleability = saleInfoObj.optString("saleability");

                        try { // getting price info
                            retailPriceObj = saleInfoObj.getJSONObject("retailPrice");
                            price = retailPriceObj.optLong("amount");
                            currencyCode = retailPriceObj.optString("currencyCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } // getting sale info


                    // getting accessInfo && searchInfo

                    try {
                        if (bookObject != null) {

                            accessInfoObj = bookObject.getJSONObject("accessInfo");

                            isPdfAvailable = accessInfoObj.getJSONObject("pdf").optBoolean("isAvailable");

                            shortDescription = bookObject.getJSONObject("searchInfo").optString("textSnippet");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    ArrayList<String> authorsList = null;// new ArrayList<>();
                    ArrayList<String> industryList = null;// new ArrayList<>();
                    ArrayList<String> categoryList = null;// new ArrayList<>();

                    if (authorsArray != null && authorsArray.length() != 0) {

                        authorsList = new ArrayList<>();

                        for (int j = 0; j < authorsArray.length(); j++) {
                            authorsList.add(authorsArray.optString(j));
                        }
                    }
                    if (industryIdentifiers != null && industryIdentifiers.length() != 0) {

                        industryList = new ArrayList<>();

                        for (int j = 0; j < industryIdentifiers.length(); j++) {

                            try {
                                JSONObject object = industryIdentifiers.getJSONObject(j);
                                industryList.add(object.optString("type"));
                                industryList.add(object.optString("identifier"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (categories != null && categories.length() != 0) {

                        categoryList = new ArrayList<>();

                        for (int j = 0; j < categories.length(); j++) {
                            categoryList.add(categories.optString(j));
                        }
                    }

                    BooksModel model = new BooksModel(
                            title, subtitle, authorsList, publisher, publishedDate,
                            description, pageCount,
                            thumbnail, previewLink,
                            infoLink, buyLink, shortDescription, country, saleability, currencyCode, price,
                            averageRating, ratingCount,
                            isEbook, isPdfAvailable, language, categoryList, industryList
                    );

                    list.add(model);
                    loadInfo();


                }

                loadInfo();


            } catch (Exception e) {
                // do something worthy
                e.printStackTrace();
                tempToast("No data found", 1);
                handler.post(() -> progressBar.setVisibility(View.VISIBLE));

            } finally {
                loadInfo();
                if (list.size() == 0) {
                    handler.post(() -> nothingFound.setVisibility(View.VISIBLE));
                } else {

                    handler.post(() -> nothingFound.setVisibility(View.GONE));

                }
                handler.post(() -> progressBar.setVisibility(View.GONE));

            }

        }, error -> {

            String myError = error + "";
            if (myError.contains("NoConnectionError")) {
                tempToast("No internet connection", 1);
            } else {
                tempToast("Some Error occurred: contact matrix", 1);
            }


            progressBar.setVisibility(View.GONE);

            if (list.size() == 0) {
                handler.post(() -> nothingFound.setVisibility(View.VISIBLE));
            } else {
                handler.post(() -> nothingFound.setVisibility(View.GONE));
            }

        });


        JsonObjectRequest objectRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, response -> {

            try {
                String robotResponse = response.getString("cnt");
//                list.add(new MessageModel(robotResponse,ROBOT_KEY));
//                handler.post(() -> adapter.notifyDataSetChanged());
                handler.post(() -> Toast.makeText(this, robotResponse, Toast.LENGTH_SHORT).show());

            } catch (JSONException e) {

//                list.add(new MessageModel("No response",ROBOT_KEY));
//                handler.post(() -> adapter.notifyDataSetChanged());

            }
        }, error -> {

//            list.add(new MessageModel("Sorry no response found", ROBOT_KEY));
            handler.post(() -> Toast.makeText(MainActivity.this, "No response from the bot.." + error, Toast.LENGTH_LONG).show()
            );

        });

        queue.add(objectRequest);
        queue.add(objectRequest2);
    }

    final void tempToast(String msg, int time) {
        if (time == 0) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }


    private class StartSearch extends Thread {

        public StartSearch() {
            start();
        }

        public void run() {
            getBooksInfo();
        }
    }


}