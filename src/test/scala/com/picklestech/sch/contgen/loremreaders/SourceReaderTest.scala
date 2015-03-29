package com.picklestech.sch.contgen.loremreaders

import com.picklestech.sch.contgen.{VeryLongPara, LongPara, MediumPara, ShortPara}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{OneInstancePerTest, FunSuite}

/**
 * Created by davidpickles on 27/03/15.
 */
class SourceReaderTest extends FunSuite with OneInstancePerTest with MockitoSugar {


  val sr = new LoripsumWsReader.SourceReader() {
    var toReturn = ""
    var urlWas = ""
    override def readFromSource(url:String):String = {urlWas=url;toReturn}
  }


  test("getParas chops off first para") {
    sr.toReturn = "foo foo\n\n blah blah blah"
    assert(sr.getParas(1,ShortPara,false)=="blah blah blah")
  }

  test("getParas translates to url correctly") {
    sr.getParas(1,ShortPara,false)
    assert(sr.urlWas==s"${sr.apiUrlPrefix}/2/short/plaintext")
    sr.getParas(3,MediumPara,false)
    assert(sr.urlWas==s"${sr.apiUrlPrefix}/4/medium/plaintext")
    sr.getParas(1,LongPara,true)
    assert(sr.urlWas==s"${sr.apiUrlPrefix}/2/long/")
    sr.getParas(1,VeryLongPara,true)
    assert(sr.urlWas==s"${sr.apiUrlPrefix}/2/verylong/")
  }
}
