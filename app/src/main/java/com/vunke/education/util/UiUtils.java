package com.vunke.education.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.vunke.education.base.Configs;
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
    private static final String TAG = "UiUtils";
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

    /**
     * 启动应用
     */
    public static void StartAPP(String packageName,String ClassName,int implementId,Context context){
        if (TextUtils.isEmpty(packageName)&&TextUtils.isEmpty(ClassName)){
            WorkLog.i(TAG,"StartAPP: start app failed ,get appinfo error");
            showToast("启动应用失败：获取应用失败",context);
        }else  if (!TextUtils.isEmpty(packageName)&&TextUtils.isEmpty(ClassName)){
            WorkLog.i(TAG, "StartAPP: get class is null,startApk");
            if (isPkgInstalled(context,packageName)){
                WorkLog.i(TAG, "StartAPP: app is installed,startApk");
                StartAPK(packageName,implementId,context);
            }else{
                WorkLog.i(TAG, "StartAPP: app not is installed");
                showToast("应用未安装",context);
            }
        }else if (TextUtils.isEmpty(packageName)&&!TextUtils.isEmpty(ClassName)){
            WorkLog.i(TAG, "StartAPP: get packageName is null,start locat Activity");
            StartLocatActivity(ClassName,implementId,context);
        }else  if (!TextUtils.isEmpty(packageName)&&!TextUtils.isEmpty(ClassName)){
            if (isPkgInstalled(context,packageName)){
                WorkLog.i(TAG, "StartAPP: app is installed,startApk");
                StartActivity(packageName,ClassName,implementId,context);
            }else{
                WorkLog.i(TAG, "StartAPP: app not is installed");
                showToast("应用未安装",context);
            }
        }
    }
    /**
     * 根据包名启动APK
     *
     * @param packageName
     * @param context
     */
    public static void StartAPK(String packageName,int implementId, Context context) {
        if (TextUtils.isEmpty(packageName)) {
            WorkLog.e(TAG, "packageName is null");
            return;
        }
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = context.getPackageManager();
            List apps = pManager.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = (ResolveInfo) apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                WorkLog.i(TAG, "start package:"+packageName+",start package launcher:"+ className);
                Configs.intent= new Intent(Intent.ACTION_MAIN);
                ComponentName cn = new ComponentName(packageName, className);
                Configs.intent.setComponent(cn);
                Configs.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent_putExtra(implementId);
                context.startActivity(Configs.intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断应用是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isPkgInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            WorkLog.i(TAG, "isPkgInstalled: get packageName is null");
            return false;
        }
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return info != null;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 根据包名和类名启动APK
     *
     * @param packageName
     * @param ClassName
     * @param context
     */
    public static void StartActivity(String packageName,String ClassName,int implementId, Context context) {
        if (TextUtils.isEmpty(packageName)) {
            WorkLog.e(TAG, "packageName is null");
            showToast("启动失败",context);
            return ;
        }
        if (TextUtils.isEmpty(ClassName)){
            WorkLog.e(TAG, "className is null");
            StartAPK(packageName,implementId,context);
            return ;
        }
        WorkLog.i(TAG, "StartActivity: get packageName;"+packageName);
        WorkLog.i(TAG, "StartActivity: get className;"+ClassName);
        Configs.intent = new Intent();
        Configs.intent.setClassName(packageName, ClassName);
//        方法一：
//        if (context.getPackageManager().resolveActivity(intent, 0) == null) {
//            // 说明系统中不存在这个activity
//        }
//        方法二：
        if(Configs.intent.resolveActivity(context.getPackageManager()) != null) {
            // 说明系统中不存在这个activity
            WorkLog.i(TAG, "StartActivity: get Activity success");
            Configs.intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cn = new ComponentName(packageName, ClassName);
            Configs.intent.setComponent(cn);
            Configs.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent_putExtra(implementId);
            context.startActivity(Configs.intent);
        }else{
            showToast("启动失败,获取本地页面失败",context);
            WorkLog.i(TAG, "StartActivity: startActivity error ,get Activity failed");
        }
//        方法三：
//        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
//        if (list.size() == 0) {
//            // 说明系统中不存在这个activity
//        }
    }

    /**
     * 启动本地Activity
     * @param ClassName
     * @param context
     */
    public static void StartLocatActivity(String ClassName,int implementId,Context context){
        if (TextUtils.isEmpty(ClassName)){
            WorkLog.e(TAG, "className is null");
            showToast("启动本地页面失败",context);
            return ;
        }
        Configs.intent = new Intent();
        Configs.intent.setClassName(context, ClassName);
        if(Configs.intent.resolveActivity(context.getPackageManager()) != null) {
            Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent_putExtra(implementId);
            context.startActivity(Configs.intent);
        }
    }

    private static void intent_putExtra(int implementId) {
        if (implementId!=-1){
            Configs.intent.putExtra("infoId",implementId);
        }
    }
}
