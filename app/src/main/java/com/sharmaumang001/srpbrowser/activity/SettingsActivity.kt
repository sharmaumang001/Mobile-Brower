package com.sharmaumang001.srpbrowser.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.sharmaumang001.srpbrowser.R
import com.sharmaumang001.srpbrowser.database.DatabaseHelper
import com.sharmaumang001.srpbrowser.database.FunctionsBookmark
import com.sharmaumang001.srpbrowser.database.FunctionsHistory.ClearData

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<ImageView>(R.id.backToMain).setOnClickListener {
            finish()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener{

        var sharedPreferences: SharedPreferences? = null
        private val SHARED_PREF = "APP_SHARED_PREF"

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            sharedPreferences = context?.getSharedPreferences(
                    SHARED_PREF,
                    Context.MODE_PRIVATE
            )

            findPreference<Preference>("clear_data")?.onPreferenceClickListener = this
            findPreference<Preference>("about_app")?.onPreferenceClickListener = this

            findPreference<SwitchPreferenceCompat>("adBlock_toggle")
                    ?.onPreferenceChangeListener = this
        }

        override fun onPreferenceClick(preference: Preference?): Boolean {
            if (preference != null) {

                val intent:Intent
                when (preference.key) {
                    "clear_data" -> {
                        context?.let {
                            ClearData(it).execute()
                            FunctionsBookmark.Delete(it).execute()
                            val myDb = DatabaseHelper(it)
                            myDb.deleteData()
                            Toast.makeText(context, "Data cleared", Toast.LENGTH_SHORT).show()
                        }
                    }
                    "about_app" -> {
                        intent = Intent(context, Info::class.java)
                        startActivity(intent)
                    }
                }
            }
            return true
        }

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            if (preference is SwitchPreferenceCompat) {
                if (preference.key == "adBlock_toggle") {
                    if (preference.isChecked) {
                        Toast.makeText(context, "AdBlock Disabled", Toast.LENGTH_SHORT).show()
                        sharedPreferences?.edit()?.putBoolean("isAdBlockEnabled", false)?.apply()
                    }
                    else {
                        Toast.makeText(context, "AdBlock Enabled", Toast.LENGTH_SHORT).show()
                        sharedPreferences?.edit()?.putBoolean("isAdBlockEnabled", true)?.apply()
                    }
                }
            }
            return true
        }
    }
}