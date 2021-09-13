package org.rsschool.rsandroidtask4.ui.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.databinding.ModifyAnimalFragmentBinding
import org.rsschool.rsandroidtask4.ui.main.MainViewModel

private const val ARG_TITLE = "title"
private const val ARG_BUTTON_CAPTION = "button_caption"
private const val ARG_ITEM = "item"

@AndroidEntryPoint
class ModifyAnimalsFragment : Fragment() {

    private var _binding: ModifyAnimalFragmentBinding? = null
    private val gson = GsonBuilder().create()
    private val binding
        get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by viewModels()

    private var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ModifyAnimalFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animal = gson.fromJson(arguments?.getString(ARG_ITEM), Animal::class.java)
        views {
            animal?.let {
                name.editText?.setText(animal?.name)
                age.editText?.setText(animal?.age.toString())
                breed.editText?.setText(animal?.breed)
            }
            toolbarTitle.text = arguments?.getString(ARG_TITLE)
            modifyButton.text = arguments?.getString(ARG_BUTTON_CAPTION)
            toolbar.setNavigationOnClickListener { goBack() }
            modifyButton.setOnClickListener {
                save(makeAnimal(animal))
            }
        }
    }

    private fun ModifyAnimalFragmentBinding.makeAnimal(animal: Animal?) =
        when (animal) {
            null -> {
                Animal(
                    name = name.editText?.text.toString(),
                    age = age.editText?.text.toString().toDouble(),
                    breed = breed.editText?.text.toString()
                )
            }
            else -> {
                Animal(
                    id = animal.id,
                    name = name.editText?.text.toString(),
                    age = age.editText?.text.toString().toDouble(),
                    breed = breed.editText?.text.toString()
                )
            }
        }


    private fun goBack() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun save(animal: Animal) {
        viewModel.save(animal)
        goBack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, captionButton: String, item: Animal? = null) =
            ModifyAnimalsFragment().apply {
                arguments = bundleOf(
                    ARG_TITLE to title,
                    ARG_BUTTON_CAPTION to captionButton,
                    ARG_ITEM to gson.toJson(item)
                )
            }
    }

    private fun <T> views(block: ModifyAnimalFragmentBinding.() -> T): T? = binding.block()
}