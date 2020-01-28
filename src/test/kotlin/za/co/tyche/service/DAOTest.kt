package za.co.tyche.service

import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldNotBeEmpty
import io.kotlintest.matchers.maps.shouldContain
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import za.co.tyche.db.DAO
import za.co.tyche.domain.Bet
import za.co.tyche.domain.Event
import za.co.tyche.domain.Outcome
import za.co.tyche.stub.FixedDates


class DAOTest : FreeSpec({

    "createEvent_validDetails_returnsUUIDOfAddedEvent" {
        val eventId: String = DAO().createEvent("foo", FixedDates.now, FixedDates.nowPlus10Days)

        eventId.length.shouldBe(36)
    }

    "getEventById_eventIdDoesNotExist_returnsNull"{
        DAO().getEventById("foo").shouldBeNull()
    }


    "getEventById_eventIdDoesExist_returnsCorrectEvent"{
        val dao = DAO()
        val eventId: String = dao.createEvent("foo", FixedDates.now, FixedDates.nowPlus10Days)
        val event = dao.getEventById(eventId)

        event.shouldNotBeNull()
        event.shouldBe(Event("foo", FixedDates.now, FixedDates.nowPlus10Days))
    }

    "createMarket_validDetails_returnsMarketId" {
        val marketId = createFooBarMarket(DAO())

        marketId.length.shouldBe(36)
    }


    "createMarket_associatedEventIdDoesNotExist_throwsError" {
        val outcomes = setOf(Outcome("Outcome 1", 0.6), Outcome("Outcome 2", 0.7))
        val errorMessage = shouldThrow<IllegalArgumentException> {
            DAO().createMarket("foo bar market", outcomes, "fizzbuzz")
        }.message

        errorMessage.shouldBe("There is no event with id fizzbuzz")
    }

    "getMarketById_existingId_returnsAssociatedMarket" {
        val dao = DAO()
        val marketId = createFooBarMarket(dao)

        val marketById = dao.getMarketById(marketId)

        marketById.shouldNotBeNull()
        marketById.description.shouldBe("foo bar market")
    }

    "getMarketById_noneExistingId_returnsNull" {
        val marketById = DAO().getMarketById("rick and morty")
        marketById.shouldBeNull()
    }

    "updateProbabilityForOutcome_validInputs_updatesProbability"{
        val dao = DAO()
        val outcomes = setOf(Outcome("Outcome 1", 0.6), Outcome("Outcome 2", 0.7))
        val eventId = dao.createEvent("foo event", FixedDates.now, FixedDates.nowPlus10Days)
        val marketId = dao.createMarket("foo bar market", outcomes, eventId)

        val updatedProbabilityForOutcome = dao.updateProbabilityForOutcome(marketId, "Outcome 1", 1.0)
        updatedProbabilityForOutcome.probability.shouldBe(1.0)

        val updatedOutcomes = dao.getMarketById(marketId)!!.outcomes

        updatedOutcomes.size.shouldBe(2)
        updatedOutcomes.first { it.description == "Outcome 1" }.probability.shouldBe(1.0)
    }

    "getAllMarketsForEventId_eventWithNoMarkets_returnsEmptyList" {
        val dao = DAO()
        val eventId = dao.createEvent("foo event", FixedDates.now, FixedDates.nowPlus10Days)

        dao.getAllMarketsForEventId(eventId).shouldBeEmpty()
    }


    "getAllMarketsForEventId_eventWithMarkets_returnsNoneEmptyList" {
        val dao = DAO()
        val outcomes = setOf(Outcome("Outcome 1", 0.6), Outcome("Outcome 2", 0.7))
        val eventId = dao.createEvent("foo event", FixedDates.now, FixedDates.nowPlus10Days)
        dao.createMarket("foo bar market", outcomes, eventId)
        dao.createMarket("foo bar market 2", outcomes, eventId)

        val allMarketsForEventId = dao.getAllMarketsForEventId(eventId)
        allMarketsForEventId.shouldNotBeEmpty()
        allMarketsForEventId.map { it.description }.toSet().shouldBe(setOf("foo bar market", "foo bar market 2"))
    }

    "placeBet_validInputs_returnsBetId" {
        val dao = DAO()
        val outcomes = setOf(Outcome("Outcome 1", 0.6), Outcome("Outcome 2", 0.7))
        val eventId = dao.createEvent("foo event", FixedDates.now, FixedDates.nowPlus10Days)
        val marketId = dao.createMarket("foo bar market", outcomes, eventId)

        val betId = dao.placeBet(marketId, "Outcome 1", 10.20)

        betId.length.shouldBe(36)
    }


    "placeBet_validInputs_updatesTheAssociatedMarketsBet" {
        val dao = DAO()
        val outcomes = setOf(Outcome("Outcome 1", 0.6), Outcome("Outcome 2", 0.7))
        val eventId = dao.createEvent("foo event", FixedDates.now, FixedDates.nowPlus10Days)
        val marketId = dao.createMarket("foo bar market", outcomes, eventId)

        dao.placeBet(marketId, "Outcome 1", 10.20)

        val (_, _, _, updatedBets) = dao.getMarketById(marketId)!!

        updatedBets.shouldContain(Outcome("Outcome 1", 0.6), 10.20)
    }

    "getBetById_idExists_returnsBet"{
        val dao = DAO()
        val outcomes = setOf(Outcome("Outcome 1", 0.6), Outcome("Outcome 2", 0.7))
        val eventId = dao.createEvent("foo event", FixedDates.now, FixedDates.nowPlus10Days)
        val marketId = dao.createMarket("foo bar market", outcomes, eventId)
        val betId = dao.placeBet(marketId, "Outcome 1", 10.20)

        val betById = dao.getBetById(betId)

        betById.shouldNotBeNull()
        betById.shouldBe(Bet(Outcome("Outcome 1", 0.6), 10.20))

    }


})

private fun createFooBarMarket(dao: DAO): String {
    val outcomes = setOf(Outcome("Outcome 1", 0.6), Outcome("Outcome 2", 0.7))
    val eventId = dao.createEvent("foo event", FixedDates.now, FixedDates.nowPlus10Days)
    return dao.createMarket("foo bar market", outcomes, eventId)
}
