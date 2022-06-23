package kh.farrukh.progee.ui.playground

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kh.farrukh.progee.R
import kh.farrukh.progee.databinding.FragmentPlaygroundBinding

/**
 *Created by farrukh_kh on 6/21/22 10:40 AM
 *kh.farrukh.progee.ui.playground
 **/
@AndroidEntryPoint
class PlaygroundFragment : Fragment(R.layout.fragment_playground) {

    private val binding by viewBinding(FragmentPlaygroundBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}