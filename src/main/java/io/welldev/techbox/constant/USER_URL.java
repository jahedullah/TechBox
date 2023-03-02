package io.welldev.techbox.constant;

public class USER_URL {
    public static final String USER_PRODUCT_LIST = "/users/{userId}/products";

    public static final String USER_WITH_ID = "/users/{userId}";
    public static final String USER_UPDATE_BY_ID = "/users/{userId}";
    public static final String USER_DELETE_BY_ID = "/users/{userId}";
    public static final String USER_PRODUCT_DELETE_BY_ID = "/users/{userId}/products/{productId}";
    public static final String USER_ADD_PRODUCT_WITH_ID = "/users/{userId}/products/{productId}";
    public static final String USER_PASSWORD_CHANGE = "/users/{userId}/password";
    public static final String USER_FORGOT_PASSWORD = "/users/forgot-password";

}
