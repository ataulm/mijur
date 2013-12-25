package uk.co.ataulm.mijur.app;

import android.net.Uri;

import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;
import uk.co.ataulm.mijur.BuildConfig;

public class ContentProvider extends SQLiteContentProviderImpl {

    public static final Uri AUTHORITY = Uri.parse("content://" + BuildConfig.PROVIDER_AUTHORITY);

}
