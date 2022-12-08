package com.capstone.travguide.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.travguide.R
import com.capstone.travguide.TravisActivity
import com.capstone.travguide.databinding.FragmentTravisHomePageBinding
import java.util.*


class TravisHomePageFragment : Fragment() {

    private lateinit var binding: FragmentTravisHomePageBinding
    private lateinit var onSpeechTextResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTravisHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onSpeechTextResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val result: ArrayList<String> =
                    it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                binding.tvSpeechText.text = Objects.requireNonNull(result)[0]
                binding.tvSpeechText.visibility = View.VISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivTextMic.setOnClickListener {
            speechToTextConvert()
        }

        binding.btnLocate.setOnClickListener {
            findNavController().navigate(R.id.action_travisHomePageFragment_to_travisLocationsListFragment)
        }

        binding.btnGetSuggestions.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Here are the Suggestions : ")
            builder.setView(R.layout.layout_suggestions_dialog)
            builder.setCancelable(true)
            builder.setPositiveButton("Okay") { dialog: DialogInterface?, _: Int ->
                dialog?.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()

            alertDialog.show()
        }

        binding.btnPrivacyPolicy.setOnClickListener {
            findNavController().navigate(R.id.action_travisHomePageFragment_to_travisPrivacyPolicyFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as TravisActivity).toolBarVisible()
    }

    private fun speechToTextConvert() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")

        try {
            onSpeechTextResult.launch(intent)
        } catch (e: Exception) {
            Toast
                .makeText(
                    this@TravisHomePageFragment.context, " " + e.message,
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}