/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.vicious;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

/**
 * Performance Settings
 */
public class PerformanceSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "PerformanceSettings";

    private static final String USE_DITHERING_PREF = "pref_use_dithering";

    private static final String USE_DITHERING_PERSIST_PROP = "persist.sys.use_dithering";

    private static final String USE_DITHERING_DEFAULT = "1";

    private static final String USE_16BPP_ALPHA_PREF = "pref_use_16bpp_alpha";

    private static final String USE_16BPP_ALPHA_PROP = "persist.sys.use_16bpp_alpha";

    private static final String USE_HARDWARE_ACCELERATION_PREF = "pref_use_hardware_acceleration";

    private static final String USE_HARDWARE_ACCELERATION_PROP = "debug.sf.hw";

    private static final String USE_HARDWARE_ACCELERATION_DEFAULT = "0";

    private static final String USE_ERROR_CHECKING_TOGGLE_PREF = "pref_use_error_checking_toggle";

    private static final String USE_ERROR_CHECKING_TOGGLE_PROP = "ro.kernel.android.checkjni";

    private static final String USE_ERROR_CHECKING_TOGGLE_DEFAULT = "1";

    private CheckBoxPreference mUseDitheringPref;

    private CheckBoxPreference mUse16bppAlphaPref;

    private CheckBoxPreference mUseHardwareAccelerationPref;

    private CheckBoxPreference mUseErrorCheckingTogglePref;

    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getPreferenceManager() != null) {

            addPreferencesFromResource(R.xml.performance_settings);

            PreferenceScreen prefSet = getPreferenceScreen();

            mUseDitheringPref = (CheckBoxPreference) prefSet.findPreference(USE_DITHERING_PREF);
            mUse16bppAlphaPref = (CheckBoxPreference) prefSet.findPreference(USE_16BPP_ALPHA_PREF);
            mUseHardwareAccelerationPref = (CheckBoxPreference) prefSet.findPreference(USE_HARDWARE_ACCELERATION_PREF);
            mUseErrorCheckingTogglePref = (CheckBoxPreference) prefSet.findPreference(USE_ERROR_CHECKING_TOGGLE_PREF);

            String useDithering = SystemProperties.get(USE_DITHERING_PERSIST_PROP,
                    USE_DITHERING_DEFAULT);
            mUseDitheringPref.setChecked("1".equals(useDithering));

            String use16bppAlpha = SystemProperties.get(USE_16BPP_ALPHA_PROP, "0");
            mUse16bppAlphaPref.setChecked("1".equals(use16bppAlpha));

            String useHardwareAcceleration = SystemProperties.get(USE_HARDWARE_ACCELERATION_PROP,
                    USE_HARDWARE_ACCELERATION_DEFAULT);
            mUseHardwareAccelerationPref.setChecked("1".equals(useHardwareAcceleration));

            String useErrorCheckingToggle = SystemProperties.get(USE_ERROR_CHECKING_TOGGLE_PROP,
                    USE_ERROR_CHECKING_TOGGLE_DEFAULT);
            mUseErrorCheckingTogglePref.setChecked("0".equals(useErrorCheckingToggle));

            /* Display the warning dialog */
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle(R.string.performance_settings_warning_title);
            alertDialog.setMessage(getResources().getString(R.string.performance_settings_warning));
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    getResources().getString(com.android.internal.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

            alertDialog.show();
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mUseDitheringPref) {
            SystemProperties.set(USE_DITHERING_PERSIST_PROP,
                    mUseDitheringPref.isChecked() ? "1" : "0");
        } else if (preference == mUse16bppAlphaPref) {
            SystemProperties.set(USE_16BPP_ALPHA_PROP,
                    mUse16bppAlphaPref.isChecked() ? "1" : "0");
        } else if (preference == mUseHardwareAccelerationPref) {
            SystemProperties.set(USE_HARDWARE_ACCELERATION_PROP,
                    mUseHardwareAccelerationPref.isChecked() ? "1" : "0");
        } else if (preference == mUseErrorCheckingTogglePref) {
            SystemProperties.set(USE_ERROR_CHECKING_TOGGLE_PROP,
                    mUseErrorCheckingTogglePref.isChecked() ? "1" : "0");
        } else {
            // If we didn't handle it, let preferences handle it.
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        return true;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {

        return false;
    }

}
