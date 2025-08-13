package org.librarymanagement.constant;

public class ApiEndpoints {
    // User
    public static final String BASE_USER_URI = "/api";

    public static final String USER_AUTH = BASE_USER_URI + "/auth";
    public static final String USER_PROFILE = BASE_USER_URI + "/me";
    public static final String USER_BOOK = BASE_USER_URI + "/book";

    // Admin
    public static final String BASE_ADMIN_URI = "/admin";

    public static final String ADMIN_AUTH = BASE_ADMIN_URI + "/auth";
    public static final String ADMIN_DASHBOARD = BASE_ADMIN_URI + "/dashboard";
    public static final String ADMIN_BOOK = BASE_ADMIN_URI + "/book";
}
