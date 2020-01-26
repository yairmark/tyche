package za.co.tyche.dto

import za.co.tyche.dto.market.Outcome

data class Bet(val market: Market, val outcome: Outcome, val amount: Double)