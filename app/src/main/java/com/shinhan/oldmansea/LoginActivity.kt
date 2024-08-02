package com.shinhan.oldmansea

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    lateinit var loginbtn: Button
    lateinit var writeId: EditText
    lateinit var writePassword: EditText
    lateinit var registerbtn: TextView
    lateinit var findIdbtn: TextView
    lateinit var findPasswordbtn: TextView
    var DB: DBHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DB = DBHelper(this)
        loginbtn = findViewById(R.id.loginbtn)
        writeId = findViewById(R.id.writeId)
        writePassword = findViewById(R.id.writePassword)
        registerbtn = findViewById(R.id.registerbtn)

        //로그인 버튼 클릭
        loginbtn!!.setOnClickListener{
            val id = writeId!!.text.toString()
            val pass = writePassword!!.text.toString()

            //빈칸 제출 시 Toast
            if (id == "" || pass == "") {
                Toast.makeText(this@LoginActivity, "아이디와 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                val checkUserpass = DB!!.checkUserpass(id, pass)
                //id 와 password 일치시
                if (checkUserpass == true) {
                    Toast.makeText(this@LoginActivity, "로그인 되었습니다. 환영합니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this@LoginActivity, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //회원가입 버튼 클릭시
        registerbtn.setOnClickListener {
            val loginIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(loginIntent)
        }
        //아이디 찾기 버튼 클릭시
        findIdbtn.setOnClickListener {
            val loginIntent = Intent(this@LoginActivity, FindIdActivity::class.java)
            startActivity(loginIntent)
        }
        //비밀번호 찾기 버튼 클릭시
        findPasswordbtn.setOnClickListener{
            val loginIntent = Intent(this@LoginActivity, FindPasswordActivity::class.java)
            startActivity(loginIntent)
        }
    }
}