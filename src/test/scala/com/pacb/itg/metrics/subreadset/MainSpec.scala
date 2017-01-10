package com.pacb.itg.metrics.subreadset

import java.io.ByteArrayOutputStream
import java.nio.file.{Files, Paths}

import org.specs2.mutable.Specification


/**
  * Command-line tool tests
  */
class MainSpec extends Specification with TestData {

  sequential

  val srsPath = Paths.get(s"$testData$movie$movieTsName.subreadset.xml")

  "Main class" should {
    "Show usage if wrong args" in {
      val buf = new ByteArrayOutputStream()
      Console.withOut(buf) {
        Main.main(Array())
      }
      buf.toString.trim mustEqual Main.usage
    }
    "Process subreadset.xml" in {
      val tmp = Files.createTempFile("output", "json")
      val buf = new ByteArrayOutputStream()
      Console.withOut(buf) {
        Main.main(Array(srsPath.toString, tmp.toAbsolutePath.toString))
      }
      new String(Files.readAllBytes(tmp)).contains(movieTsName) mustEqual true // values tested in SubreadSetSpec
      buf.toString.trim mustEqual ""
    }
  }
}