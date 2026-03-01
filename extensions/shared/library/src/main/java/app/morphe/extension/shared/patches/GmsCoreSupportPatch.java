package app.morphe.extension.shared.patches;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Minimal GmsCore support extension class for Google Photos.
 * Uses only Android SDK classes — no dependency on shared-youtube library.
 * The patcher modifies getOriginalPackageName() and getGmsCoreVendorGroupId()
 * at patch-time by injecting return instructions.
 */
@SuppressWarnings({"unused", "deprecation"})
public class GmsCoreSupportPatch {

    private static final String GMS_CORE_PACKAGE_NAME =
            getGmsCoreVendorGroupId() + ".android.gms";

    private static final Uri GMS_CORE_PROVIDER =
            Uri.parse("content://" + getGmsCoreVendorGroupId() + ".android.gsf.gservices/prefix");

    private static String getOriginalPackageName() {
        return null; // Modified during patching.
    }

    /**
     * @return If the current package name matches the original unpatched app.
     */
    public static boolean isPackageNameOriginal() {
        String originalPackageName = getOriginalPackageName();
        return originalPackageName == null
                || originalPackageName.equals(getPackageContext().getPackageName());
    }

    private static volatile Context appContext;

    private static Context getPackageContext() {
        return appContext;
    }

    /**
     * Injection point. Called from the main activity onCreate.
     */
    public static void checkGmsCore(Activity context) {
        try {
            if (appContext == null) appContext = context.getApplicationContext();

            // Root-install check.
            if (isPackageNameOriginal()) {
                Toast.makeText(context,
                        "The 'GmsCore support' patch breaks mount installations",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Verify GmsCore is installed.
            try {
                context.getPackageManager()
                        .getPackageInfo(GMS_CORE_PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(context,
                        "MicroG (GmsCore) is not installed. Please install it.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Check GmsCore is running in the background.
            var client = context.getContentResolver()
                    .acquireContentProviderClient(GMS_CORE_PROVIDER);
            if (client != null) {
                client.close();
            } else {
                Toast.makeText(context,
                        "MicroG (GmsCore) is not running in the background." +
                        " Allow background activity for it.",
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            // Silently ignore errors during the check.
        }
    }

    private static String getGmsCoreVendorGroupId() {
        return "app.revanced"; // Modified during patching.
    }
}
