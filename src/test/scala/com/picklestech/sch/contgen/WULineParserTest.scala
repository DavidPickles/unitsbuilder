package com.picklestech.sch.contgen

import org.mockito.ArgumentCaptor
import org.scalatest.mock.MockitoSugar
import org.scalatest.{OneInstancePerTest, FunSuite}
import org.mockito.Matchers.{eq=>meq}
import org.mockito.Matchers._
import org.mockito.Mockito._

/**
 * Created by davidpickles on 26/03/15.
 */
class WULineParserTest extends FunSuite with OneInstancePerTest with MockitoSugar {

  val sup = mock[WUSupplementer]

  when(sup.inventBody).thenReturn("body")
  when(sup.inventSummary).thenReturn("summary")
  when(sup.inventTitle).thenReturn("invented title")
  when(sup.getLessonPlanTitles(4)).thenReturn(Seq("one","two","three","four"))

  val fac = new WUFactory(sup)

  val parser = new WULineParser(fac)

  val lines = List(
    "title= a title",
    "sub = a subject  ",
    "yg   =a yg",
    "tt=something| something else",
     "numLsns = 4"
  )


  test("parses field values correctly") {
    lines.foreach ( (l) => assert(parser.parseLine(l)==None) )
    val wu = parser.parseLine("!").get
    assert(wu.title=="a title")
    assert(wu.subject=="a subject")
    assert(wu.yearGroup=="a yg")
    assert(wu.topics.sameElements(Array("something","something else")))
    assert(wu.body=="body")
    assert(wu.summary=="summary")
    assert(wu.lsnTitles==Seq("one","two","three","four"))
  }

  test("barfs if bad syntax") {
    intercept[MatchError] { parser.parseLine("tit=not a title") }
  }

  test("ignores empty line") {
    parser.parseLine(" ")
    parser.parseLine(" \t")
  }

  test("clears up non-persistent state after parse") {
    lines.foreach ( parser.parseLine(_) )
    val wu = parser.parseLine("!").get
    assert(parser.state.body==None)
    assert(parser.state.summary==None)
    assert(parser.state.title==None)
    assert(parser.state.lsnTitles==None)
  }
}
