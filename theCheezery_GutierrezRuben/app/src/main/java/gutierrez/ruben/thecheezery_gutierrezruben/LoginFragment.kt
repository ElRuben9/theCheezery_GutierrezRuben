package gutierrez.ruben.thecheezery_gutierrezruben

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gutierrez.ruben.thecheezery_gutierrezruben.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonLogin.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString()

            binding.textLoginError.text = when {
                email.isBlank() || password.isBlank() -> "Completa tu correo y contraseña."
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Escribe un correo electrónico válido."
                else -> ""
            }

            if (binding.textLoginError.text.isEmpty()) {
                findNavController().navigate(R.id.action_login_to_menu)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
