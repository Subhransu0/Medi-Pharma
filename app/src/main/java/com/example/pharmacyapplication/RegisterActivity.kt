package com.example.pharmacyapplication
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmacyapplication.databinding.ActivityRegisterBinding
import com.example.pharmacyapplication.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class RegisterActivity : AppCompatActivity() {
    private lateinit var email :String
    private lateinit var password : String
    private lateinit var username : String
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference


    private val binding : ActivityRegisterBinding by lazy{
ActivityRegisterBinding.inflate((layoutInflater))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference


        binding.createUserButton.setOnClickListener{
            username = binding.userName.text.toString()
            email = binding.emailAddress.text.toString().trim()
            password = binding.passwwordSignup.text.toString().trim()

            if(username.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please fillup the details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }
        }

        binding.alreadyHaveButton.setOnClickListener{
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createAccount(email: String, password: String) {
auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
    task ->
    if(task.isSuccessful){
        Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
        saveUserData()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }else{
        Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
        Log.d("Account", "createAccount: Failure",task.exception)
    }
}
    }

    private fun saveUserData() {
        username = binding.userName.text.toString()
        email = binding.emailAddress.text.toString().trim()
        password = binding.passwwordSignup.text.toString().trim()

        val user = UserModel(username,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)

    }
}