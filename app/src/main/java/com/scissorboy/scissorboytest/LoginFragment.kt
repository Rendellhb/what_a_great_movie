package com.scissorboy.scissorboytest

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.scissorboy.scissorboytest.databinding.FragmentLoginBinding
import com.scissorboy.scissorboytest.util.validate
import com.scissorboy.scissorboytest.viewmodel.LoginViewModel
import com.scissorboy.scissorboytest.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.main_activity.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private var userAction = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_title)

        val factory = LoginViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        subscribeUi(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val regex = "[a-zA-Z0-9_]{0,20}".toRegex()
        edit_username.validate({s -> regex.matches(s)}, getString(R.string.empty_not_allowed))

        requireActivity().navigation.visibility = View.GONE

        edit_username.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performClick(edit_username.text.toString())
            }
            false
        }

        btn_go.setOnClickListener {
            performClick(edit_username.text.toString())
        }
    }

    private fun performClick(username: String) {
        if (edit_username.error == null) {
            userAction = 1
            viewModel.setUsername(username)
            viewModel.getUser()
        }
    }
    private fun subscribeUi(v: View) {
        viewModel.getUser().observe(viewLifecycleOwner, Observer { users ->
            if (userAction != -1) {
                if (users.isNullOrEmpty()) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage(R.string.new_user)
                    builder.setPositiveButton(android.R.string.yes) { _, _ ->
                        viewModel.createUser()
                    }
                    builder.setNegativeButton(android.R.string.no) { _, _ -> }
                    builder.create().show()
                } else {
                    navigateToHome(v)
                }
            }
        })
    }

    private fun navigateToHome(view : View?) {
        val directions = LoginFragmentDirections.actionLoginFragmentToMoviesListFragment().setUsernameToShow(edit_username.text.toString())
        view?.findNavController()?.navigate(directions)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.logout)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}