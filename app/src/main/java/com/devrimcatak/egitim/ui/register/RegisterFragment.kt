package com.devrimcatak.egitim.ui.register


import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.base.BaseFragment
import com.devrimcatak.egitim.databinding.FragmentRegisterBinding
import com.devrimcatak.egitim.model.enums.PasswordVisibilityType
import com.devrimcatak.egitim.ui.login.LoginFragmentDirections
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import com.devrimcatak.egitim.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(FragmentRegisterBinding::inflate) {

    var passwordType = PasswordVisibilityType.Hide
    var passwordRepeatType = PasswordVisibilityType.Hide

    override val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateFinished() {

        passwordVisibleClick()
        loginClick()
        registerClick()
    }

    override fun initializeListeners() {
    }

    override fun observeEvents() {
        with(viewModel){
            registerResponse.observe(viewLifecycleOwner, Observer {
                it?.let { response ->
                    handleViews(false)
                    SharedPreferencesHelper().setToken(requireContext(),response.data.token)
                    SharedPreferencesHelper().setLoginStatus(requireContext(), true)
                    val navigation = RegisterFragmentDirections.actionRegisterFragmentToCoursesFragment()
                    Navigation.findNavController(requireView()).navigate(navigation)
                }
            })
            isLoading.observe(viewLifecycleOwner, Observer {
                handleViews(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                handleViews(false)
            })
        }
    }

    private fun handleViews(isLoading: Boolean = false){
        if (isLoading){
            binding.clLoading.visibility = View.VISIBLE
            binding.animationView.playAnimation()
        } else {
            binding.clLoading.visibility = View.GONE
            binding.animationView.pauseAnimation()
        }
    }

    private fun registerClick(){
        binding.btnRegister.setOnClickListener {
            if (formControl()){
                handleViews(true)
                viewModel.register(binding.editName.text.toString(), binding.editEmail.text.toString(), binding.editPassword.text.toString())
            }
        }
    }

    private fun formControl() : Boolean{
        if (binding.editEmail.text.isNotEmpty()){
            if (binding.editName.text.isNotEmpty()){
                if (binding.editPassword.text.isNotEmpty()){
                    if (binding.editPasswordRepeat.text.isNotEmpty()){
                        return true
                    } else {
                        Tools().showToast(requireContext(),getString(R.string.auth_password_null))
                        return false
                    }
                } else {
                    Tools().showToast(requireContext(),getString(R.string.auth_password_null))
                    return false
                }
            } else {
                Tools().showToast(requireContext(),getString(R.string.auth_name_null))
                return false
            }
        } else {
            Tools().showToast(requireContext(),getString(R.string.auth_email_null))
            return false
        }
    }

    private fun loginClick(){
        binding.textLogin.setOnClickListener {
            val navigation = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(requireView()).navigate(navigation)
        }
    }

    private fun passwordVisibleClick(){
        binding.imgPassword.setOnClickListener {
            passwordVisible(it)
        }

        binding.imgPasswordRepeat.setOnClickListener {
            passwordVisible(it)
        }
    }

    private fun passwordVisible(view : View){
        when(view){
            binding.imgPassword ->{
                if(passwordType == PasswordVisibilityType.Hide){
                    binding.editPassword.transformationMethod = null
                    passwordType = PasswordVisibilityType.Show
                    binding.imgPassword.setImageResource(R.drawable.password_show)
                }else{
                    binding.editPassword.transformationMethod = PasswordTransformationMethod()
                    passwordType = PasswordVisibilityType.Hide
                    binding.imgPassword.setImageResource(R.drawable.password_hide)
                }
            }
            binding.imgPasswordRepeat ->{
                if(passwordRepeatType == PasswordVisibilityType.Hide){
                    binding.editPasswordRepeat.transformationMethod = null
                    passwordRepeatType = PasswordVisibilityType.Show
                    binding.imgPasswordRepeat.setImageResource(R.drawable.password_show)
                }else{
                    binding.editPasswordRepeat.transformationMethod = PasswordTransformationMethod()
                    passwordRepeatType = PasswordVisibilityType.Hide
                    binding.imgPasswordRepeat.setImageResource(R.drawable.password_hide)
                }
            }
        }
    }
}