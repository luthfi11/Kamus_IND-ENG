package com.luthfialfarisi.dicodingkamus.utils;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_ID_EN  = "ID_EN";
    public static String TABLE_EN_ID  = "EN_ID";

    public static final class KamusColumns implements BaseColumns {
        public static String WORD = "WORD";
        public static String DESC = "DESC";
    }

}
