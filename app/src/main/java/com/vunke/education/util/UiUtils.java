package com.vunke.education.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.vunke.education.log.WorkLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuxi on 2017/3/9.
 */
public class UiUtils {
    /**
     *  吐司
     * @param string
     * @param context
     */
    public static void showToast(String string,Context context) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
    /**
     * @param context
     * @return versionName 版本名字
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            String pkName = context.getPackageName();
            versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return versionName;
    }
    /**
     * 获取版本信息
     * @param context
     * @return packageName+versionName+versionCode
     */
    @Nullable
    public static String getVersionInfo(Context context){
        try {

            String pkName = context.getPackageName();

            String versionName = context.getPackageManager().getPackageInfo(

                    pkName, 0).versionName;

            int versionCode = context.getPackageManager()

                    .getPackageInfo(pkName, 0).versionCode;

            return pkName + "   " + versionName + "  " + versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param context
     * @return versionCode 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            String pkName = context.getPackageName();
            versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return versionCode;
    }
    /**
     * 获取当前时间
     *
     * @return String 2016-6-12 10:53:05:888
     */
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss:SS");
        Date date = new Date(System.currentTimeMillis());
        String time = dateFormat.format(date);
        return time;
    }
    /**
     * 获取当前时间
     *
     * @return String 2016-6-12_10:53
     */
    public static String getDateTime2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd_HH:mm:ss:SS");
        Date date = new Date(System.currentTimeMillis());
        String time = dateFormat.format(date);
        return time;
    }

    /**
     * 根据两个长整形数，判断是否是同一天
     *
     * @param lastDay
     * @param thisDay
     * @return
     */
//    public static boolean isSameToday(long lastDay, long thisDay) {
//        Time time = new Time();
//        time.set(lastDay);
//
//        int thenYear = time.year;
//        int thenMonth = time.month;
//        int thenMonthDay = time.monthDay;
//        time.set(thisDay);
//        return (thenYear == time.year) && (thenMonth == time.month)
//                && (thenMonthDay == time.monthDay);
//    }
    /**
     * 使用系统工具类判断是否是今天 是今天就显示发送的小时分钟 不是今天就显示发送的那一天
     * */
    public static String getDate(Context context, long when) {
        String date = null;
        if (DateUtils.isToday(when)) {
            date = DateFormat.getTimeFormat(context).format(when);
        } else {
            date = DateFormat.getDateFormat(context).format(when);
        }
        return date;
    }

    /**
     * 防止按钮重复点击
     *
     * @param ts
     * @return
     */
    private static long lastClickTime = 0;
    public static boolean isFastDoubleClick(float ts) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        if (0 < timeD && timeD < ts * 1000) {
            return true;
        }
        return false;
    }

    /**
     * Map转JSON
     * @param params
     * @return
     */
    @Nullable
    public static String Map_toJSONObject(Map<String, Object> params) {
        try {
            final JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                try {
                    jsonObject.put(param.getKey(), param.getValue());
                } catch (JSONException e) {
                    WorkLog.e("tag", "JSON错误");
                    e.printStackTrace();
                }
            }
            // params.put("json", jsonObject);
            if (jsonObject.length() > 0 && jsonObject != null) {
                WorkLog.i("json", jsonObject.toString());
                return jsonObject.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 判断服务是否运行
     *
     * @param context
     * @param clazz
     *            要判断的服务的class
     * @return
     */
    public static boolean isServiceRunning(Context context,
                                           Class<? extends Service> clazz) {
        try {
            ActivityManager manager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(100);
            for (int i = 0; i < services.size(); i++) {
                String className = services.get(i).service.getClassName();
                if (className.equals(clazz.getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
