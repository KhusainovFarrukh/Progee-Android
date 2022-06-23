package kh.farrukh.progee.ui.create_data

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.progee.R
import kh.farrukh.progee.databinding.FragmentCreateDataBinding

/**
 *Created by farrukh_kh on 6/21/22 10:35 AM
 *kh.farrukh.progee.ui.create_data
 **/
@AndroidEntryPoint
class CreateDataFragment : Fragment(R.layout.fragment_create_data) {

    private val binding by viewBinding(FragmentCreateDataBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}