package com.picklestech.sch.contgen

/**
 * Created by davidpickles on 20/03/15.
 */
sealed abstract class ParaLength
case object ShortPara extends ParaLength
case object MediumPara extends ParaLength
case object LongPara extends ParaLength
case object VeryLongPara extends ParaLength

