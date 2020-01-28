package za.co.tyche.domain

data class Market internal constructor(val description: String, val outcomes: Set<Outcome>, val event: Event) {
    init {
        require(description.isNotBlank()) { "Description cannot be empty" }
    }

    operator fun plus(outcome: Outcome): Market {
        return Market(this.description, this.outcomes + outcome, this.event)
    }

    fun activate(): ActiveMarket {
        require(this.outcomes.sumByDouble { it.probability } > 1.0) { "The probabilities in a market must be greater than 1" }
        return ActiveMarket(this)
    }

    companion object {
        fun newMarket(description: String, event: Event, outcomes: Set<Outcome>): Market {
            require(outcomes.size >= 2) { "At least 2 outcomes need to be provided but ${outcomes.size} were instead" }
            return Market(description, outcomes, event)
        }
    }
}
