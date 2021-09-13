package org.rsschool.rsandroidtask4.ui.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.databinding.ModifyAnimalFragmentBinding
import org.rsschool.rsandroidtask4.ui.main.MainViewModel

private const val ARG_TITLE = "title"
private const val ARG_BUTTON_CAPTION = "button_caption"

@AndroidEntryPoint
class ModifyAnimalsFragment : Fragment() {

    private var _binding: ModifyAnimalFragmentBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ModifyAnimalFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views {
            toolbarTitle.text = arguments?.getString(ARG_TITLE)
            modifyButton.text = arguments?.getString(ARG_BUTTON_CAPTION)
            toolbar.setNavigationOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
            modifyButton.setOnClickListener{
                val animal = Animal(
                    name = name.editText?.text.toString(),
                    age = age.editText?.text.toString().toInt(),
                    breed = breed.editText?.text.toString()
                )
                save(animal)
            }
        }
    }

    private fun save(animal: Animal) {
        viewModel.save(animal)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, captionButton: String) =
            ModifyAnimalsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_BUTTON_CAPTION, captionButton)
                }
            }
    }

    private fun <T> views(block: ModifyAnimalFragmentBinding.() -> T): T? = binding.block()
}