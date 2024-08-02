package com.shinhan.oldmansea

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "Login.db", null, 1) {

    companion object {
        const val DBNAME = "Login.db"
    }

    override fun onCreate(DB: SQLiteDatabase?) {
        DB?.execSQL("create table users (id TEXT primary key, password TEXT, email TEXT, name TEXT, phone TEXT)")
    }

    override fun onUpgrade(DB: SQLiteDatabase?, i: Int, i1: Int) {
        DB?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(DB)
    }

    //id,password, email, phone 삽입( 성공시 true, 실패시 false)
    fun insertData(
        id: String?,
        password: String?,
        email: String?,
        name: String?,
        phone: String?
    ): Boolean {
        val DB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("password", password)
        contentValues.put("email", email)
        contentValues.put("name", name)
        contentValues.put("phone", phone)
        val result = DB.insert("users", null, contentValues)
        DB.close()
        return result != -1L
    }

    //사용자 번호가 없으면 false, 이미 존재하면 true
    fun checkPhone(phone: String?): Boolean {
        val DB = this.readableDatabase
        var res = true
        val cursor = DB.rawQuery("SELECT * FROM users WHERE phone = ?", arrayOf(phone))
        if (cursor.count <= 0) res = false
        cursor.close()
        DB.close()
        return res

    }

    //사용자 아이디가 없으면 false, 이미 존재하면 true
    fun checkUser(id: String?): Boolean {
        val DB = this.readableDatabase
        var res = true
        val cursor = DB.rawQuery("SELECT * FROM users WHERE id = ?", arrayOf(id))
        if (cursor.count <= 0) res = false
        cursor.close()
        DB.close()
        return res
    }

    // 해당 id, password가 있는지 확인 (없다면 false)
    fun checkUserpass(id: String, password: String): Boolean {
        val DB = this.writableDatabase
        var res = true
        val cursor =
            DB.rawQuery("Select * from users where id = ? AND password = ?", arrayOf(id, password))
        if (cursor.count <= 0) res = false
        cursor.close()
        DB.close()
        return res
    }
}
