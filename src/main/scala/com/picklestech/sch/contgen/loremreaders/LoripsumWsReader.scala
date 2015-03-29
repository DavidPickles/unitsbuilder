package com.picklestech.sch.contgen.loremreaders

import com.picklestech.sch.contgen._
import com.picklestech.sch.contgen.loremreaders.LoripsumWsReader.SourceReader

import scala.io.Source

/**
 * Created by davidpickles on 20/03/15.
 */
class LoripsumWsReader(val sourceReader:LoripsumWsReader.SourceReader=new SourceReader) extends RawLoremReader{

  override def getSentence(numWords: Int): String = {
    val firstNWordsPattern = s"^\\W*((?:\\w+\\W*){1,$numWords})".r.unanchored
    val firstNWordsPattern(s) = sourceReader.getParas(1,ShortPara,false)
    val badPunc = """(\.|([-&:,;'"]\s*)$)"""
    var s1 = s.replaceAll(badPunc,"").replaceAll("\\s+"," ").replaceAll(";",",").trim.capitalize
    if (s1.matches("^.*\\w$")) s1 + "." else s1
  }

  override def getParas(num: Int, length: ParaLength, htmlMarkup: Boolean): String = {
    sourceReader.getParas(num,length,htmlMarkup)
  }

}

object LoripsumWsReader {


  class SourceReader() {

    var apiUrlPrefix = "http://loripsum.net/api"
    var enc = "UTF-8"

    def getParas(num:Int, size: ParaLength,  htmlMarkup:Boolean):String = {
      val format = if (htmlMarkup) "" else "plaintext"
      val url = s"$apiUrlPrefix/${num+1}/${getParaLengthStr(size)}/$format"
      val s = readFromSource(url)
      // first sentence is always the same, so we throw away the whole first paragraph
      // chop of first lorem - always the same :(,
      // remove line breaks
      s.replaceFirst("^.*?\\n\\n","").
        trim.
        replaceAll("\\n+<","<").
        replaceAll("\\s+"," ")
    }

    protected def readFromSource(url:String):String = {
      Console.err.println(s"GET $url")
      Source.fromURL(url,enc).mkString
    }

    private def getParaLengthStr(length: ParaLength):String = {
      length match {
        case ShortPara => "short"
        case MediumPara => "medium"
        case LongPara => "long"
        case VeryLongPara => "verylong"
      }
    }

  }


}
