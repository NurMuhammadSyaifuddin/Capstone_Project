package com.project.capstoneproject.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jakewharton.rxbinding2.widget.RxTextView
import com.project.capstoneproject.MainActivity
import com.project.capstoneproject.R
import com.project.capstoneproject.databinding.ActivityLoginBinding
import com.project.capstoneproject.ui.signup.SignUpActivity
import com.project.capstoneproject.utils.*
import io.reactivex.Observable

@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setGso()

        processedLogin()

        onAction()
    }

    private fun setGso() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private fun processedLogin() {
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

            val invalidFieldStream = Observable.combineLatest(
                emailStream,
                passwordStream
            ) { emailInvalid, passwordInvalid ->
                !emailInvalid && !passwordInvalid
            }
            invalidFieldStream.subscribe { isValid ->
                if (isValid) btnLogin.enable() else btnLogin.disable()
            }
        }
    }

    private fun onAction() {
        binding.apply {
            tvRegister.setOnClickListener {
                Intent(this@LoginActivity, SignUpActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }

            tvGoogleLogin.setOnClickListener { signIn() }

            btnLogin.setOnClickListener { signInWithEmail() }
        }
    }

    private fun signInWithEmail() {
        binding.apply {
            loading.root.visible()
            try {
                auth.signInWithEmailAndPassword(
                    edtEmail.text.toString(),
                    edtPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (auth.currentUser?.isEmailVerified as Boolean) {
                            Intent(this@LoginActivity, MainActivity::class.java).also { intent ->
                                startActivity(intent)
                                finishAffinity()
                            }
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.email_not_verify),
                                Toast.LENGTH_SHORT
                            ).show()
                            auth.signOut()
                        }
                        loading.root.gone()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            it.exception?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        loading.root.gone()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.edtPassword.error =
            if (isNotValid) getString(R.string.password_length_less_than_8) else null
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edtEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>?) {
        try {
            val account = completedTask?.getResult(ApiException::class.java)
            account?.let { firebaseAuthWithGoogle(it) }
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Intent(this, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, R.string.google_sign_in_failed, Toast.LENGTH_SHORT).show()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null || GoogleSignIn.getLastSignedInAccount(this) != null) {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1
    }
}