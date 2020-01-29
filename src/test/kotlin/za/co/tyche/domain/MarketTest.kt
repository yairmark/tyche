package za.co.tyche.domain

import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import za.co.tyche.stub.ExamplePDFData.activeMarket
import za.co.tyche.stub.ExamplePDFData.drawOutcome
import za.co.tyche.stub.ExamplePDFData.liverpoolWinOutcome
import za.co.tyche.stub.ExamplePDFData.manUtdWinOutcome
import za.co.tyche.stub.FixedDates

class MarketTest : FreeSpec({

    val validMarket = Market(
            description = "Foo",
            outcomes = setOf(Outcome("Some outcome 1", 0.6), Outcome("Some outcome 2", 0.5)),
            event = Event("Foo bar", FixedDates.now, FixedDates.nowPlus10Days)
    )

    "init_blankDescription_throwsError" - {
        listOf(
                "",
                "        ",
                "\t\t\n\t\n"
        )
                .map { invalidDescription ->

                    val errorMessage = shouldThrow<IllegalArgumentException> {
                        validMarket.copy(description = invalidDescription)
                    }.message

                    errorMessage.shouldBe("Description cannot be empty")

                }
    }

    "init_emptyOutcomes_throwsAnError" {
        val errorMessage = shouldThrow<IllegalArgumentException> {
            validMarket.copy(outcomes = setOf())
        }.message

        errorMessage.shouldBe("At least 2 outcomes need to be provided but 0 was/were instead")
    }


    "init_oneOutcome_throwsAnError" {
        val errorMessage = shouldThrow<IllegalArgumentException> {
            validMarket.copy(outcomes = setOf(Outcome("foo bar", 0.7)))
        }.message

        errorMessage.shouldBe("At least 2 outcomes need to be provided but 1 was/were instead")
    }

    "init_outComeProbabilitiesSumToLessThanOne_throwsAnError" {
        val errorMessage = shouldThrow<IllegalArgumentException> {
            validMarket.copy(outcomes = setOf(Outcome("foo bar", 0.7), Outcome("fizz buzz", 0.1)))
        }.message

        errorMessage.shouldBe("The probabilities in a market must be greater than 1")
    }


    "init_validParameters_initializesWithoutIssues" {
        val someMarket = Market(
                description = "Foo",
                outcomes = setOf(Outcome("Some outcome 1", 0.6), Outcome("Some outcome 2", 0.5)),
                event = Event("Foo bar", FixedDates.now, FixedDates.nowPlus10Days)
        )

        someMarket.shouldNotBeNull()
    }

    "calculateResultFor_pdfManUtdWin_outputsManUtdesults" {
        val expectedManUtdWinResult = Result("ManUtd Win", 10.00, 7.50)

        activeMarket.calculateResultFor(manUtdWinOutcome).shouldBe(expectedManUtdWinResult)
    }

    "calculateResultFor_pdfLiverpoolWin_outputsLiverpoolResults" {
        //TODO sort out rounding issues
        val expectedLiverpoolWinResult = Result("Liverpool Win", 14.444444444444445, 6.00)

        activeMarket.calculateResultFor(liverpoolWinOutcome).shouldBe(expectedLiverpoolWinResult)
    }

    "calculateResultFor_pdfDraw_outputsDrawResults" {
        val expectedDrawResult = Result("Draw", 10.0, 11.50)

        activeMarket.calculateResultFor(drawOutcome).shouldBe(expectedDrawResult)
    }

    "calculateResults" {
        val expectedManUtdWinResult = Result("ManUtd Win", 10.00, 7.50)
        //TODO sort out rounding issues
        val expectedLiverpoolWinResult = Result("Liverpool Win", 14.444444444444445, 6.00)
        val expectedDrawResult = Result("Draw", 10.0, 11.50)

        activeMarket.calculateResults().shouldBe(setOf(expectedManUtdWinResult, expectedLiverpoolWinResult, expectedDrawResult))
    }
})

