package com.capstone.travguide.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.travguide.databinding.FragmentTravisPrivacyPolicyBinding

class TravisPrivacyPolicyFragment : Fragment() {

    private lateinit var binding: FragmentTravisPrivacyPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTravisPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wvPrivacyPolicy.loadUrl("https://www.privacypolicies.com/live/179523f3-5850-4bf8-8875-abbe0a5ef318")
    }
}