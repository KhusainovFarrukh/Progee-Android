package kh.farrukh.progee.ui.languages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import kh.farrukh.progee.R
import kh.farrukh.progee.databinding.FragmentLanguagesBinding

/**
 *Created by farrukh_kh on 6/21/22 10:40 AM
 *kh.farrukh.progee.ui.languages
 **/
class LanguagesFragment : Fragment(R.layout.fragment_languages) {

    private val binding by viewBinding(FragmentLanguagesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}