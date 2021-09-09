package org.rsschool.rsandroidtask4.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.rsschool.rsandroidtask4.R
import org.rsschool.rsandroidtask4.databinding.MainFragmentBinding
import org.rsschool.rsandroidtask4.ui.addAnimals.AddAnimalsFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views {
            addAnimal.setOnClickListener { showAddAnimalsFragment() }
        }
    }

    private fun showAddAnimalsFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack("addAnimals")
            ?.replace(
                R.id.container, AddAnimalsFragment.newInstance(
                    title = getString(R.string.add_animal_title),
                    captionButton = getString(R.string.add_animal_button_caption)
                )
            )
            ?.commit()
    }

    private fun <T> views(block: MainFragmentBinding.() -> T): T? = binding.block()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}