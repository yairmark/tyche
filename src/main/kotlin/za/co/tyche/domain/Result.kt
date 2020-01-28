package za.co.tyche.domain

data class Result(val description: String, val payout: Double, val takings: Double) {
    override fun toString(): String {
        return "${description}: Payout = R${payout}, Takings = R${takings}"
    }
}