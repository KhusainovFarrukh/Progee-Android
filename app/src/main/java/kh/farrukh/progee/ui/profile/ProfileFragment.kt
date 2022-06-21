package kh.farrukh.progee.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import kh.farrukh.progee.R
import kh.farrukh.progee.databinding.FragmentProfileBinding

/**
 *Created by farrukh_kh on 6/21/22 10:41 AM
 *kh.farrukh.progee.ui.profile
 **/
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}