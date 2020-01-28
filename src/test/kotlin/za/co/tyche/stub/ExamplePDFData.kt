package za.co.tyche.stub

import za.co.tyche.dto.*
import java.time.LocalDateTime
import java.time.Month

object ExamplePDFData {

    val now = LocalDateTime.of(2020, Month.JANUARY, 28, 0, 0, 0, 0)
    val nowPlus10Days = now.plusDays(10)

    val event = Event("ManUtd vs Liverpool", now, nowPlus10Days)

    val manUtdWinOutcome = Outcome("ManUtd Win", 0.5)
    val drawOutcome = Outcome("Draw", 0.1)
    val liverpoolWinOutcome = Outcome("Liverpool Win", 0.45)

    val outcomes = setOf(
            manUtdWinOutcome,
            drawOutcome,
            liverpoolWinOutcome

    )

    val activeMarket: ActiveMarket = Market.newMarket("ManUtd vs Liverpool Jan 2020", event, outcomes)
            .activate()
            .placeBet(Bet(manUtdWinOutcome, 5.0))
            .placeBet(Bet(liverpoolWinOutcome, 2.0))
            .placeBet(Bet(liverpoolWinOutcome, 4.5))
            .placeBet(Bet(drawOutcome, 1.0))
}

