package com.picklestech.sch.contgen

import java.io.{StringWriter, PrintWriter}

import org.scalatest.mock.MockitoSugar
import org.scalatest.{OneInstancePerTest, FunSuite}
import org.mockito.Mockito._

import scala.io.Source

/**
 * Created by davidpickles on 27/03/15.
 */
class WUBuilderTest extends FunSuite with OneInstancePerTest with MockitoSugar {

  val proto = new PrototypeWU()
  proto.body = Some("body")
  proto.lsnTitles = Some(Seq("atitle"))
  proto.numOfLsns = Some(1)
  proto.subject = Some("sub")
  proto.summary = Some("summary")
  proto.title = Some("title")
  proto.topics = Some(Seq("topic"))
  proto.yearGroup = Some("yg")
  val wu = WU(proto)

  val parser = mock[WULineParser]
  val in = mock[Source]
  when(in.getLines).thenReturn(Array("one","two","three").toIterator)

  when(parser.parseLine("one")).thenReturn(None)
  when(parser.parseLine("two")).thenReturn(None)
  when(parser.parseLine("three")).thenReturn(Some(wu))
  val outsw = new StringWriter()
  val out = new PrintWriter(outsw)

  val builder = new WUBuilder(parser,in,out)

  test("parses lines and prints wu") {
    builder.run()
    assert(outsw.toString==s"${builder.titleLine}\ntitle\tsub\tyg\tatitle\ttopic\tsummary\tbody\n")
  }
}
