package foxstore.android.com.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class MetaDataUtils {

    public static Object getMetaData(Context paramContext, String name, Object defaultValue) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = paramContext.getPackageManager();
        ApplicationInfo applicationInfo = packageManager
                .getApplicationInfo(paramContext.getPackageName(),
                        PackageManager.GET_META_DATA);
        if (applicationInfo != null) {
            Object obj = applicationInfo.metaData.get(name);
            if (obj != null) {
                return obj;
            }
        }
        return defaultValue;
    }
}
