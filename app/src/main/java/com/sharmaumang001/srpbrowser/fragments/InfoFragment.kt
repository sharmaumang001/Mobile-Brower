package com.sharmaumang001.srpbrowser.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sharmaumang001.srpbrowser.R
@SuppressLint("SetTextI18n")
class InfoFragment : Fragment() {
    private lateinit var osInfo: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        osInfo = view.findViewById(R.id.osInfo)
        val modelName = Build.MODEL
        val os = Build.VERSION.RELEASE
        val patch = Build.ID
        osInfo.text = "Android: $os; $modelName, $patch"
        return view
    }
}