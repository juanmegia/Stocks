package com.example.architectcoders.data


class SymbolsRepository {

    suspend fun fetchPopularStocks(): List<Stock> =

        SymbolsClient.instance.fetchPopularStocks().body.map { it.toDomainModel() }
    suspend fun fetchStockProfile(symbol:String): StockDetail =
        SymbolsClient.instance.fetchStockDetails(symbol).toDomainModel()

}

private fun  RemoteStock.toDomainModel(): Stock =
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
