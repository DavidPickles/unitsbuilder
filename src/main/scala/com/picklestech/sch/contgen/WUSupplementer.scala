package com.picklestech.sch.contgen

import scala.util.Random

/**
 * Created by davidpickles on 20/03/15.
 */
class WUSupplementer(lsnTitles:Iterable[String], val loremGenerator:LoremGenerator ) {

  private val lsnTitlesIterator =  lsnTitles.toIterator
  private val randomer = Random

  var maxParasInBody = 5
  var minParasInBody = 3
  var paraSizeInBody = MediumPara
  var htmlMarkupInBody = true

  var maxWordsInTitle = 7
  var minWordsInTitle = 1
  var chopPeriodFromTitle = true
  var capitaliseTitle = true

  var maxParasInSummary = 1
  var minParasInSummary = 1
  var paraSizeInSummary = ShortPara
  var htmlMarkupInSummary = false


  def inventBody():String = {
    val numPs = getIntBetween(maxParasInBody,minParasInBody)
    loremGenerator.getParas(numPs,htmlMarkupInBody,paraSizeInBody)
  }

  def inventSummary():String = {
    val numPs = getIntBetween(maxParasInSummary,minParasInSummary)
    loremGenerator.getParas(numPs,htmlMarkupInSummary,paraSizeInSummary)
  }


  /**
   * Invents a title. Guaranteed to be different from any registered title. Note that this method does not register the
   * title that it invents, so you have to call registerTitle separately if that's what you want.
   * @return A new title
   */
  def inventTitle():String = {
    val numWs = getIntBetween(maxWordsInTitle,minWordsInTitle)
    loremGenerator.getPhrase(numWs, capitaliseTitle, chopPeriodFromTitle)
  }

  def getLessonPlanTitles(numOfLsns:Int):Seq[String] = {
    val lsns = new Array[String](numOfLsns)
    for (i <- 0 until numOfLsns)
      if (lsnTitlesIterator.hasNext) lsns(i) = lsnTitlesIterator.next()
      else throw new NoSuchElementException("Not enough lesson titles")
    lsns
  }

  def registerTitle(s:String) = {
    loremGenerator.protectPhrase(s)
  }

  private def getIntBetween(max:Int, min:Int):Int = {
    if (max==min) min else Random.nextInt(max-min) + min
  }


}
