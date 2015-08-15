package vn.alovoice.ideabox.data;

import android.provider.BaseColumns;

/**
 * Created by VIENTHONG on 8/15/2015.
 */
public class IdeaContract {

    public static final class IdeaEntry implements BaseColumns {

        public static final String TABLE_NAME = "ideas";

        //Các cột trong bảng ideas
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DIENTHOAI = "dienthoai";
        public static final String COLUMN_NOIDUNG = "noidung";
        public static final String COLUMN_NGAYTAO= "ngaytao";
    }
}
