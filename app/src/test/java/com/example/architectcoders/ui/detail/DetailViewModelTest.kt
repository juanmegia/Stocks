package com.example.architectcoders.ui.detail

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.architectcoders.Result
import com.example.architectcoders.domain.CompanyOfficerSummary
import com.example.architectcoders.domain.StockDetail
import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.runner.RunWith

//@ExtendWith(MockKExtension::class)
@RunWith(AndroidJUnit4::class)
class DetailViewModelTest {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    val fetchStockProfileUseCase: FetchStockProfileUseCase = mockk(relaxed = true)

    val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk(relaxed = true)

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

    @BeforeEach
    fun setUp() {
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
        // whenever(fetchStockProfileUseCase(symbol)).thenReturn(flowOf(fakeStockDetail))

        viewModel.onUiReady(symbol)

        viewModel.uiState.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(fakeStockDetail), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun favoriteActionTriggersToggleFavorite() = runTest {
        val symbol = "AAPL"
        coEvery {fetchStockProfileUseCase(symbol)} returns flowOf(fakeStockDetail)

        viewModel.onUiReady(symbol)
        viewModel.onAction(DetailAction.FavoriteClick)
        advanceUntilIdle()

        coVerify{toggleFavoriteUseCase.invoke(symbol)}
    }
}