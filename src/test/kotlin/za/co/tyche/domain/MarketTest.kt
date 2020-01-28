package za.co.tyche.domain

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import za.co.tyche.stub.ExamplePDFData.activeMarket
import za.co.tyche.stub.ExamplePDFData.drawOutcome
import za.co.tyche.stub.ExamplePDFData.liverpoolWinOutcome
import za.co.tyche.stub.ExamplePDFData.manUtdWinOutcome

class MarketTest : FreeSpec({

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

