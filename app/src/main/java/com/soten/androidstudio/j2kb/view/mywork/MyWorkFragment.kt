package com.soten.androidstudio.j2kb.view.mywork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.soten.androidstudio.j2kb.R

class MyWorkFragment : Fragment() {

    private lateinit var myWorkViewModel: MyWorkViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myWorkViewModel = ViewModelProvider(this).get(MyWorkViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_work, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        myWorkViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}