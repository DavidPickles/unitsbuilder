package com.picklestech.sch.contgen

/**
 * Created by davidpickles on 20/03/15.
 */
class WUFactory(val supplementer: WUSupplementer) {

  def makeWorkUnit(prototype: PrototypeWU): WU = {
    if (prototype.lsnTitles.isEmpty) prototype.lsnTitles = Option(supplementer.getLessonPlanTitles(prototype.numOfLsns.get))
    if (prototype.title.isEmpty) prototype.title = Option(supplementer.inventTitle())
    if (prototype.summary.isEmpty) prototype.summary = Option(supplementer.inventSummary())
    if (prototype.body.isEmpty) prototype.body = Option(supplementer.inventBody())
    supplementer.registerTitle(prototype.title.get)
    WU(prototype)
  }


}
