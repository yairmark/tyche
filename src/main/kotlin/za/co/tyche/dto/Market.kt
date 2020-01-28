package za.co.tyche.dto

data class Market internal constructor(val description: String, val outcomes: Set<Outcome>) {
    init {
        require(description.isNotBlank()) { "Description cannot be empty" }
    }

    operator fun plus(outcome: Outcome): Market {
        return Market(this.description, this.outcomes + outcome)
    }

    fun activate(): ActiveMarket {
        require(this.outcomes.sumByDouble { it.probability } > 1.0) { "The probabilities in a market must be greater than 1" }
        return ActiveMarket(this)
    }
}