package com.capstone.travguide.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.travguide.TravisActivity
import com.capstone.travguide.databinding.FragmentTravisLoginBinding


class TravisLoginFragment : Fragment() {

    private lateinit var binding: FragmentTravisLoginBinding
    private val travisActivity: TravisActivity get() = (activity as TravisActivity)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTravisLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoogleLogin.setOnClickListener {
            travisActivity.googleSignIn()
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as TravisActivity).toolBarVisible(false)
    }
}