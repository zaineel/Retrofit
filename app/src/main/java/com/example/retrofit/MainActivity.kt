 package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)
        val responseLiveData: LiveData <Response<Albums>> = liveData{
            val response = retService.getAlbums()
            emit(response)
        }
        responseLiveData.observe(this, Observer{
            val itemsList = it.body()?.listIterator()
            if (itemsList != null){
                while (itemsList.hasNext()){
                    val albumsItem = itemsList.next()
                    val result = " " + "Album Title : ${albumsItem.title}\n" +
                            " " + "Album id : ${albumsItem.id}\n" +
                            " " + "User id : ${albumsItem.userId}\n\n\n"
                    val textview = findViewById<TextView>(R.id.text_view)
                    textview.append(result)


                }
            }
        })
    }
}