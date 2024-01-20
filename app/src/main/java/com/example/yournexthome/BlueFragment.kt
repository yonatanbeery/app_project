package com.example.yournexthome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class BlueFragment : Fragment() {
    var textView: TextView? = null
    var title: String? = null
    val TITLE = "title"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_blue, container, false)
        textView = view.findViewById(R.id.blueFragmentTitle)
        textView?.text = title ?: "assign a title"

        return view
    }

    fun newInstance(title: String): BlueFragment? {
        return BlueFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
            }
        }
    }
}