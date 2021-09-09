package org.rsschool.rsandroidtask4.ui.addAnimals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.rsschool.rsandroidtask4.databinding.AddAnimalFragmentBinding

private const val ARG_TITLE = "title"
private const val ARG_BUTTON_CAPTION = "button_caption"

class AddAnimalsFragment : Fragment() {

    private var _binding: AddAnimalFragmentBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AddAnimalFragmentBinding.inflate(inflater).also { _binding = it }
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, captionButton: String) =
            AddAnimalsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_BUTTON_CAPTION, captionButton)
                }
            }
    }

    private fun <T> views(block: AddAnimalFragmentBinding.() -> T): T? = binding.block()
}