package com.example.kimdoyeop.ourfamilydefence.Save;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KIMDOYEOP on 2016-07-22.
 */
public class SaveLoc {

    String save;
    Context context;

    public SaveLoc(Context context, String save) {
        this.save = save;
        this.context = context;
        SharedPreferences Save = context.getSharedPreferences("Token", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = Save.edit();
        edit.putString("Save", save);
        edit.apply();
    }
}