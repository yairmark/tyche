package za.co.tyche.dto

import za.co.tyche.dto.market.Outcome

data class Market(val description: String, val outcomes: Set<Outcome>)