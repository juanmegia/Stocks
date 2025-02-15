package com.example.architectcoders.ui.detail

import DetailViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.architectcoders.R
import com.example.architectcoders.data.CompanyOfficerSummary
import com.example.architectcoders.data.StockDetail
import com.example.architectcoders.ui.common.LoadingProgressIndicator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    symbol: String,
    onBack: () -> Unit
) {
    val viewModel: DetailViewModel = viewModel()
    val state by rememberFlowWithLifecycle(viewModel.uiState).collectAsState(initial = DetailViewModel.UiState())
    val detailState = rememberDetailState()
    
    detailState.ShowMessageEffect(message = state.message) {
        viewModel.onAction(DetailAction.MessageShown)
    }
    LaunchedEffect(symbol) {
        viewModel.onUiReady(symbol)
    }





    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = symbol) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAction(DetailAction.FavoriteClick)}) {
                Icon(imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.back))
            }
        },
        snackbarHost = { SnackbarHost(hostState = detailState.snackbarHostState)}
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.loading -> {
                    LoadingProgressIndicator()
                }
                state.profile != null -> {
                    DetailContent(
                        profile = state.profile!!,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No data available",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun DetailContent(profile: StockDetail, modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = profile.companySymbol,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Text(
                text = profile.businessSummary,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expanded) Int.MAX_VALUE else 10, // Show 10 lines initially
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded } // Toggle on click
            )

        }
        item {
            InfoItem(label = "Industry", value = profile.industry)
        }
        item {
            InfoItem(label = "Sector", value = profile.sector)
        }
        item {
            InfoItem(label = "Employees", value = profile.fullTimeEmployees.toString())
        }
        item {
            InfoItem(label = "Address", value = profile.address)
        }
        item {
            InfoItem(label = "Phone", value = profile.phone)
        }
        item {
            InfoItem(label = "Website", value = profile.website)
        }
        items(profile.companyOfficers) { officer ->
            OfficerItem(officer = officer)
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 20.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun OfficerItem(officer: CompanyOfficerSummary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = officer.name, style = MaterialTheme.typography.bodyLarge)
        Text(text = officer.title, style = MaterialTheme.typography.bodyMedium)
        if (officer.totalPay != null) {
            Text(text = "Total Pay: ${officer.totalPay}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> {
    return remember(flow, lifecycle) {
        flow.flowWithLifecycle(lifecycle, minActiveState)
    }
}
