package com.sharmaumang001.srpbrowser.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sharmaumang001.srpbrowser.R

class InfoFragment : Fragment() {
    lateinit var osInfo: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        osInfo = view.findViewById(R.id.osInfo)
        val modelName= Build.MODEL
        val os = Build.VERSION.RELEASE
        val patch = Build.ID
        osInfo.text = "Android: $os; $modelName, $patch"
        return view
    }
}