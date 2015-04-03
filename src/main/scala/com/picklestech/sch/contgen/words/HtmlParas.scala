package com.picklestech.sch.contgen.words

/**
 * Created by davidpickles on 02/04/2015.
 */
trait HtmlParas extends Words {

  def toHtmlParas:HtmlParas = this

  def toPlaintextParas:PlaintextParas = {
    val newContent = this.content // do something to the content
    new PlaintextParas { val content = newContent }
  }

  def toSentence(numWds:Int):Sentence = {
    val newContent = this.content // do something to the content
    new Sentence() { val content = newContent }
  }

  def toPhrase(numWds:Int, captialise: Boolean, includeFinalStop:Boolean):Phrase = {
    val newContent = this.content // do something to the content
    new Phrase { val content = newContent }
  }

  def toTitle(includeFinalStop: Boolean):Title = {
    val newContent = this.content // do something to the content
    new Title { val content = newContent }
  }

}
