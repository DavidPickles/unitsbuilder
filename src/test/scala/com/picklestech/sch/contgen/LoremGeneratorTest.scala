package com.picklestech.sch.contgen

import org.scalatest.FunSuite

import scala.collection.mutable

/**
 * Created by davidpickles on 22/03/15.
 * Written before I started using Mockito
 */
class LoremGeneratorTest extends FunSuite {

  class RawLoremReader4Test extends RawLoremReader {
    override def getSentence(numWords: Int): String = ???
    override def getParas(num: Int, length: ParaLength, htmlMarkup: Boolean): String = ???
  }



  test("getParas delegates to reader") {
    val num_p = 6
    val htmlMarkup_p = true
    val length_p = ShortPara
    val v = "one two three"
    val mockReader:RawLoremReader = new RawLoremReader4Test {
      override def getParas(num: Int, length: ParaLength, htmlMarkup: Boolean): String = {
        assert(num==num_p)
        assert(htmlMarkup_p)
        assert(length==length_p)
        v
      }
    }
    val g = new LoremGenerator(mockReader)
    assert(g.getParas(num_p, htmlMarkup_p, length_p)==v)
  }

  test("getPhrase delegates to reader") {
    val numWords_p = 10
    val v = "one two three."
    val mockReader:RawLoremReader = new RawLoremReader4Test {
      override def getSentence(numWords: Int): String = {
        assert(numWords==numWords_p)
        v
      }
    }
    val g = new LoremGenerator(mockReader)
    assert(g.getPhrase(numWords_p, false, false)==v)
  }

  test("getPhrase chops period") {
    val stubReader:RawLoremReader = new RawLoremReader4Test {
      override def getSentence(numWords: Int): String = "one two three."
    }
    val g = new LoremGenerator(stubReader)
    assert(g.getPhrase(10, false, true)=="one two three")
  }

  test("getPhrase doesn't chop period") {
    val stubReader:RawLoremReader = new RawLoremReader4Test {
      override def getSentence(numWords: Int): String = "one two three."
    }
    val g = new LoremGenerator(stubReader)
    assert(g.getPhrase(10, false, false)=="one two three.")
  }

  test("getPhrase doesn't chop non-period punct") {
    val stubReader:RawLoremReader = new RawLoremReader4Test {
      override def getSentence(numWords: Int): String = "one two three?"
    }
    val g = new LoremGenerator(stubReader)
    assert(g.getPhrase(10, false, true)=="one two three?")
  }

  test("protectPhrase protects") {
    val g = new LoremGenerator(new RawLoremReader4Test())
    g.protectPhrase("one two three???")
    assert(g.isProtected("one two three???"))
    assert(g.isProtected("one two three"))
    assert(g.isProtected("one two three."))
    assert(g.isProtected("onetwothree"))
    assert(g.isProtected(" |??o  n e t w o t h r e e "))
    assert(!g.isProtected("one two three 4"))
  }

  test("getPhrase respects protection") {
    val sentences = mutable.Stack("one two three?","onetwothree","four five six","seven eight nine")
    val stubReader:RawLoremReader = new RawLoremReader4Test {
      override def getSentence(numWords: Int): String = sentences.pop()
    }
    val g = new LoremGenerator(stubReader)
    g.protectPhrase("one two three")
    g.protectPhrase("four five six")
    assert(g.getPhrase(1,false, false)=="seven eight nine")
  }

  test("getPhrase capitalises if asked") {
    val stubReader:RawLoremReader = new RawLoremReader4Test {
      override def getSentence(numWords: Int): String = "one two three?"
    }
    val g = new LoremGenerator(stubReader)
    assert(g.getPhrase(10, true, true)=="One Two Three?")

  }
}
