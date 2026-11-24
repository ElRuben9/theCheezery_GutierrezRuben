package gutierrez.ruben.thecheezery_gutierrezruben

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.FragmentAddComboBinding
import kotlinx.coroutines.launch

class AddComboFragment : Fragment() {
    private var _binding: FragmentAddComboBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity())[CheezeryViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddComboBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonBack.setOnClickListener { findNavController().popBackStack() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect { products ->
                    binding.productChecks.removeAllViews()
                    products.forEach { product ->
                        binding.productChecks.addView(CheckBox(requireContext()).apply {
                            text = product.name
                            tag = product.id
                        })
                    }
                }
            }
        }

        binding.buttonSaveCombo.setOnClickListener {
            val name = binding.editComboName.text?.toString().orEmpty().trim()
            val price = binding.editComboPrice.text?.toString()?.toFloatOrNull()
            val productIds = (0 until binding.productChecks.childCount).mapNotNull { index ->
                val check = binding.productChecks.getChildAt(index) as? CheckBox
                if (check?.isChecked == true) check.tag as? Int else null
            }

            binding.textAddComboError.text = when {
                name.isBlank() -> "Escribe el nombre del combo."
                price == null || price <= 0f -> "Escribe un precio válido."
                productIds.isEmpty() -> "Selecciona al menos un producto."
                else -> ""
            }
            if (binding.textAddComboError.text.isNotEmpty()) return@setOnClickListener

            viewModel.saveCombo(name, price!!, productIds) { saved ->
                if (_binding == null) return@saveCombo
                if (saved) {
                    Toast.makeText(requireContext(), "Combo guardado", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    binding.textAddComboError.text = "No se pudo guardar el combo."
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
