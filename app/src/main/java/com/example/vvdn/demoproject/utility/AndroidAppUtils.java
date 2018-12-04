package com.example.vvdn.demoproject.utility;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.example.vvdn.volleydemoproject.R;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;



/**
 * Utils of this application use static methods of application.
 *
 * @author Darshna/2148
 */
public class AndroidAppUtils {
    private static final String TAG = "AndroidAppUtils";
    @SuppressWarnings("unused")
    private static final String MAC_PATTERN = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    /**
     * Object instance of progress bar
     */
    public static int MAX_COUNT = 3;
    public static boolean ISDEBUGGING = true;
    private static ProgressDialog mProgressDialog;

    /**
     * Show debug Message into logcat
     *
     * @param TAG
     * @param msg
     */
    public static void showLog(String TAG, String msg) {
        if (ISDEBUGGING) {
            try {
                Log.d(TAG, msg);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

    }

    /* Function to get sum of digits */
    public static long sumDigits(long no) {
        return no == 0 ? 0 : no % 10 +
                sumDigits(no / 10);
    }

    /**
     * Show debug Error Message into logcat
     *
     * @param TAG
     * @param msg
     */
    public static void showErrorLog(String TAG, String msg) {
        if (ISDEBUGGING) {
            Log.e(TAG, msg);
        }

    }


    /**
     * Show Toast
     *
     * @param mActivity activity instance
     * @param msg       message to show
     */
    public static void showToast(final Activity mActivity, final String msg) {
        try {
            if (mActivity != null && msg != null && msg.length() > 0) {
                mActivity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Showing progress dialog
     *
     * @param msg message to show progress dialog
     */
    public static void showProgressDialog(final Activity mActivity,
                                          final String msg, final boolean isCancelable) {
        try {
            if (mActivity != null && mProgressDialog != null
                    && mProgressDialog.isShowing()) {
                try {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            mProgressDialog = null;
            if (mProgressDialog == null && mActivity != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog = new ProgressDialog(mActivity, R.style.MyAlertDialogStyle);
                        mProgressDialog.setMessage(msg);
                        mProgressDialog.setCancelable(isCancelable);
                    }
                });

            }
            if (mActivity != null && mProgressDialog != null
                    && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hide progress dialog
     */
    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
                mProgressDialog = null;

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        } else {
            showErrorLog(TAG, "mProgressDialog is null");
        }
    }


    /**
     * Down Keyboard
     *
     * @param mActivity activity instance
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void keyboardDown(Activity mActivity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) mActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(inputManager).hideSoftInputFromWindow(Objects.requireNonNull(mActivity.getCurrentFocus())
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    /**
     * to get id of relative layout by passing string
     *
     * @param resourceName
     * @param c
     * @return
     */
    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }


    /**
     * convert hex to decimal
     *
     * @param hex hexadecimal string which has to be converted to decimal
     * @return decimal value
     */
    public static BigInteger hexToBigInteger(String hex) {
        return new BigInteger(hex, 16);
    }

    /**
     * convert the byte[] to hex string
     *
     * @param data data that need to change in string
     * @return string value
     */
    public static String convertToHexString(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

//    //function to show alert dialog box
//    public static void showAlertDialog(String message, final Context context) {
//        AlertDialog.Builder customBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
//        customBuilder.setMessage(message);
//        customBuilder.setNegativeButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
////                showNotificationMessage(message, context);
//            }
//        });
//        AlertDialog dialog = customBuilder.create();
//        dialog.show();
//        Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        if (b != null) {
//            b.setTextColor(context.getResources().getColor(R.color.colorAccent));
//        }
//    }
//
//
//

    /**
     * method used to convert hex string to byte array
     *
     * @param s input string
     * @return byte array
     */
    public static byte[] convertHexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * check string
     *
     * @param string input string
     * @return true/false
     */
    public static boolean verifyStringValue(String string) {
        if (string.trim().isEmpty())
            return false;
        return true;
    }


    /**
     * to get system date and time
     *
     * @return String
     */
    public static String getSystemDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM, dd yyyy HH:mm:ss", Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * convert the byte[] to hex string
     *
     * @param data input byte array
     * @return String value
     */
    public static String convertToHexStringEthereum(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return "0x" + buf.toString();
    }


    /**
     * Method use to retrieve json object from assets
     *
     * @param context activity instance
     * @return string value
     */
    public static String getAssetJsonData(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("currency_detail.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        return json;

    }


    /**
     * Set Status Bar Color
     *
     * @param mActivity activity instance
     * @param color     pass color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity mActivity, int color, boolean isWhite) {
        Window window = mActivity.getWindow();
        View decor = mActivity.getWindow().getDecorView();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
        if (isWhite) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
// We want to change tint color to white again.
// You can also record the flags in advance so that you can turn UI back completely if
// you have set other flags before, such as translucent or full screen.
            decor.setSystemUiVisibility(0);
        }
    }



    /**
     * check network Connection Available if not then its return
     * true/false value and show network connectivity dialog
     *
     * @param activity activity instance
     * @return true/false
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNetworkConnectionAvailable(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            AndroidAppUtils.showLog("Network", "Connected");
            return true;
        } else {
//            checkNetworkConnection(activity);
            AndroidAppUtils.showLog("Network", "Not Connected");
            return false;
        }
    }


    /**
     * check network Connection Available if not than
     * return true/false value
     *
     * @param activity activity instance
     * @return true/false
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNetworkConnectionAvailableLanding(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            AndroidAppUtils.showLog("Network", "Connected");
            return true;
        } else {
            AndroidAppUtils.showLog("Network", "Not Connected");
            return false;
        }
    }




}