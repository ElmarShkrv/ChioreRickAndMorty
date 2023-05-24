package com.example.chiorerickandmorty.ui.fragments.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.chiorerickandmorty.R
import com.example.chiorerickandmorty.adapter.homeadapters.HomeRvAdapter
import com.example.chiorerickandmorty.databinding.FragmentFilterBinding
import com.example.chiorerickandmorty.extensions.getTextButtonChecked
import com.example.chiorerickandmorty.extensions.getTextChipChecked
import com.example.chiorerickandmorty.extensions.setButtonChecked
import com.example.chiorerickandmorty.extensions.setChipChecked
import com.example.chiorerickandmorty.ui.fragments.homefragment.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            viewModel.filterValue.observe(viewLifecycleOwner) { item ->
                chipgroupStatus.setChipChecked(item[0])
                radiogroupGender.setButtonChecked(item[1])
            }
        }

        binding.apply {
            btnMakeFilter.setOnClickListener {

                if (chipgroupStatus.getTextChipChecked()
                        .isNotEmpty() && radiogroupGender.getTextButtonChecked().isNotEmpty()
                ) {
                    viewModel.getByStatusAndGender(
                        chipgroupStatus.getTextChipChecked(),
                        radiogroupGender.getTextButtonChecked(),
                    )
                } else {
                    if (chipgroupStatus.getTextChipChecked().isNotEmpty()) {
                        viewModel.getByStatus(chipgroupStatus.getTextChipChecked())
                    } else {
                        viewModel.getByGender(radiogroupGender.getTextButtonChecked())
                    }
                }

                viewModel.filterValue.value = arrayOf(
                    chipgroupStatus.checkedChipId, radiogroupGender.checkedRadioButtonId
                )

                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }

    }

}