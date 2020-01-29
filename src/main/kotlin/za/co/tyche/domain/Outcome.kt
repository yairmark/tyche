package za.co.tyche.domain

data class Outcome(val description: String, val probability: Double) {
    init {
        require(description.isNotBlank()) { "Description cannot be empty" }
        require(probability in 0.0..1.0) { "Probability has to between 0 and 1 inclusive. Instead it was $probability" }
    }

    fun decimalOdds() = (1.0 / this.probability)

}

