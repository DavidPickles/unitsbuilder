import java.io.PrintWriter
import com.picklestech.sch.contgen._
import com.picklestech.sch.contgen.loremreaders.LoripsumWsReader
import scala.io.Source

val in =  Source.fromFile("/Users/davidpickles/Projects/scheme/scala/unitsbuilder/data/unitdefs.def")
val out = new PrintWriter(Console.out,true)
val loripsumWsReader = new LoripsumWsReader
val parser = new WULineParser(new WUFactory(new WUSupplementer(LsnTitles.titles, new LoremGenerator(loripsumWsReader))))
def run() {
  for (ln <- in.getLines) {
    Console.err.println(ln)
    parser parseLine (ln)
  }
}

Console.err.println("started")
run()
Console.err.println("done")