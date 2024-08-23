package com.example.shopinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopinglistapp.ui.theme.ShopingListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // `ShopingListAppTheme` teması uygulanarak uygulamanın genel stil ayarları belirlenir. Bu tema altında,
            // bir `Surface` bileşeni eklenir ve bu bileşen, ekranın tamamını kaplayacak şekilde ayarlanır.
            // `Surface` bileşeninin arka plan rengi, tema renk düzenine uygun olarak ayarlanır
            ShopingListAppTheme {
                    Surface(
                       modifier = Modifier.fillMaxSize(),
                        color= MaterialTheme.colorScheme.background
                    )

                    {
                        ShopListingApp()

                }
            }
        }
    }
}


