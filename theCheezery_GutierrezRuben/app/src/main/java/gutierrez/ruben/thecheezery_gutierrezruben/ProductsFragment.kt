package gutierrez.ruben.thecheezery_gutierrezruben

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.FragmentProductsBinding
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.RowProductBinding

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity())[CheezeryViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val category = requireArguments().getString("category") ?: "Hot drinks"
        binding.textCategoryTitle.text = if (category == "All products") "Combos" else category
        binding.textCategoryTitle.typeface = resources.getFont(R.font.hello_bunny)
        binding.buttonBack.setOnClickListener { findNavController().popBackStack() }
        binding.buttonCart.text = "🛒 ${viewModel.cart.size}"
        binding.buttonCart.setOnClickListener { findNavController().navigate(R.id.action_products_to_cart) }

        val products = if (category == "All products") {
            ProductCatalog.products
        } else {
            ProductCatalog.products.filter { it.category == category }
        }

        products.forEach { product ->
            val row = RowProductBinding.inflate(layoutInflater, binding.productList, false)
            row.textProductName.text = product.name
            row.textProductDescription.text = product.description
            row.textProductPrice.text = "$${"%.1f".format(product.price)}"

            val imageId = resources.getIdentifier(product.imageName, "drawable", requireContext().packageName)
            if (imageId != 0) row.imageProduct.setImageResource(imageId)

            row.buttonAdd.setOnClickListener {
                viewModel.addToCart(product)
                binding.buttonCart.text = "🛒 ${viewModel.cart.size}"
                Toast.makeText(requireContext(), "Agregado al carrito", Toast.LENGTH_SHORT).show()
            }
            binding.productList.addView(row.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
