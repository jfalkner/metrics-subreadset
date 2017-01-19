package com.pacb.itg.metrics.subreadset

import java.nio.file.Paths

import org.specs2.mutable.Specification


/**
  * Tests 4.0.1 (aka Seabiscuit) files
  *
  * Not much changed in the format for this release.
  */
class v4_0_0_SeabiscuitSpec extends Specification with TestData {

  // /pbi/collections/315/3150529/r54003_20161209_231018/1_A01/m54003_161209_232044.transferdone
  val base = Paths.get("/pbi/dept/itg/test-data/subreadset/4_0_0_seabiscuit")
  val sp = base.resolve("pbi/collections/315/3150529/r54003_20161209_231018/1_A01/m54003_161209_232044.subreadset.xml")
  lazy val srs = SubreadSet(sp)

  // first 3 cells worth of movies for r54009_20161107_213956
  // cell index, collection count = movie in cell index
  "Seabicuit subreadset.xml" should {
    "subreadset.xml parses" in (SubreadSet_v4_0_0(sp).asString("Spec Version") mustEqual "4.0.0")
    "CellPac parses for MES lookup" in (srs.asString("CellPac: Barcode") mustEqual "BA037127636169224441441810")
    "Name" in (srs.asString("Name") mustEqual "2016_12_09_A3_SAT_Ecoli_3150529")

    //"WellSample@Description" in (srs.asString("Well: Description") must throwA(new NoSuchElementException("head of empty list"))) // doesn't exist?
  }
}