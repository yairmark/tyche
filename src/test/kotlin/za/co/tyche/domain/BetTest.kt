package za.co.tyche.domain

import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class BetTest : StringSpec({

    "init_amountIsZero_throwsAnError"  {
        val errorMessage = shouldThrow<IllegalArgumentException> {
            Bet(Outcome("Foo", 0.5), amount = 0.0)
        }.message

        errorMessage.shouldBe("A bet cannot be zero or negative")
    }

    "init_amountIsNegative_throwsAnError"  {
        val errorMessage = shouldThrow<IllegalArgumentException> {
            Bet(Outcome("Foo", 0.5), amount = -1.0)
        }.message

        errorMessage.shouldBe("A bet cannot be zero or negative")
    }

    "init_amountIsPositive_buildsWithoutIssues"  {
        val bet = Bet(Outcome("Foo", 0.5), amount = 1.0)

        bet.shouldNotBeNull()
    }
})


