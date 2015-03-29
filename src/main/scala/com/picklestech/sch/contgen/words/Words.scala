package com.picklestech.sch.contgen.words

/**
 * Created by davidpickles on 27/03/15.
 * Would like to replace use of String for paras and sentences with this.
 */
class Words(val content:String) {

  def addHtmlParaMarkup():Words = {
    this
  }

  def removeHtmParalMarkup():Words = {
    this
  }

  def extractSentence(numWds:Int):Words = {
    this
  }

  def extractPhrase(numWds:Int, captialise: Boolean, includeFinalStop:Boolean):Words = {
    this
  }
}
