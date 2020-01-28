package za.co.tyche.dto

data class Bet(val outcome: Outcome, val amount: Double) {
    init {
        require(amount > 0) { "A bet cannot be zero or negative" }
    }
}