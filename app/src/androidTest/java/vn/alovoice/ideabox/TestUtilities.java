package vn.alovoice.ideabox;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

import vn.alovoice.ideabox.data.IdeaContract;

/**
 * Created by VIENTHONG on 8/15/2015.
 */
public class TestUtilities extends AndroidTestCase {
    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createIdeaValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(IdeaContract.IdeaEntry.COLUMN_DIENTHOAI, "0903757444");
        testValues.put(IdeaContract.IdeaEntry.COLUMN_NOIDUNG, "Ý tưởng hay");
        testValues.put(IdeaContract.IdeaEntry.COLUMN_NGAYTAO, "12/8/2015");
        return testValues;
    }
}
