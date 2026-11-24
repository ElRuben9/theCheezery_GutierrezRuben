package gutierrez.ruben.thecheezery_gutierrezruben

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.FragmentProductsBinding
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.RowProductBinding
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.relation.ComboWithProducts
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(viewModel.products, viewModel.combos) { products, combos -> products to combos }
                    .collect { (products, combos) -> renderProducts(products, combos, category) }
            }
        }
    }

    private fun renderProducts(
        products: List<ProductEntity>,
        combos: List<ComboWithProducts>,
        category: String
    ) {
        binding.productList.removeAllViews()

        if (category == "All products") {
            if (combos.isEmpty()) {
                binding.productList.addView(emptyMessage("Aún no hay combos. Agrega uno desde el menú."))
                return
            }
            combos.forEach { comboWithProducts ->
                val header = TextView(requireContext())
                header.text = "${comboWithProducts.combo.name}  $${"%.1f".format(comboWithProducts.combo.price)}"
                header.textSize = 22f
                header.setTypeface(resources.getFont(R.font.hello_bunny))
                header.setTextColor(resources.getColor(R.color.Pinky, null))
                header.setPadding(0, 16, 0, 8)
                binding.productList.addView(header)
                comboWithProducts.products.forEach { addProductRow(it) }
            }
            return
        }

        val filtered = products.filter { viewModel.categoryOf(it) == category }
        if (filtered.isEmpty()) {
            binding.productList.addView(emptyMessage("No hay productos en esta categoría."))
            return
        }
        filtered.forEach { addProductRow(it) }
    }

    private fun emptyMessage(message: String): TextView = TextView(requireContext()).apply {
        text = message
        textSize = 16f
        setPadding(0, 24, 0, 24)
    }

    private fun addProductRow(product: ProductEntity) {
        val row = RowProductBinding.inflate(layoutInflater, binding.productList, false)
        row.textProductName.text = product.name
        row.textProductDescription.text = ProductCatalog.products
            .firstOrNull { it.name == product.name }?.description.orEmpty()
        row.textProductPrice.text = "$${"%.1f".format(product.price)}"

        val imageName = product.image
        if (!imageName.isNullOrBlank()) {
            val imageId = resources.getIdentifier(imageName, "drawable", requireContext().packageName)
            if (imageId != 0) row.imageProduct.setImageResource(imageId)
        }

        row.buttonAdd.setOnClickListener {
            viewModel.addToCart(product)
            binding.buttonCart.text = "🛒 ${viewModel.cart.size}"
            Toast.makeText(requireContext(), "Agregado al carrito", Toast.LENGTH_SHORT).show()
        }
        binding.productList.addView(row.root)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
