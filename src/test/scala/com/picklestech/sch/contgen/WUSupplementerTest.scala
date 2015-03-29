package com.picklestech.sch.contgen

import org.mockito.ArgumentCaptor
import org.scalatest.{OneInstancePerTest, BeforeAndAfter, FunSuite}
import org.scalatest.mock.{MockitoSugar}
import org.mockito.Matchers.{eq=>meq}
import org.mockito.Matchers._
import org.mockito.Mockito._

/**
 * Created by davidpickles on 22/03/15.
 */
class WUSupplementerTest extends FunSuite with OneInstancePerTest with MockitoSugar {

  val lg = mock[LoremGenerator]
  when(lg.getParas(anyInt(),anyBoolean(),any[ParaLength])).thenReturn("one two three")
  when(lg.getPhrase(anyInt(),anyBoolean(),anyBoolean())).thenReturn("four five six")

  val lsnTitles = Array("one","two","three","four","five")

  val wus = new WUSupplementer(lsnTitles,lg)

  val intCaptor = ArgumentCaptor.forClass(classOf[Int])
  

  test("inventBody delegates correctly to LoremGenerator") {
    val body = wus.inventBody()
    assert(body=="one two three")
    verify(lg).getParas(intCaptor.capture(),meq(wus.htmlMarkupInBody),meq(wus.paraSizeInBody))
    val numParas = intCaptor.getValue
    assert(numParas>=wus.minParasInBody && numParas<=wus.maxParasInBody)
  }

  test("inventSummary delegates correctly to LoremGenerator") {
    val summary = wus.inventSummary()
    assert(summary=="one two three")
    verify(lg).getParas(intCaptor.capture(),meq(wus.htmlMarkupInSummary),meq(wus.paraSizeInSummary))
    val numParas = intCaptor.getValue
    assert(numParas>=wus.minParasInSummary && numParas<=wus.maxParasInSummary)
  }

  test("inventTitle delegates correctly to LoremGenerator") {
    val title = wus.inventTitle()
    assert(title=="four five six")
    verify(lg).getPhrase(intCaptor.capture(),meq(wus.capitaliseTitle),meq(wus.chopPeriodFromTitle))
    val numWords = intCaptor.getValue
    assert(numWords>=wus.minWordsInTitle && numWords<=wus.maxWordsInTitle)
  }


  test("registerTitle delegates correctly to LoremGenerator") {
    wus.registerTitle("foo")
    verify(lg).protectPhrase("foo")
  }

  test("getLessonPlanTitles pops titles from supplied list") {
    assert(wus.getLessonPlanTitles(2)==Seq("one","two"))
    assert(wus.getLessonPlanTitles(3)==Seq("three","four","five"))
  }


  test("getLessonPlanTitles throws if out of titles") {
    intercept[NoSuchElementException] { wus.getLessonPlanTitles(6) }
  }
}
