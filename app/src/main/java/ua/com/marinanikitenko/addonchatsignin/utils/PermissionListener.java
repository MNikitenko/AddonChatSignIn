package ua.com.marinanikitenko.addonchatsignin.utils;

/**
 * Created by Marina on 07.07.2017.
 */

public interface PermissionListener {
    void onPermissionGranted(int requestCode);
    void onPermissionDenied(int requestCode);
}
