package com.example.kazuki.sainttropez

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerForContextMenu(imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.top -> {
                imageView.setImageResource(R.drawable.toppage)
                return true
            }
            R.id.lunch01 -> {
                imageView.setImageResource(R.drawable.lunch01)
                return true
            }
            R.id.lunch02 -> {
                imageView.setImageResource(R.drawable.lunch02)
                return true
            }
            R.id.dinner01 -> {
                imageView.setImageResource(R.drawable.dinner01)
                return true
            }
            R.id.dinner02 -> {
                imageView.setImageResource(R.drawable.dinner02)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo:ContextMenu.ContextMenuInfo?){
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.sms -> {
                val number = "999-9999-9999"
                val uri = Uri.parse("sms:$number")
                var intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                startActivity(intent)
                return true
            }
            R.id.mail -> {
                val email: String = "nobody@example.com"
                val subject: String = "予約問い合わせ"
                val text:String = "以下の通り予約希望します．"
                val uri = Uri.parse("mailto:")
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.apply{
                    data = uri
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }
            R.id.share -> {
                val text:String = "美味しいレストランを紹介します．"
                val intent = Intent(Intent.ACTION_SEND)
                intent.apply{
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                val chooser = Intent.createChooser(intent, null)
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(chooser)
                }
                return true
            }
            R.id.browse -> {
                val url:String = "http://www.google.com"
                val intent =Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
