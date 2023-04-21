package com.group05.emarket.enums;

public enum SortProductOption {
    NAME_ASCENDING,
    NAME_DESCENDING,
    PRICE_ASCENDING,
    PRICE_DESCENDING,
    HIGHEST_RATED,
    LOWEST_RATED;

    public static String toString(SortProductOption option) {
        switch (option) {
            case NAME_ASCENDING:
                return "Name (A-Z)";
            case NAME_DESCENDING:
                return "Name (Z-A)";
            case PRICE_ASCENDING:
                return "Price (Ascending)";
            case PRICE_DESCENDING:
                return "Price (Descending)";
            case HIGHEST_RATED:
                return "Highest Rated";
            case LOWEST_RATED:
                return "Lowest Rated";
            default:
                return null;
        }
    }
}
