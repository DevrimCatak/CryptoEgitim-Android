package com.devrimcatak.egitim.ui.login

import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.base.BaseFragment
import com.devrimcatak.egitim.databinding.FragmentLoginBinding
import com.devrimcatak.egitim.model.enums.PasswordVisibilityType
import com.devrimcatak.egitim.utils.SharedPreferencesHelper
import com.devrimcatak.egitim.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment <FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate)  {

    var passwordType = PasswordVisibilityType.Hide

    override val viewModel by viewModels<LoginViewModel>()

    override fun onCreateFinished() {

        passwordVisibleClick()
        loginClick()
        registerClick()
    }

    override fun initializeListeners() {
    }

    override fun observeEvents() {
        with(viewModel){
            loginResponse.observe(viewLifecycleOwner, Observer {
                it?.let { response ->
                    handleViews(false)
                    SharedPreferencesHelper().setToken(requireContext(),response.data.token)
                    SharedPreferencesHelper().setLoginStatus(requireContext(), true)
                    val navigation = LoginFragmentDirections.actionLoginFragmentToCoursesFragment()
                    Navigation.findNavController(requireView()).navigate(navigation)
                }
            })
            isLoading.observe(viewLifecycleOwner, Observer {
                handleViews(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                it?.let { it1 ->
                    Tools().showToast(requireContext(), it1)
                }
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

    private fun loginClick(){
        binding.btnLogin.setOnClickListener {
            handleViews(true)
            viewModel.login(binding.editEmail.text.toString(), binding.editPassword.text.toString())
        }
    }

    private fun registerClick(){
        binding.textRegister.setOnClickListener {
            if (formControl()) {
                val navigation = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                Navigation.findNavController(requireView()).navigate(navigation)
            }
        }
    }

    private fun formControl() : Boolean{
        return if (binding.editEmail.text.isNotEmpty()){
            if (binding.editPassword.text.isNotEmpty()){
                true
            } else {
                Tools().showToast(requireContext(),getString(R.string.auth_password_null))
                false
            }
        } else {
            Tools().showToast(requireContext(),getString(R.string.auth_email_null))
            false
        }
    }

    private fun passwordVisibleClick(){
        binding.imgPassword.setOnClickListener {
            passwordVisible()
        }
    }

    private fun passwordVisible(){
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

    override fun onResume() {
        super.onResume()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}