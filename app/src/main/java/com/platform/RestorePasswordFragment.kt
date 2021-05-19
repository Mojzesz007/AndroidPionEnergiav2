package com.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.platform.databinding.FragmentRestorePasswordBinding

class RestorePasswordFragment : Fragment() {
    private var binding: FragmentRestorePasswordBinding? = null
    private var mText: String? = null
    private var mListener: OnFragmentInteractionListener? = null
    private var editTextFragment: EditText? = null
    private val buttonFragment: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestorePasswordBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        editTextFragment = binding!!.RPFLoginTextTI
        (editTextFragment as TextInputEditText).setText(mText)
        (editTextFragment as TextInputEditText).requestFocus()
        binding!!.RPFForgotTV.setOnClickListener { it: View? ->
            val sendBackText = (editTextFragment as TextInputEditText).text.toString()
            sendBack(sendBackText)
        }
        val test1 = 0
        //test1.split()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mText = requireArguments().getString(TEXT)
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun sendBack(sendBackText: String?) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(sendBackText)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentInteractionListener) {
            context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(sendBackText: String?)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val TEXT = "text"

        // TODO: Rename and change types and number of parameters
        fun newInstance(text: String?): RestorePasswordFragment {
            val fragment = RestorePasswordFragment()
            val args = Bundle()
            args.putString(TEXT, text)
            fragment.arguments = args
            return fragment
        }
    }
}