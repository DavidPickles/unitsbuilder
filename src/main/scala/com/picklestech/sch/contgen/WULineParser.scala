package com.picklestech.sch.contgen


/**
 * Created by davidpickles on 20/03/15.
 */
class WULineParser(val wUFactory:WUFactory) {

  val state = new PrototypeWU() // this is the state of the parser
  
  private val linePattern = """^(title|sub|yg|numLsns|tt)\s*=\s*(.+)$""".r

  def parseLine(ln:String):Option[WU] = {
    val tln = ln.trim()
    tln match {
      case linePattern(name,value) => addToState(name,value); None
      case "!" => Some(end())
      case "" => None
    }
  }

  private def end():WU = {
    val wu = wUFactory.makeWorkUnit(state)
    state.body = None
    state.lsnTitles = None
    state.summary = None
    state.title = None // all other state is retained
    wu
  }

  private def addToState(name:String, value:String)  {
    name match {
      case "title" => state.title = Option(value)
      case "sub" => state.subject = Option(value)
      case "yg" => state.yearGroup = Option(value)
      case "tt" => state.topics = Option(value.split("""\s*\|\s*"""))
      case "numLsns" => state.numOfLsns = Option(value.toInt)
    }



  }
}
