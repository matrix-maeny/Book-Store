package com.matrix_maeny.books.books;

import java.util.ArrayList;


/*
* pattern for showing data
*   *.
*   1. publisher
*   2. publish date
*   3. country
*   4. industry identifier 1
*   5. industry identifier 2
*   6. language
*   7. category
*   8. page count
*   9. is ebook
*   10. pdf
*   11. avg ratings
*   12. customers
*   13. saleability
*
*
* */
public class BooksModel {

    private String title;
    private String subtitle;
    private ArrayList<String> authors;
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

    private ArrayList<String> categoryList;
    private ArrayList<String> industryIdentifiers;
    String bookLanguage;


    public BooksModel(String title, String subtitle, ArrayList<String> authors, String publisher,
                      String publishedDate, String description, long pageCount, String thumbnail,
                      String previewLink, String infoLink,
                      String buyLink, String shortDescription, String country, String saleability, String currencyCode,
                      long price, long averageRating, long ratingCount,
                      boolean isEbook, boolean isPdfAvailable,String bookLanguage,ArrayList<String> categoryList,ArrayList<String> industryIdentifiers) {

        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.buyLink = buyLink;

        this.shortDescription = shortDescription;
        this.country = country;
        this.saleability = saleability;
        this.currencyCode = currencyCode;
        this.price = price;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.isEbook = isEbook;
        this.isPdfAvailable = isPdfAvailable;

        this.bookLanguage = bookLanguage;
        this.categoryList = categoryList;
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getCategory() {
        if (categoryList != null) {
            return categoryList.get(0);
        }else{
            return null;
        }
    }

    public void setCategoryList(ArrayList<String> categoryList) {
        this.categoryList = categoryList;
    }

    public ArrayList<String> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(ArrayList<String> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getBookLanguage() {
        if(bookLanguage.equalsIgnoreCase("en")){
            bookLanguage = "English";
        }else if(bookLanguage.equalsIgnoreCase("hi")){
            bookLanguage = "Hindi";
        }else if(bookLanguage.equalsIgnoreCase("ta")){
            bookLanguage = "Tamil";
        }
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthorsList() {
        return authors;
    }

    public String getAuthorsString() {
        StringBuilder authorsString = new StringBuilder("by ");

        if (authors == null) return "No Authors";

        for (String x : authors) {
            authorsString.append(x).append(", ");
        }


        authorsString = new StringBuilder(authorsString.substring(0, authorsString.length() - 2));

        return authorsString.toString();
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getThumbnail() {

        try {
            thumbnail = thumbnail.replace("http", "https");

            return thumbnail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(long averageRating) {
        this.averageRating = averageRating;
    }

    public long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public boolean isEbook() {
        return isEbook;
    }

    public void setEbook(boolean ebook) {
        isEbook = ebook;
    }

    public String getPdfAvailability() {
        if (isPdfAvailable) {
            return "PDF Available";
        } else {
            return "PDF not Available";

        }
    }

    public void setPdfAvailable(boolean pdfAvailable) {
        isPdfAvailable = pdfAvailable;
    }

    public String getRatingsAndCount() {

        String ratingText = "Ratings: ";

        if (averageRating > 0) {
            ratingText += averageRating + " ";
            ratingText += ", Count: " + ratingCount;
        } else {
            ratingText = "No Ratings Available";
        }

        return ratingText;

    }

    public String getBookType() {
        String type;
        if (isEbook) {
            type = "Ebook";
        } else {
            type = "Paperback";
        }


        return type;
    }
}
