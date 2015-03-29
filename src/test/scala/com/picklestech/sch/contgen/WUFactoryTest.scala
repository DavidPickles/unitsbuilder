package com.picklestech.sch.contgen

import org.mockito.Mockito._
import org.scalatest.{OneInstancePerTest, FunSuite}
import org.scalatest.mock.MockitoSugar
import org.mockito.Matchers.{eq=>meq}

/**
 * Created by davidpickles on 26/03/15.
 */
class WUFactoryTest extends FunSuite with OneInstancePerTest with MockitoSugar {

  val sup = mock[WUSupplementer]
  when(sup.getLessonPlanTitles(2)).thenReturn(List("one","two"))
  when(sup.inventBody()).thenReturn("a body")
  when(sup.inventSummary()).thenReturn("a summary")
  when(sup.inventTitle()).thenReturn("an invented title")

  val protoWU = new PrototypeWU()
  protoWU.numOfLsns = Option(2)
  protoWU.subject = Option("a subject")
  protoWU.topics = Option(List("t1","t2"))
  protoWU.yearGroup = Option("a yg")

  val wuf = new WUFactory(sup)

  test("gets empty fields from supplementer") {
    val wu = wuf.makeWorkUnit(protoWU)
    assert(wu.lsnTitles==List("one","two"))
    assert(wu.body=="a body")
    assert(wu.summary=="a summary")
    assert(wu.title=="an invented title")
  }

  test("gets registers invented titles") {
    val wu = wuf.makeWorkUnit(protoWU)
    verify(sup).registerTitle(meq("an invented title"))
  }

  test("gets registers uninvented titles") {
    protoWU.title = Option("uninvented title")
    val wu = wuf.makeWorkUnit(protoWU)
    verify(sup).registerTitle(meq("uninvented title"))
  }

  test("throws if missing field") {
    protoWU.subject = None
    intercept[IllegalArgumentException] { wuf.makeWorkUnit(protoWU) }
  }

}
