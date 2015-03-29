package com.picklestech.sch.contgen

import java.io.PrintWriter

import scala.io.Source


/**
 * Created by davidpickles on 20/03/15.
 */
class WUBuilder(val parser:WULineParser, val in:Source, val out:PrintWriter) {

  var wuCtr = 0
  var lineCtr = 0
  val titleLine = "title\tsubject\tyg\tlessons\ttt\tsummary\tbody"

  def run(progress: =>Unit) {
    out.println(titleLine)
    for (ln <- in.getLines) {
      lineCtr += 1
      parser parseLine (ln) foreach { wu => // if parser gives you some data, do something with it
        wuCtr += 1
        out.println(wu.mkString("\t","|"))
        progress
      }
    }
  }

}
