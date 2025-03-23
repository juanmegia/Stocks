package com.example.architectcoders.data.datasource

import com.example.architectcoders.data.datasource.database.CompanyOfficerSummaryDao
import com.example.architectcoders.data.datasource.database.StockDao
import com.example.architectcoders.data.datasource.database.StockDetailDao
import com.example.architectcoders.domain.Stock
import com.example.architectcoders.domain.StockDetail
import com.example.architectcoders.data.datasource.database.SymbolsDao
import com.example.architectcoders.domain.CompanyOfficerSummary
import com.example.architectcoders.domain.LocalDataSource
import kotlinx.coroutines.flow.map




class SymbolsLocalDataSource(private val symbolsDao: SymbolsDao) : LocalDataSource {

    override val stocks = symbolsDao.fetchPopularStocks()
    override fun getStockProfile(symbol: String) =
        symbolsDao.fetchStockProfile(symbol).map { it?.toDomain() }

    override suspend fun insertStocks(stocks: List<Stock>) =
        symbolsDao.saveStocks(stocks.map { it.toDao() })

    override suspend fun insertStockDetail(stock: StockDetail) =
        symbolsDao.saveStockDetail(stock.toDao())

    override suspend fun toggleFavorite(symbol: String) {
        symbolsDao.toggleFavorite(symbol)
        symbolsDao.toggleFavoriteProfile(symbol)
    }
}

fun StockDetailDao.toDomain(): StockDetail {
    return StockDetail(
        companySymbol = this.companySymbol,
        industry = this.industry,
        sector = this.sector,
        businessSummary = this.businessSummary,
        address = this.address,
        phone = this.phone,
        website = this.website,
        fullTimeEmployees = this.fullTimeEmployees,
        companyOfficers = this.companyOfficersDao.map { it.toDomain() },
        isFavorite = this.isFavorite
    )
}
fun Stock.toDao(): StockDao {
    return StockDao(
        symbol = this.symbol,
        name = this.name,
        lastSale = this.lastSale,
        netChange = this.netChange,
        pctChange = this.pctChange,
        marketCap = this.marketCap,
        isFavorite = this.isFavorite
    )
}

fun CompanyOfficerSummaryDao.toDomain(): CompanyOfficerSummary {
    return CompanyOfficerSummary(
        name = this.name,
        title = this.title,
        totalPay = this.totalPay
    )
}

fun StockDetail.toDao(): StockDetailDao {
    return StockDetailDao(
        companySymbol = this.companySymbol,
        industry = this.industry,
        sector = this.sector,
        businessSummary = this.businessSummary,
        address = this.address,
        phone = this.phone,
        website = this.website,
        fullTimeEmployees = this.fullTimeEmployees,
        companyOfficersDao = this.companyOfficers.map { it.toDao() },
        isFavorite = this.isFavorite
    )
}

fun CompanyOfficerSummary.toDao(): CompanyOfficerSummaryDao {
    return CompanyOfficerSummaryDao(
        name = this.name,
        title = this.title,
        totalPay = this.totalPay
    )
}
