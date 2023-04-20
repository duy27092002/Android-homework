package t3h.android.crudstudent;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExampleProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        uriMatcher.addURI("t3h.android.crudstudent", "students", 1);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://t3h.android.crudstudent/students/3" matches, but
         * "content://t3h.android.crudstudent/students doesn't.
         */
        uriMatcher.addURI("t3h.android.crudstudent", "students/#", 2);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projects,
                        @Nullable String selectionClause,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        /*
         * Choose the table to query and a sort order based on the code returned for the incoming
         * URI. Here, too, only the statements for table 3 are shown.
         */
        switch (uriMatcher.match(uri)) {
            // If the incoming URI was for all of student
            case 1:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
                break;
            // If the incoming URI was for a single row
            case 2:
                /*
                 * Because this URI was for a single row, the _ID value part is
                 * present. Get the last path segment from the URI; this is the _ID value.
                 * Then, append the value to the WHERE clause for the query
                 */
                selectionClause = selectionClause + "_ID = " + uri.getLastPathSegment();
                break;
            default:
                // If the URI is not recognized, you should do some error handling here.
        }
        // call the code to actually do the query
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selectionClause, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selectionClause, @Nullable String[] selectionArgs) {
        return 0;
    }
}
