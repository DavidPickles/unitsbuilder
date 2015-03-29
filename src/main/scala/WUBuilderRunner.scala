import java.io.{FileInputStream, PrintWriter}

import com.picklestech.sch.contgen._
import com.picklestech.sch.contgen.loremreaders.LoripsumWsReader

import scala.io.Source

/**
 * Created by davidpickles on 27/03/15.
 */
object WUBuilderRunner extends App {

  val in = if (args.length==1) Source.fromFile(args(0)) else Source.stdin
  val out = new PrintWriter(Console.out)

  val loripsumWsReader = new LoripsumWsReader

  val parser = new WULineParser(new WUFactory(new WUSupplementer(LsnTitles.titles, new LoremGenerator(loripsumWsReader))))

  val builder = new WUBuilder(parser, in, out)

  try {
    builder.run(Console.err.print("."))
  } catch {
    case e: Exception => {
      Console.err.println(s"Around line ${builder.lineCtr}, Unit of Work ${builder.wuCtr + 1}")
      e.printStackTrace(Console.err)
      System.exit(1)
    }
  }

  in.close()
  out.flush()
  out.close()

  Console.err.println(s"${builder.lineCtr} lines")
  Console.err.println(s"${builder.wuCtr} units of work")

  
}
