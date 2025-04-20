package com.example.architectcoders.ui.detail

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.architectcoders.Result
import com.example.architectcoders.domain.CompanyOfficerSummary
import com.example.architectcoders.domain.StockDetail
import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import com.example.architectcoders.utils.CoroutinesTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailViewModelTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Inject
    lateinit var fetchStockProfileUseCase: FetchStockProfileUseCase

    @Inject
    lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private lateinit var viewModel: DetailViewModel

    private val fakeStockDetail = StockDetail(
        companySymbol = "AAPL",
        industry = "Technology",
        sector = "Consumer Electronics",
        businessSummary = "Apple Inc. designs, manufactures, and markets smartphones.",
        address = "One Apple Park Way, Cupertino, CA 95014, United States",
        phone = "408-996-1010",
        website = "https://www.apple.com",
        fullTimeEmployees = 164000,
        companyOfficers = listOf(
            CompanyOfficerSummary(
                name = "Tim Cook",
                title = "CEO",
                totalPay = "$100M"
            )
        ),
        isFavorite = false
    )

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = DetailViewModel(fetchStockProfileUseCase, toggleFavoriteUseCase)
    }

    @Test
    fun initialStateIsLoading() = runTest {
        viewModel.uiState.test {
            assertEquals(Result.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onUiReadyFetchesStockProfileAndUpdatesState() = runTest {
        val symbol = "AAPL"
        whenever(fetchStockProfileUseCase(symbol)).thenReturn(flowOf(fakeStockDetail))

        viewModel.onUiReady(symbol)

        viewModel.uiState.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(fakeStockDetail), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun favoriteActionTriggersToggleFavorite() = runTest {
        val symbol = "AAPL"
        whenever(fetchStockProfileUseCase(symbol)).thenReturn(flowOf(fakeStockDetail))

        viewModel.onUiReady(symbol)
        viewModel.onAction(DetailAction.FavoriteClick)

        verify(toggleFavoriteUseCase).invoke(symbol)
    }
} 