package za.co.tyche.domain

data class Market internal constructor(val description: String, val outcomes: Set<Outcome>, val event: Event, val bets: Map<Outcome, Double> = mapOf()) {

    init {
        require(description.isNotBlank()) { "Description cannot be empty" }
        require(outcomes.size >= 2) { "At least 2 outcomes need to be provided but ${outcomes.size} was/were instead" }
        require(outcomes.sumByDouble { it.probability } > 1.0) { "The probabilities in a market must be greater than 1" }
    }

    operator fun plus(outcome: Outcome): Market {
        return Market(this.description, this.outcomes + outcome, this.event)
    }

    fun placeBet(bet: Bet): Market {
        val (outcome, amount) = bet

        require(this.outcomes.contains(outcome)) { "The ${this.description} market does not have the outcome [${outcome}]" }

        val updateBetMap = bets.toMutableMap()
        updateBetMap[outcome] = updateBetMap.getOrDefault(outcome, 0.0) + amount

        return this.copy(bets = updateBetMap.toMap())
    }

    fun calculateResults(): Set<Result> {
        val totalPot = bets.values.sum()

        return bets.map { (outcome, bet) ->
            resultForGivenOutcome(bet, outcome, totalPot)
        }.toSet()
    }

    fun calculateResultFor(outcome: Outcome): Result {
        require(this.outcomes.contains(outcome)) { "The ${this.description} market does not have the outcome [${outcome}]" }

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
