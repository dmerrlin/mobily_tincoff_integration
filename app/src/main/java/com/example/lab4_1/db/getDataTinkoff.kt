
import com.example.lab4_1.db.Profile
import com.example.lab4_1.db.Share
import com.example.lab4_1.db.Shares
import ru.tinkoff.piapi.core.InvestApi


class getDataTinkoff() {

    fun getConnectApi(token: String): InvestApi{

        var api = InvestApi.create(token)

        return api
    }

    fun getPortfolioExample(api: InvestApi): Profile {

        val accounts = api.userService.accountsSync
        val mainAccount = accounts[0].id

        //Получаем портфолио
        val portfolio = api.operationsService.getPortfolioSync(mainAccount)
        val totalAmountBonds = portfolio.totalAmountBonds.value

        val totalAmountEtf = portfolio.totalAmountEtfs.value

        val totalAmountCurrencies = portfolio.totalAmountCurrencies.value

        val totalAmountFutures = portfolio.totalAmountFutures.value

        val totalAmountShares = portfolio.totalAmountShares.value

        val positions = portfolio.positions
        val dataList = ArrayList<Share>()

        for (i in 0 until Math.min(positions.size, 5)) {
            val position = positions[i]
            val figi = position.figi
            val quantity = position.quantity
            val currentPrice = position.currentPrice.value
            val expectedYield = position.expectedYield

            dataList.add(Share(figi, quantity.toInt(), currentPrice.toDouble(), expectedYield.toDouble()))
        }

        return Profile(totalAmountBonds.toInt(), totalAmountEtf.toDouble(), totalAmountCurrencies.toDouble(),
            totalAmountFutures.toDouble(), totalAmountShares.toDouble(), portfolio.expectedYield.toDouble(),
            positions.size, dataList)
    }

    fun getSharesData(api: InvestApi): ArrayList<Shares>{
        val dataList = ArrayList<Shares>()

        val shares = api.instrumentsService.allShares.get()

        for (i in 0 until shares.size) {
            if(shares[i].currency == "rub"){

                val share = Shares(shares[i].figi, shares[i].name, shares[i].isin)
                dataList.add(share)

            }
        }

        return dataList
    }

    fun getShareByfigi(api: InvestApi, figi: String): Shares{

        val share = api.instrumentsService.getShareByFigi(figi).get()

        return Shares(share.figi, share.name, share.isin)
    }

}