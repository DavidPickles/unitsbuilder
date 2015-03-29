package com.picklestech.sch.contgen

/**
 * Created by davidpickles on 21/03/15.
 */
class WU private (mwu: PrototypeWU) {
  val title = mwu.title.get
  val subject = mwu.subject.get
  val yearGroup = mwu.yearGroup.get
  val summary = mwu.summary.get
  val body = mwu.body.get
  val lsnTitles = mwu.lsnTitles.get
  val topics = mwu.topics.get

  def mkString(sep:String, listSep:String):String = {
    title +sep+ subject +sep+ yearGroup +sep+ lsnTitles.mkString(listSep) +sep+
      topics.mkString(listSep) +sep+ summary +sep+ body
  }
}

object WU {

  def apply(mwu: PrototypeWU) = {
    validate(mwu)
    new WU(mwu)
  }

  def validate(mwu: PrototypeWU) = {
    valueChecker("title",mwu.title)
    valueChecker("subject",mwu.subject)
    valueChecker("year group",mwu.yearGroup)
    valueChecker("summary",mwu.summary)
    valueChecker("body",mwu.body)
    valuesChecker("lesson titles",mwu.lsnTitles)
    valuesChecker("topics",mwu.topics)
  }

  private def valueChecker(name:String, value: Option[String]) = {
    require(value.isDefined,s"$name is not defined")
    require(value.get!=null,s"$name is null")
    require(value.get.trim!="",s"$name is empty string")
  }

  private def valuesChecker(name:String, values: Option[Seq[String]])  = {
    require(values.isDefined,s"$name is not defined")
    require(values.get!=null,s"$name is null")
    require(!values.get.isEmpty,s"$name is empty")
    require (values.get.forall { (e) => e != null && e.trim != "" },s"one of $name is null or empty string")
  }



}
