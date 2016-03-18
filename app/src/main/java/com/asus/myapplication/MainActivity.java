package com.asus.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "spotlight";

    public static final String BADGE_COLUMN_PKGNAME = "package_name";
    public static final String BADGE_COLUMN_CLZNAME = "class_name";
    public static final String BADGE_COLUMN_COUNT = "count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void button(View view) {

    }

    public static class PrefsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        class BadgeInfo {
            private String packageName;
            private String className;
            BadgeInfo(String packageName, String className) {
                this.packageName = packageName;
                this.className = className;
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }

        @Override
        public void onResume() {
            super.onResume();

            PreferenceCategory listCategory = (PreferenceCategory) getPreferenceScreen().findPreference("prefs_category_support_list");
            listCategory.removeAll();

            Set<BadgeInfo> set = getBadgeSet();

            for (BadgeInfo info : set) {
                CheckBoxPreference cbPrefs = new CheckBoxPreference(getActivity());
                cbPrefs.setKey(info.packageName);
                cbPrefs.setTitle(info.packageName);
                cbPrefs.setSummary("請打開權限好嗎");
//                cbPrefs.setChecked(false);
                cbPrefs.setPersistent(false);
                cbPrefs.setOnPreferenceChangeListener(this);
                listCategory.addPreference(cbPrefs);
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();

            if (preference instanceof CheckBoxPreference) {
                boolean isCheck = (Boolean) newValue;
//                ((CheckBoxPreference) preference).setChecked(isCheck);
                getActivity().getSharedPreferences("com.asus.launcher_badge_preferences", Context.MODE_PRIVATE).edit().putBoolean(key, !isCheck).commit();
            }

            return false;
        }

        private Set<BadgeInfo> getBadgeSet() {
            Set<BadgeInfo> set = new HashSet<>();
            Cursor c = getActivity().getContentResolver().query(
                    Uri.parse("content://com.android.launcher2.asus.settings/badge"),
                    new String[]{"package_name", "class_name"},
                    null, null, null);
            while (c.moveToNext()) {
                set.add(new BadgeInfo(
                        c.getString(c.getColumnIndex("package_name")),
                        c.getString(c.getColumnIndex("class_name"))));
            }
            return set;
        }
    }
}

