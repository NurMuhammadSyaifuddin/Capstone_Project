package com.project.capstoneproject.ui.signup

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.project.capstoneproject.databinding.ActivitySignUpBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import com.project.capstoneproject.R
import com.project.capstoneproject.utils.*
import io.reactivex.Observable

@SuppressLint("CheckResult")
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val customDialog by lazy { CustomDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        procesedAccount()

        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnBack.setOnClickListener { finish() }
        }
    }

    private fun procesedAccount() {
        binding.apply {
            val emailStream = RxTextView.textChanges(edtEmail)
                .skipInitialValue()
                .map { email ->
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }
            emailStream.subscribe {
                showEmailExistAlert(it)
            }

            val passwordStream = RxTextView.textChanges(edtPassword)
                .skipInitialValue()
                .map { password ->
                    password.length < 8
                }
            passwordStream.subscribe {
                showPasswordMinimalAlert(it)
            }

            val confirmPasswordStream = Observable.merge(
                RxTextView.textChanges(edtConfirmPassword)
                    .skipInitialValue()
                    .map { confirmPassword ->
                        confirmPassword.length < 8
                    },
                RxTextView.textChanges(edtConfirmPassword)
                    .skipInitialValue()
                    .map { confirmPassword ->
                        confirmPassword.toString() != edtPassword.text.toString()
                    }
            )
            confirmPasswordStream.subscribe {
                showConfirmPasswordAlert(it)
            }

            val invalidFieldStream = Observable.combineLatest(
                emailStream,
                passwordStream,
                confirmPasswordStream,
                { emailInvalid, passwordInvalid, confirmPasswordInvalid ->
                    !emailInvalid && !passwordInvalid && !confirmPasswordInvalid
                }
            )
            invalidFieldStream.subscribe { isValid ->
                if (isValid)
                    btnCreateAccount.enable()
                else
                    btnCreateAccount.disable()

            }

            btnCreateAccount.setOnClickListener { signUp() }
        }
    }

    private fun signUp() {
        binding.apply {
            loading.root.visible()
            try {
                auth.createUserWithEmailAndPassword(
                    edtEmail.text.toString(),
                    edtPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        sendVerificationEmail()
                        customDialog.showSuccessCreateAccount(this@SignUpActivity, {
                            finish()
                        })
                        loading.root.gone()
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        this@SignUpActivity,
                        it.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loading.root.gone()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendVerificationEmail() {
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            auth.signOut()
        }
    }

    private fun showConfirmPasswordAlert(isNotValid: Boolean) {
        binding.edtConfirmPassword.error =
            if (isNotValid) getString(R.string.confirm_password_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.edtPassword.error =
            if (isNotValid) getString(R.string.password_length_less_than_8) else null
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edtEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

}