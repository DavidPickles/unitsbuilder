package com.picklestech.sch.contgen

import org.scalatest.mock.MockitoSugar
import org.scalatest.{OneInstancePerTest, FunSuite}

/**
 * Created by davidpickles on 28/03/15.
 */
class WUTest extends FunSuite with OneInstancePerTest with MockitoSugar {

  val proto = new PrototypeWU()
  proto.body = Some("body")
  proto.lsnTitles = Some(Seq("atitle"))
  proto.numOfLsns = Some(1)
  proto.subject = Some("sub")
  proto.summary = Some("summary")
  proto.title = Some("title")
  proto.topics = Some(Seq("topic"))
  proto.yearGroup = Some("yg")

  test("doesn't barf if all values are in prototypeb") {
    WU(proto)
  }

  test("barfs if no body") {
    proto.body = None
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if no lsnTitles") {
    proto.lsnTitles = None
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if lsnTitles empty") {
    proto.lsnTitles = Some(Seq[String]())
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if no subject") {
    proto.subject = None
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if no summary") {
    proto.summary = None
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if no title") {
    proto.title = None
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if no topics") {
    proto.topics = None
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if no topics is empty") {
    proto.topics = Some(Seq[String]())
    intercept[IllegalArgumentException] { WU(proto) }
  }

  test("barfs if no yg") {
    proto.yearGroup = None
    intercept[IllegalArgumentException] { WU(proto) }
  }

  /*
    proto.body = Some("body")
  proto.lsnTitles = Some(Seq("atitle"))
  proto.numOfLsns = Some(1)
  proto.subject = Some("sub")
  proto.summary = Some("summary")
  proto.title = Some("title")
  proto.topics = Some(Seq("topic"))
  proto.yearGroup = Some("yg")
    title +sep+ subject +sep+ yearGroup +sep+ lsnTitles.mkString(listSep) +sep+
      topics.mkString(listSep) +sep+ summary +sep+ body

   */
  test("mkString works correctly") {
    assert(WU(proto).mkString("&","*")=="title&sub&yg&atitle&topic&summary&body")
    proto.lsnTitles = Some(proto.lsnTitles.get:+"another")
    proto.topics = Some(proto.topics.get:+"ananother")
    assert(WU(proto).mkString("&","*")=="title&sub&yg&atitle*another&topic*ananother&summary&body")
  }

}
