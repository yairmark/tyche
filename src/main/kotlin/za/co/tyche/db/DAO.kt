package za.co.tyche.db

import za.co.tyche.domain.Bet
import za.co.tyche.domain.Event
import za.co.tyche.domain.Market
import za.co.tyche.domain.Outcome
import java.time.LocalDateTime
import java.util.*

class DAO {

    private val events = mutableMapOf<String, Event>()
    private val markets = mutableMapOf<String, Market>()
    private val bets = mutableMapOf<String, Bet>()

    private val eventMarkets = mutableMapOf<String, MutableSet<String>>()
    private val marketBets = mutableMapOf<String, MutableSet<Int>>()


    private fun randomID() = UUID.randomUUID().toString()

    fun createEvent(description: String, startDate: LocalDateTime, endDate: LocalDateTime): String {
        val event = Event(description, startDate, endDate)
        val eventID = randomID()

        events[eventID] = event

        return eventID
    }

    fun getEventById(eventId: String): Event? {
        return events[eventId]
    }

    fun createMarket(description: String, outcomes: Set<Outcome>, eventId: String): String {
        require(events.containsKey(eventId)) { "There is no event with id $eventId" }

        val event = events.getValue(eventId)
        val market = Market(description, outcomes, event)
        val marketId = randomID()

        markets[marketId] = market
        val updatedEventToMarkets = eventMarkets.getOrDefault(eventId, mutableSetOf())
        updatedEventToMarkets.add(marketId)
        this.eventMarkets[eventId] = updatedEventToMarkets

        return marketId
    }

    fun getMarketById(marketId: String): Market? {
        return markets[marketId]
    }

    fun updateProbabilityForOutcome(marketId: String, outcomeDescription: String, newProbability: Double): Outcome {
        require(markets.containsKey(marketId)) { "There is no market with id $marketId" }
        val market = markets.getValue(marketId)
        val currentOutcome: Outcome? = market.outcomes.firstOrNull { it.description == outcomeDescription }
        require(currentOutcome != null) { "There is no outcome with description [${outcomeDescription}] in market with id $marketId" }

        val newOutcome = currentOutcome.copy(probability = newProbability)

        var updatedOutcomes = markets.getValue(marketId).outcomes
        updatedOutcomes = updatedOutcomes - currentOutcome
        updatedOutcomes = updatedOutcomes + newOutcome

        markets[marketId] = markets.getValue(marketId).copy(outcomes = updatedOutcomes)

        return newOutcome
    }

    fun getAllMarketsForEventId(eventId: String): List<Market> {
        return eventMarkets[eventId]
                ?.map { marketId ->
                    markets.getValue(marketId)
                } ?: listOf()
    }

    fun placeBet(marketId: String, outcomeDescription: String, amount: Double): String {
        require(markets.containsKey(marketId)) { "There is no market with id $marketId" }
        val market = markets.getValue(marketId)

        val currentOutcome: Outcome = market.outcomes.firstOrNull { it.description == outcomeDescription }
                ?: throw IllegalArgumentException("There is no outcome with description [${outcomeDescription}] in market with id $marketId")

        val bet = Bet(currentOutcome, amount)
        val betId = randomID()

        val updatedMarket = market.placeBet(bet)

        bets[betId] = bet
        val currentMarketBets = marketBets.getOrDefault(marketId, mutableSetOf())
        currentMarketBets + bet

        return betId
    }


}