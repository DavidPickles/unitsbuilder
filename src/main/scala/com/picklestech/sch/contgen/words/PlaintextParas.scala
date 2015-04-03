package com.picklestech.sch.contgen.words

/**
 * Created by davidpickles on 02/04/2015.
 */
trait PlaintextParas extends Words {

  def toPlaintextParas:PlaintextParas = this


  def toHtmlParas:HtmlParas = {
    val newContent = this.content // do something to the content
    new HtmlParas { val content = newContent }
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
