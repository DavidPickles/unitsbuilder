package com.picklestech.sch.contgen.words

/**
 * Created by davidpickles on 27/03/15.
 * Would like to replace use of String for paras and sentences with this.
 */
trait Words {

  def content:String

  def toHtmlParas:HtmlParas

  def toPlaintextParas:PlaintextParas

  def toSentence(numWds:Int):Sentence

  def toPhrase(numWds:Int, captialise: Boolean, includeFinalStop:Boolean):Phrase

  def toTitle(includeFinalStop: Boolean):Title

  override def toString():String = content
}
