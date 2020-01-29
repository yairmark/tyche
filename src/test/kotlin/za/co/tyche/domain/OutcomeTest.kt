package za.co.tyche.domain

import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import io.kotlintest.tables.row

class OutcomeTest : FreeSpec({

    "init_emptyDescription_throwsAnError" {
        listOf(
                "",
                "        ",
                "\t\t\n\t\n"
        )
                .map { invalidDescription ->

                    val errorMessage = shouldThrow<IllegalArgumentException> {
                        Outcome(description = invalidDescription, probability = 0.3)
                    }.message

                    errorMessage.shouldBe("Description cannot be empty")

                }
    }

    "init_probabilityNegative_throwsAnError" {

        val errorMessage = shouldThrow<IllegalArgumentException> {
            Outcome(description = "Foo", probability = -0.3)
        }.message

        errorMessage.shouldBe("Probability has to between 0 and 1 inclusive. Instead it was -0.3")
    }


    "init_probabilityGreaterThanOne_throwsAnError" {
        val errorMessage = shouldThrow<IllegalArgumentException> {
            Outcome(description = "Foo", probability = 1.1)
        }.message

        errorMessage.shouldBe("Probability has to between 0 and 1 inclusive. Instead it was 1.1")
    }


    "init_probabilityZero_buildsWithoutIssue" {
        val outcome = Outcome(description = "Foo", probability = 0.0)

        outcome.shouldNotBeNull()
    }


    "init_probabilityOne_buildsWithoutIssue" {
        val outcome = Outcome(description = "Foo", probability = 1.0)

        outcome.shouldNotBeNull()
    }


    "init_probabilityBetweenOneAndZero_buildsWithoutIssue" {
        val outcome = Outcome(description = "Foo", probability = 0.5)

        outcome.shouldNotBeNull()
    }

    "decimalOdds_anyNumber_isTheInverseOfTheProbability" - {

        listOf(
                row(0.5, 2.0),
                row(0.1, 10.0),
                row(0.4, 2.5)
        ).map { (probability, expectedDecimalOdds) ->
            val decimalOdds = Outcome(description = "foo", probability = probability).decimalOdds()

            decimalOdds.shouldBe(expectedDecimalOdds)
        }

    }

})

