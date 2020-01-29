package za.co.tyche

import za.co.tyche.domain.Bet
import za.co.tyche.domain.Event
import za.co.tyche.domain.Market
import za.co.tyche.domain.Outcome
import java.time.LocalDateTime
import java.time.Month

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val startDate = LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0, 0, 0)
            val endDate = startDate.plusHours(2)
            val event = Event("Manchester Utd vs Liverpool", startDate, endDate)

            val manUtdWinOutcome = Outcome("ManUtd Win", 0.5)
            val drawOutcome = Outcome("Draw", 0.1)
            val liverpoolWinOutcome = Outcome("Liverpool Win", 0.5)

            val market = Market(description = "Manchester Utd vs Liverpool End Result",
                    outcomes = setOf(manUtdWinOutcome, drawOutcome, liverpoolWinOutcome),
                    event = event
            )

            val marketResults = market
                    .placeBet(Bet(manUtdWinOutcome, 5.0))
                    .placeBet(Bet(liverpoolWinOutcome, 2.0))
                    .placeBet(Bet(liverpoolWinOutcome, 4.5))
                    .placeBet(Bet(drawOutcome, 1.0))
                    .calculateResults()

            val resultsAsAString = marketResults.joinToString("\n") { "* $it" }

            println("-------------------------------------------------")
            println("Results on different outcomes for the given bets")
            println("-------------------------------------------------")
            println("${market.description}\n")
            println(resultsAsAString)
            println("-------------------------------------------------")

        }
    }
}