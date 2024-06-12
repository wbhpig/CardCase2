package com.team4.cardcase2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.team4.cardcase2.entity.Info
import com.team4.cardcase2.entity.Info.Companion.toJson
import com.team4.cardcase2.entity.LoginInfo
import com.team4.cardcase2.entity.LoginInfo.Companion.toJson
import com.team4.cardcase2.entity.Login_back
import com.team4.cardcase2.foreground.MainActivity

class Login:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // 确保使用正确的布局文件

        val emailEditText = findViewById<EditText>(R.id.email_string)
        val passwordEditText = findViewById<EditText>(R.id.login_password_string)
        val loginButton = findViewById<Button>(R.id.logInButton)
        val forgotButton = findViewById<Button>(R.id.forgetpassword_button)
        val signupLinkButton = findViewById<Button>(R.id.signUpLink_button)
        val backButton = findViewById<ImageButton>(R.id.backbutton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }else{
                val info = LoginInfo(email,password)

                val jsonInfo = info.toJson()
//                Toast.makeText(this, jsonInfo, Toast.LENGTH_SHORT).show()
                val url = "https://578q07p228.vicp.fun/api/login"
                val httpRequest = HttpRequest()
                httpRequest.sendInfoRequest(url,jsonInfo){response,exception->
                    runOnUiThread {
                        if (exception != null) {
                            // 处理错误
                            println("Request failed: ${exception.message}")
                            Toast.makeText(this, "Request failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                        } else {
                            // 处理成功响应
                            val response_string = response.toString()
                            val loginBackObject = Login_back.fromJson(response_string)
                            when {
                                // 根据具体情况处理响应消息，例如根据返回的特定字段或信息来判断要跳转到哪个界面
                                // 假设根据返回的消息中的某个字段来判断跳转到哪个界面
                                loginBackObject != null && loginBackObject.success == false -> {
                                    // 创建失败，已存在用户
                                    Toast.makeText(
                                        this,
                                        loginBackObject.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                                loginBackObject != null && loginBackObject.success == true -> {
                                    Toast.makeText(
                                        this,
                                        loginBackObject.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // 跳转到主界面
                                    // 进一步处理响应，比如跳转到新界面
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                }

                                else -> {
                                    // 默认情况下，做一些通用处理或显示错误消息
                                    Toast.makeText(this, "Unexpected response", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
            }
        }

        forgotButton.setOnClickListener{
            val intent = Intent(this, forgot_password::class.java)
            startActivity(intent)
        }

        signupLinkButton.setOnClickListener{
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }
        backButton.setOnClickListener{
            finish()
        }

    }
}