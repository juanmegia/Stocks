package com.example.architectcoders.data.database.remote

import com.example.architectcoders.domain.datasource.RemoteDataSource
import com.example.architectcoders.domain.CompanyOfficerSummary
import com.example.architectcoders.domain.Stock
import com.example.architectcoders.domain.StockDetail

class SymbolsRemoteDataSource(private val client: SymbolsClient) :
    RemoteDataSource {
    override suspend fun fetchPopularStocks(): List<Stock> =
        client.service.fetchPopularStocks().body.map { it.toDomainModel() }

    override suspend fun fetchStockProfile(symbol:String): StockDetail =
        client.service.fetchStockDetails(symbol).toDomainModel()
}

private fun RemoteStock.toDomainModel(): Stock =
    Stock(
        symbol = this.symbol,
        name = this.name,
        lastSale = this.lastsale,
        netChange = this.netchange,
        pctChange = this.pctchange,
        marketCap = this.marketCap
    )

private fun RemoteResultStockDetail.toDomainModel(): StockDetail {
    return StockDetail(
        companySymbol = this.meta.symbol,
        industry = this.body.industry,
        sector = this.body.sector,
        businessSummary = this.body.longBusinessSummary,
        address = "${this.body.address1}, ${this.body.city}, ${this.body.state}, ${this.body.country}",
        phone = this.body.phone,
        website = this.body.website,
        fullTimeEmployees = this.body.fullTimeEmployees,
        companyOfficers = this.body.companyOfficers.map { officer ->
            CompanyOfficerSummary(
                name = officer.name,
                title = officer.title,
                totalPay = officer.totalPay?.fmt
            )
        }
    )
}
