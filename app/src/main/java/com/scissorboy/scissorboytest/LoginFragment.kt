package com.scissorboy.scissorboytest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.scissorboy.scissorboytest.databinding.FragmentLoginBinding
import com.scissorboy.scissorboytest.util.StaticObjects
import com.scissorboy.scissorboytest.util.validate
import com.scissorboy.scissorboytest.viewmodel.LoginViewModel
import com.scissorboy.scissorboytest.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.main_activity.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_title)

        val factory = LoginViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

        return FragmentLoginBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val regex = "[a-zA-Z0-9_]{0,20}".toRegex()
        edit_username.validate({s -> regex.matches(s)}, getString(R.string.empty_not_allowed))
;
        requireActivity().navigation.visibility = View.GONE

        edit_username.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                navigateToHome(v)
            }
            false
        }

        btn_go.setOnClickListener {
            navigateToHome(it)
        }
    }

    fun navigateToHome(view : View?) {
        if (edit_username.error == null) {
            StaticObjects.username = edit_username.text.toString()
            val directions = LoginFragmentDirections.actionLoginFragmentToMoviesListFragment().setUsernameToShow(edit_username.text.toString())
            view?.findNavController()?.navigate(directions)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.logout)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}