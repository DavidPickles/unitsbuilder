package com.picklestech.sch.contgen

import org.apache.commons.lang3.text.WordUtils

import scala.collection.mutable
/**
 * Created by davidpickles on 20/03/15.
 */
class LoremGenerator(val reader: RawLoremReader) {

  private val simplifyPattern = """\W|_*""".r
  private val protectedPhrases = mutable.Set[String]()

  def getPhrase(numWords: Int, capitalise:Boolean, chopPeriod:Boolean):String = {
    var phrase = reader.getSentence(numWords)
    if (!isProtected(phrase)) {
      if (chopPeriod) phrase = phrase.stripSuffix(".")
      if (capitalise) phrase = WordUtils.capitalize(phrase)
      phrase
    } else getPhrase(numWords, capitalise, chopPeriod) // tail recursive
  }

  def getParas(num:Int, htmlMarkup:Boolean, length:ParaLength): String = {
    reader.getParas(num, length, htmlMarkup)
  }


  /**
   * Protected phrases cannot be generated again
   * @param phrase
   */
  def protectPhrase(phrase:String) {
    protectedPhrases.add(simplify(phrase))
  }

  /**
   * Protected phrases cannot generated
   * @param phrase
   * @return
   */
  def isProtected(phrase: String): Boolean = {
    protectedPhrases.contains(simplify(phrase))
  }

  private def simplify(phrase: String):String = {
    simplifyPattern.replaceAllIn(phrase,"").toLowerCase
  }
}
