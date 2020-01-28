package za.co.tyche.domain

data class ActiveMarket internal constructor(private val market: Market, private val bets: Map<Outcome, Double> = mapOf()) {

    operator fun plus(bet: Bet): ActiveMarket {
        return placeBet(bet)
    }

    fun placeBet(bet: Bet): ActiveMarket {
        val (outcome, amount) = bet

        require(this.market.outcomes.contains(outcome)) { "The ${this.market.description} market does not have the outcome [${outcome}]" }

        val updateBetMap = bets.toMutableMap()
        updateBetMap[outcome] = updateBetMap.getOrDefault(outcome, 0.0) + amount

        return ActiveMarket(this.market, updateBetMap.toMap())
    }

    fun calculateResults(): Set<Result> {
        val totalPot = bets.values.sum()

        return bets.map { (outcome, bet) ->
            resultForGivenOutcome(bet, outcome, totalPot)
        }.toSet()
    }

    fun calculateResultFor(outcome: Outcome): Result {
        require(this.market.outcomes.contains(outcome)) { "The ${this.market.description} market does not have the outcome [${outcome}]" }

        val totalPot = bets.values.sum()
        val bet = bets.getValue(outcome)

        return resultForGivenOutcome(bet, outcome, totalPot)
    }

    private fun resultForGivenOutcome(bet: Double, outcome: Outcome, totalPot: Double): Result {
        val payout = (bet * outcome.decimalOdds())
        val takings = totalPot - bet

        return Result(outcome.description, payout, takings)
    }

}