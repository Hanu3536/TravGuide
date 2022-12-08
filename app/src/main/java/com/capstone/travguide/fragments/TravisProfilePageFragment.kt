package com.capstone.travguide.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.travguide.TravisActivity
import com.capstone.travguide.databinding.FragmentTravisProfilePageBinding

class TravisProfilePageFragment : Fragment() {

    private lateinit var binding: FragmentTravisProfilePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTravisProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as TravisActivity).checkAndGetGoogleUser()?.let { user ->
            binding.apply {
                Glide.with(requireContext())
                    .load(user.photoUrl)
                    .into(civProfileImage)

                tvEmailId.text = user.email
                tvUniqueId.text = user.id
                tvFullName.text = user.displayName
                tvLastLogin.text = "12:00:00 PM"
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        menu.clear()
    }
}