package com.example.architectcoders.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.architectcoders.data.Stock

@Composable
fun HomeScreen( modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    val viewModel: HomeViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        println("HomeScreen: LaunchedEffect triggered")
        viewModel.onUiReady()
    }

    Box(modifier = modifier.fillMaxSize().statusBarsPadding()) {
        if (state.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            StockList(stocks = state.stocks, modifier = Modifier.fillMaxSize(), onClick = { symbol ->
                onClick(symbol)
                viewModel.onClear()
            })
        }
    }
}

@Composable
fun StockList(stocks: List<Stock>, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(stocks) { stock ->
            StockItem(stock = stock, modifier = Modifier.fillMaxWidth(), onClick = onClick)
        }
    }
}

@Composable
fun StockItem(stock: Stock, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    val backgroundColor = when {
        stock.netChange.startsWith("-") -> Color.Red
        else -> Color.Green
    }
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(stock.symbol) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stock.symbol, style = MaterialTheme.typography.titleLarge)
            Text(text = stock.name, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Last Sale: ${stock.lastSale}", style = MaterialTheme.typography.bodySmall)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(backgroundColor, shape = RoundedCornerShape(4.dp)) // Fondo dinámico con borde redondeado
                        .padding(horizontal = 8.dp, vertical = 4.dp) // Padding interno para que el texto no toque los bordes
                )
                {
                    Text(
                        text = "Change: ${stock.netChange} (${stock.pctChange})",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Market Cap: ${stock.marketCap}", style = MaterialTheme.typography.labelSmall)
        }
    }
}
