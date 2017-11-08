package ua.com.marinanikitenko.addonchatsignin.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.HashMap;

import ua.com.marinanikitenko.addonchatsignin.R;
import ua.com.marinanikitenko.addonchatsignin.activities.MainActivity;
import ua.com.marinanikitenko.addonchatsignin.model.Constants;


/**
 * Created by Marina on 07.07.2017.
 * Helper fro Requests the runtime permissions by user
 */

public class PermissionUtil {
    private String TAG = PermissionUtil.class.getSimpleName() + ":";
    private PermissionListener mListener;
    private Activity mActivity;
    private HashMap<Integer,String> permList = new HashMap<>();

    public PermissionUtil(Activity activity) {
        mActivity = activity;
        permList.put(Constants.PERMISSION_REQUEST_CAMERA, Manifest.permission.CAMERA);
        permList.put(Constants.PERMISSION_REQUEST_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        permList.put(Constants.PERMISSION_READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

     /**
     *Register Listener for tracing/notificate result permission request, notificate MainActivity
     *
     *@param permissionListener
     *
     */
    public void setListener(PermissionListener permissionListener) {
        this.mListener = permissionListener;
    }

    /**
     *Used for check version SDK, request current permission
     *
     * @param permission
     */
    public void checkPermissions(int permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mActivity, permList.get(permission)) != PackageManager.PERMISSION_GRANTED) {
                // permission has not been granted.
                requestPermission(permission);
            } else {
                //permissions is already available
                mListener.onPermissionGranted(permission);
                Toast.makeText(mActivity, mActivity.getString(R.string.perm_success) , Toast.LENGTH_SHORT).show();
            }
        } else {
            return;
        }
    }


    /**
     * Requests the  permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     *
     * @param permission
     */
    private void requestPermission(int permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
               permList.get(permission))) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            ActivityCompat.requestPermissions(mActivity, new String[]{ permList.get(permission)}, permission);
        } else {
            //permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(mActivity, new String[]{ permList.get(permission)}, permission);
        }
    }

    // Callback received when a permissions request has been completed.
    public void getOnRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, MainActivity mainActivity) {
        switch (requestCode) {
            case Constants.PERMISSION_REQUEST_CAMERA:
                // Received permission result for camera permission.
                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has been granted
                    mListener.onPermissionGranted(requestCode);
                    Toast.makeText(mActivity, mActivity.getString(R.string.perm_success) , Toast.LENGTH_SHORT).show();

                } else {
                    // Camera permission has no granted
                    mListener.onPermissionDenied(requestCode);
                    Toast.makeText(mActivity, mActivity.getString(R.string.perm_denied) , Toast.LENGTH_SHORT).show();
                }
                break;


            case Constants.PERMISSION_REQUEST_LOCATION :
                // Received permission result for location.
                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // location permission has been granted
                    mListener.onPermissionGranted(requestCode);
                    Toast.makeText(mActivity,  mActivity.getString(R.string.perm_success) , Toast.LENGTH_SHORT).show();

                } else {
                    // location permission no granted
                    mListener.onPermissionDenied(requestCode);
                    Toast.makeText(mActivity,  mActivity.getString(R.string.perm_denied), Toast.LENGTH_SHORT).show();
                }
                break;


            case Constants.PERMISSION_READ_EXTERNAL_STORAGE :
                // Received permission result for location.
                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // location permission has been granted
                    mListener.onPermissionGranted(requestCode);
                    Toast.makeText(mActivity,  mActivity.getString(R.string.perm_success) , Toast.LENGTH_SHORT).show();

                } else {
                    // read external storage permission no granted
                    mListener.onPermissionDenied(requestCode);
                    Toast.makeText(mActivity,  mActivity.getString(R.string.perm_denied) , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
