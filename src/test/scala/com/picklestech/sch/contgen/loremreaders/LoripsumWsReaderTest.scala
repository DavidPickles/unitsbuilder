package com.picklestech.sch.contgen.loremreaders

import com.picklestech.sch.contgen.{LongPara, MediumPara, ShortPara}
import org.mockito.ArgumentCaptor
import org.scalatest.{OneInstancePerTest, BeforeAndAfter, FunSuite}
import org.scalatest.mock.{MockitoSugar}
import org.mockito.Matchers.{eq=>meq}
import org.mockito.Matchers._
import org.mockito.Mockito._

/**
 * Created by davidpickles on 20/03/15.
 */
class LoripsumWsReaderTest extends FunSuite with OneInstancePerTest with MockitoSugar {

  val sr = mock[LoripsumWsReader.SourceReader]
  val r = new LoripsumWsReader(sr)


  test("getSentence produces right number of words") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("one two three.")
    assert(r.getSentence(3)=="One two three.")
  }

  test("getSentence produces as many words as it can") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("one two three four.")
    assert(r.getSentence(1000)=="One two three four.")
  }

  test("getSentence recognises the last word even if no stop") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("one two three")
    assert(r.getSentence(3)=="One two three.")
  }

  test("getSentence deals ok with embedded full stops") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("O.n.e. two. three four")
    assert(r.getSentence(3)=="One.")
  }


  test("getSentence deals ok with dots in funny places") {
    when(sr.getParas(1,ShortPara,false)).thenReturn(". One two. three . four . ")
    assert(r.getSentence(3)=="One two three.")
  }

  test("getSentence captializes first char") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("one two three four")
    assert(r.getSentence(3)=="One two three.")
  }

  test("getSentence throws away bad sentence ending characters") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("One two three: four")
    assert(r.getSentence(3)=="One two three.")
    when(sr.getParas(1,ShortPara,false)).thenReturn("One two three- four")
    assert(r.getSentence(3)=="One two three.")
    when(sr.getParas(1,ShortPara,false)).thenReturn("One two three\" four")
    assert(r.getSentence(3)=="One two three.")
    when(sr.getParas(1,ShortPara,false)).thenReturn("One two three; four")
    assert(r.getSentence(3)=="One two three.")
    when(sr.getParas(1,ShortPara,false)).thenReturn("One two three, four")
    assert(r.getSentence(3)=="One two three.")
  }

  test("getSetnence replaces semicolons with commas") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("One; two; three; four")
    assert(r.getSentence(3)=="One, two, three.")
    when(sr.getParas(1,ShortPara,false)).thenReturn("One; two; three! four")
    assert(r.getSentence(3)=="One, two, three!")

  }

  test("getSentence keeps good sentence ending characters") {
    when(sr.getParas(1,ShortPara,false)).thenReturn("One two three? four")
    assert(r.getSentence(3)=="One two three?")
    when(sr.getParas(1,ShortPara,false)).thenReturn("One two three! four")
    assert(r.getSentence(3)=="One two three!")
  }

  test("getParas delegates correctly") {
    r.getParas(5,LongPara,true)
    verify(sr).getParas(5,LongPara,true)
  }
}
