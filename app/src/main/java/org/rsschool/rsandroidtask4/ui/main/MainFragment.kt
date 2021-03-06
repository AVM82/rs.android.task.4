package org.rsschool.rsandroidtask4.ui.main

import android.content.Intent
import android.os.Bundle
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.rsschool.rsandroidtask4.R
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.databinding.BottomMenuBinding
import org.rsschool.rsandroidtask4.databinding.MainFragmentBinding
import org.rsschool.rsandroidtask4.ui.AppState
import org.rsschool.rsandroidtask4.ui.adapter.AnimalViewHolder
import org.rsschool.rsandroidtask4.ui.adapter.AnimalsAdapter
import org.rsschool.rsandroidtask4.ui.modify.ModifyAnimalsFragment
import org.rsschool.rsandroidtask4.ui.settings.SettingsActivity

@AndroidEntryPoint
class MainFragment : Fragment(), AnimalViewHolder.ItemListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: AnimalsAdapter?
        get() = views { animalsList.adapter as? AnimalsAdapter }
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onClickListener(item: Animal) {
        showFragment(
            ModifyAnimalsFragment.newInstance(
                title = getString(R.string.edit_animal),
                captionButton = getString(R.string.confirm),
                item = item
            )
        )
    }

    override fun onLongClickListener(item: Animal): Boolean {
        renderBottomMenu(item)
        return true
    }

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
            animalsList.adapter = AnimalsAdapter(this@MainFragment)
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.appState.collect(::renderAppState)
            }
        }
    }

    private fun renderAnimalsList(animals: List<Animal>) {
        adapter?.run {
            submitList(animals)
            viewModel.toggleEmptyListImage(animals.isEmpty())
        }
    }

    private fun renderAppState(state: AppState) {
        renderEmptyAnimalImage(state.isEmptyAnimalsList)
    }

    private fun renderEmptyAnimalImage(isEmpty: Boolean) {
        views {
            emptyList.isVisible = isEmpty
        }
    }

    private fun renderBottomMenu(animal: Animal) {

        val bottomMenuBinding = BottomMenuBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomMenuBinding.root)

        bottomMenuBinding.title.text = getString(R.string.delete_animal, animal.name)
        bottomMenuBinding.itemDelete.setOnClickListener {
            viewModel.delete(animal)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun showSettingsActivity() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun showFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.commit {
            addToBackStack("modifyAnimal")
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
