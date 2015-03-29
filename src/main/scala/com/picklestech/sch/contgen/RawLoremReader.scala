package com.picklestech.sch.contgen

/**
 * Created by davidpickles on 20/03/15.
 */
abstract class RawLoremReader {

  def getSentence(numWords:Int):String

  def getParas(num: Int, length: ParaLength, htmlMarkup: Boolean): String

}
