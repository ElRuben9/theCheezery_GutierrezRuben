package gutierrez.ruben.thecheezery_gutierrezruben

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity())[CheezeryViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonCart.text = "🛒 ${viewModel.cart.size}"
        binding.buttonCart.setOnClickListener { findNavController().navigate(R.id.action_menu_to_cart) }
        binding.buttonHotDrinks.setOnClickListener { openCategory("Hot drinks") }
        binding.buttonColdDrinks.setOnClickListener { openCategory("Cold drinks") }
        binding.buttonSalties.setOnClickListener { openCategory("Salties") }
        binding.buttonSweets.setOnClickListener { openCategory("Sweets") }
        binding.buttonCombos.setOnClickListener { openCategory("All products") }
        binding.buttonAddProduct.setOnClickListener {
            Toast.makeText(requireContext(), "Esta opción no forma parte de la práctica.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCategory(category: String) {
        val args = Bundle().apply { putString("category", category) }
        findNavController().navigate(R.id.action_menu_to_products, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
