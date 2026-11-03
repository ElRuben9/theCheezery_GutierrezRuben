package gutierrez.ruben.thecheezery_gutierrezruben

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.FragmentCartBinding
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.RowCartBinding

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { ViewModelProvider(requireActivity())[CheezeryViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textCartTitle.typeface = resources.getFont(R.font.hello_bunny)
        binding.buttonBack.setOnClickListener { findNavController().popBackStack() }
        showCartItems()
    }

    private fun showCartItems() {
        binding.cartItems.removeAllViews()
        binding.textEmptyCart.visibility = if (viewModel.cart.isEmpty()) View.VISIBLE else View.GONE
        binding.cartScroll.visibility = if (viewModel.cart.isEmpty()) View.GONE else View.VISIBLE
        binding.textCartTotal.text = "$${"%.1f".format(viewModel.total())}"

        viewModel.cart.forEachIndexed { index, product ->
            val row = RowCartBinding.inflate(layoutInflater, binding.cartItems, false)
            row.textCartProductName.text = product.name
            row.textCartProductPrice.text = "$${"%.1f".format(product.price)}"
            row.buttonRemove.setOnClickListener {
                viewModel.removeFromCart(index)
                showCartItems()
            }
            binding.cartItems.addView(row.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
