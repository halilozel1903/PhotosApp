package com.halil.ozel.photosapp.utils;

public class Constants {
    // API Constants
    public static final String BASE_URL = "https://www.flickr.com/services/rest/";
    public static final String API_KEY = "5adbb887ee60c548c02690ef16b848cd";

    // Pagination
    public static final int ITEMS_PER_PAGE = 20;
    public static final int GRID_COLUMNS = 2;
    public static final int VIEW_THRESHOLD = 20;

    // Intent Keys
    public static final String EXTRA_POSTER_URL = "posterUrl";

    // Timeout
    public static final int CONNECT_TIMEOUT = 30;
    public static final int READ_TIMEOUT = 30;
    public static final int WRITE_TIMEOUT = 30;

    private Constants() {
        // Private constructor to prevent instantiation
    }
}

