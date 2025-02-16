package com.zype.fire.api;

import android.content.Context;

/**
 * Created by Evgeny Cherkasov on 17.04.2017.
 */

public class ZypeSettings {
    // Zype app key
    public static final String APP_KEY = "a03kQlVGOCUGyA1ZKMjajHLIQ_ha3uouZKTGJq21MBj79i8S070Cg1xfsa9WmtL2";
    // OAuth credentials
    public static final String CLIENT_ID = "beb849d6db649791e1540693182d5be2a7ffd30c190e5027f22c44ca3cdf1565";
    // public static final String CLIENT_SECRET = "d14ce33a7a6e53df00dc3eee8fd2156011bb300ec2f9952d3dcfa739f1476c7f";
    // Playlist
    public static final String ROOT_PLAYLIST_ID = "5f52957b247c740001a7b87b";

    public static final String FAVORITES_PLAYLIST_ID = "Favorites";
    public static final String MY_LIBRARY_PLAYLIST_ID = "MyLibrary";
    public static final String ROOT_FAVORITES_PLAYLIST_ID = "RootFavorites";
    public static final String ROOT_MY_LIBRARY_PLAYLIST_ID = "RootMyLibrary";
    public static final String ROOT_SLIDERS_PLAYLIST_ID = "RootSliders";

    // Template version
    public static final String TEMPLATE_VERSION = "1.8.0";

    public static final boolean ACCOUNT_NAV_BUTTON_DISPLAY = true;
    public static final boolean EPG_ENABLED = false;
    public static final boolean DETAIL_BACKGROUND_IMAGE = false;
    /* Define the app theme
     *
     * The default theme is dark. To make the app theme light, set this flag to 'true'.
     * This flag controls the following:
     * - Text color on Terms and Privacy Policy screen
    */
    public static final boolean LIGHT_THEME = false;
    public static final boolean SETTINGS_PLAYLIST_ENABLED = false;
    public static final boolean SHOW_EPISODE_NUMBER = false;
    public static final boolean SHOW_TITLE = true;
    public static final boolean SHOW_LEFT_MENU = true;
    public static final boolean SHOW_MENU_ICON = false;
    public static final boolean SHOW_SEARCH_ICON = false;
    public static final boolean SHOW_TOP_MENU = true;
    public static final boolean TERMS_NAV_BUTTON_DISPLAY = false;
    public static final boolean UNLOCK_TRANSPARENT = false;

    public static final String TERMS_CONDITION_URL  = "https://www.zype.com/";

    // Features
    public static final boolean ACCOUNT_CREATION_TOS = false;
    public static final boolean DEVICE_LINKING = true;
    public static final boolean FAVORITES_VIA_API = true;
    public static final boolean LIBRARY_ENABLED = true;

    // Monetization
    public static final boolean MARKETPLACE_CONNECT_SVOD = false;
    public static final boolean NATIVE_SUBSCRIPTION_ENABLED = false;
    public static final boolean NATIVE_TVOD = false;
    public static final boolean PLAYLIST_PURCHASE_ENABLED = false;
    public static final boolean SUBSCRIBE_TO_WATCH_AD_FREE_ENABLED = false;
    public static final boolean UNIVERSAL_SUBSCRIPTION_ENABLED = false;
    public static final boolean UNIVERSAL_TVOD = true;
    public static final boolean FIREBASE_ENABLED = true;

    // Analytics
    public static final boolean SEGMENT_ANALYTICS = false;
    public static final String SEGMENT_ANALYTICS_WRITE_KEY = "";

    /**
     * Amazon shared key is required for native subscription feature. It is used in request to Zype Bifrost
     * service for verifying subscription.
     */
    public static final String AMAZON_SHARED_KEY = "";
}
