package com.example.addassignment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.addassignment.databinding.ActivityMainBinding
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var qiitaApi: QiitaApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // 初期化処理
        setUpQiitaApi()



        lifecycleScope.launch {
            runCatching {
                qiitaApi.getQiitaArticles("tag:kotlin")
            }.onSuccess{
                binding.textOne.text = it[0].title
                binding.textTwo.text = it[1].title
                binding.textThree.text = it[2].title
                binding.textFour.text = it[3].title
                binding.textFive.text = it[4].title

            }.onFailure {
                Log.d("error", it.message.toString())
            }
        }
    }

    fun setUpQiitaApi(){
        // moshiのセットアップ
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        //HTTP通信をするための設定をしている。
        val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging).build()

        // Retrofitの設定をしている
        val retrofit = Retrofit.Builder()
            .baseUrl("https://qiita.com/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClientBuilder)
            .build()


        qiitaApi = retrofit.create(QiitaApiService::class.java)
    }
}