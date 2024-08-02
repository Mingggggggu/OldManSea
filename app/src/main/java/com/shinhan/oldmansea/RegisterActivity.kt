package com.shinhan.oldmansea

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    lateinit var DB: DBHelper
    lateinit var writenewId: EditText
    lateinit var writenewPassword: EditText
    lateinit var ckwritenewPassword: EditText
    lateinit var writenewEmail: EditText
    lateinit var writeName: EditText
    lateinit var writenewPhoneNum: EditText
    lateinit var noIdDualbtn: Button
    lateinit var registerFinish: Button
    lateinit var noNumDualbtn: Button
    var CheckId: Boolean = false
    var CheckNum: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        DB = DBHelper(this)
        writenewId = findViewById(R.id.writenewId)
        writenewPassword = findViewById(R.id.writenewPassword)
        ckwritenewPassword = findViewById(R.id.ckwritenewPassword)
        writenewEmail = findViewById(R.id.writenewEmail)
        writeName = findViewById(R.id.writeName)
        writenewPhoneNum = findViewById(R.id.writenewPhoneNum)
        noNumDualbtn = findViewById(R.id.noNumDualbtn)
        noIdDualbtn = findViewById(R.id.noIdDualbtn)
        registerFinish = findViewById(R.id.registerFinish)


        noNumDualbtn.setOnClickListener{
            val PhoneNum = writenewPhoneNum.text.toString()
            val PhoneNumPattern = "^(\\+[0-9]+)?[0-9]{10,15}$"

            if(PhoneNum == "") {
                Toast.makeText(this@RegisterActivity, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (Pattern.matches(PhoneNumPattern, PhoneNum)) {
                    val checkPhone = DB.checkPhone(PhoneNum)
                    if (checkPhone == false) {
                        CheckNum = true
                        Toast.makeText(this@RegisterActivity, "사용 가능한 전화번호입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, "이미 존재하는 전화번호입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "전화번호 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        noIdDualbtn.setOnClickListener {
            val id = writenewId.text.toString()
            val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,15}$"

            if (id == "") {
                Toast.makeText(this@RegisterActivity, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (Pattern.matches(idPattern, id)) {
                    val checkUsername = DB.checkUser(id)
                    if (checkUsername == false) {
                        CheckId = true
                        Toast.makeText(this@RegisterActivity, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "아이디 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerFinish.setOnClickListener {
            val id = writenewId.text.toString()
            val password = writenewPassword.text.toString()
            val ckpass = ckwritenewPassword.text.toString()
            val email = writenewEmail.text.toString()
            val name = writeName.text.toString()
            val phone = writenewPhoneNum.text.toString()
            val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$])[A-Za-z0-9!@#$]{8,15}$"
            val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            val namePattern = "^[가-힣]{2,30}$"

            if (id == "" || password == "" || ckpass == "" || email == "" || name == "" || phone == "") {
                Toast.makeText(this@RegisterActivity, "회원정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (CheckId) {
                    if (Pattern.matches(pwPattern, password)) {
                        if (password == ckpass) {
                            if (Pattern.matches(emailPattern, email)) {
                                if(Pattern.matches(namePattern, name)) {
                                    if (CheckNum) {
                                        Log.d("DBHelper", "Inserting user data: id=$id, email=$email, name=$name, phone=$phone")
                                        val insert = DB.insertData(id, password, email, name, phone)
                                        if (insert) {
                                            Toast.makeText(
                                                this@RegisterActivity,"가입이 되었습니다.", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(applicationContext, LoginActivity::class.java)
                                            startActivity(intent)
                                        } else {
                                            Toast.makeText(
                                                this@RegisterActivity, "가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            this@RegisterActivity, "전화번호 중복확인을 해주세요.", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(this@RegisterActivity,"이름을 확인해주세요", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@RegisterActivity, "이메일 형식이 옳바르지 않습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@RegisterActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, "비밀번호 형식이 옳바르지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
