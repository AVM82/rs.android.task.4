package org.rsschool.rsandroidtask4.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.rsschool.rsandroidtask4.R
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.databinding.MainFragmentBinding
import org.rsschool.rsandroidtask4.repository.room.AnimalsDataBaseRoom
import org.rsschool.rsandroidtask4.ui.AppState
import org.rsschool.rsandroidtask4.ui.adapter.AnimalsAdapter
import org.rsschool.rsandroidtask4.ui.modify.ModifyAnimalsFragment
import org.rsschool.rsandroidtask4.ui.settings.SettingsActivity

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: AnimalsAdapter?
        get() = views { animalsList.adapter as? AnimalsAdapter }
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainFragmentBinding.inflate(inflater).also { _binding = it }

        println("ViewModal -> $viewModel")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views {
            animalsList.adapter = AnimalsAdapter()
            addAnimal.setOnClickListener {
                showFragment(
                    ModifyAnimalsFragment.newInstance(
                        title = getString(R.string.add_animal_title),
                        captionButton = getString(R.string.add_animal_button_caption)
                    )
                )
            }
            settings.setOnClickListener { showSettingsActivity() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.animalsListFlow.collect(::renderAnimalsList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.appState.collect(::renderAppState)
            }
        }
    }

    private fun renderAnimalsList(animals: List<Animal>) {
        adapter?.submitList(animals)
    }

    private fun renderAppState(state: AppState){
        renderEmptyAnimalImage(state.isEmptyAnimalsList)
    }

    private fun renderEmptyAnimalImage(isEmpty: Boolean) {
        views{
            emptyList.isVisible = isEmpty
        }
    }

    private fun showSettingsActivity() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun showFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.commit {
            addToBackStack("addAnimals")
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            replace(R.id.main_container, fragment)
        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T) = binding.block()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
