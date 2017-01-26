package com.pacb.itg.metrics.subreadset

import java.nio.file.Paths
import org.specs2.mutable.Specification


/**
  * Tests the derived "Movie In Cell Index" value from ITG-281
  *
  * This is a one-off test beause this value is calculated here. It'd be preferred to have Primary export this and have
  * it directly extracted from the XML. Similar to SubreadSetSpec.
  */
class MovieInCellIndexSpec extends Specification with TestData {

  sequential

  def movieInCellIndex(prefix: String, wellMovie: String): Int =
    SubreadSet_v3_0_1(Paths.get(s"$prefix/$wellMovie.subreadset.xml")).movieInCellIndex

  // first 3 cells worth of movies for r54009_20161107_213956
  // cell index, collection count = movie in cell index
  "Movie In Chip Index" should {
    val testData = "/pbi/dept/itg/test-data"
    val r54009_20161107_213956 = "/pbi/collections/312/3120185/r54009_20161107_213956"
    // first cell. movies 0-4
    "0, 0 = 0" in (movieInCellIndex(r54009_20161107_213956, "1_A01/m54009_161107_214323") mustEqual 0)
    "0, 1 = 1" in (movieInCellIndex(r54009_20161107_213956, "2_A01/m54009_161107_234350") mustEqual 1)
    "0, 2 = 2" in (movieInCellIndex(r54009_20161107_213956, "3_A01/m54009_161108_001110") mustEqual 2)
    "0, 3 = 3" in (movieInCellIndex(r54009_20161107_213956, "4_A01/m54009_161108_003820") mustEqual 3)
    "0, 4 = 4" in (movieInCellIndex(r54009_20161107_213956, "5_A01/m54009_161108_010450") mustEqual 4)
    // second cell. movies 0-5
    "1, 5 = 0" in (movieInCellIndex(r54009_20161107_213956, "6_B01/m54009_161107_235309") mustEqual 0)
    "1, 6 = 1" in (movieInCellIndex(r54009_20161107_213956, "7_B01/m54009_161108_020410") mustEqual 1)
    "1, 7 = 2" in (movieInCellIndex(r54009_20161107_213956, "8_B01/m54009_161108_023130") mustEqual 2)
    "1, 8 = 3" in (movieInCellIndex(r54009_20161107_213956, "9_B01/m54009_161108_025846") mustEqual 3)
    "1, 9 = 4" in (movieInCellIndex(r54009_20161107_213956, "10_B01/m54009_161108_032517") mustEqual 4)
    // third cell. movies 0-5
    "2, 10 = 0" in (movieInCellIndex(r54009_20161107_213956, "11_C01/m54009_161108_021330") mustEqual 0)
    "2, 11 = 1" in (movieInCellIndex(r54009_20161107_213956, "12_C01/m54009_161108_042434") mustEqual 1)
    "2, 12 = 2" in (movieInCellIndex(r54009_20161107_213956, "13_C01/m54009_161108_045145") mustEqual 2)
    "2, 13 = 3" in (movieInCellIndex(r54009_20161107_213956, "14_C01/m54009_161108_051855") mustEqual 3)
    "2, 14 = 4" in (movieInCellIndex(r54009_20161107_213956, "15_C01/m54009_161108_054553") mustEqual 4)
    // ITG-386 bug. if instrument fails to transfer file, then the old logic will have the wrong calculated index
    val r54134_20170120_223549 = s"$testData/ITG-386/r54134_20170120_223549"
    "6_B01 = 0" in (movieInCellIndex(r54134_20170120_223549, "6_B01/m54134_170121_004258") mustEqual 0)
    "7_B01 = 1" in (movieInCellIndex(r54134_20170120_223549, "7_B01/m54134_170121_024222") mustEqual 1)
    "8_B01 = 2" in (movieInCellIndex(r54134_20170120_223549, "8_B01/m54134_170121_030009") mustEqual 2)
    "9_B01 = 3" in (movieInCellIndex(r54134_20170120_223549, "9_B01/m54134_170121_031800") mustEqual 3)
    "10_B01 = 4" in (movieInCellIndex(r54134_20170120_223549, "10_B01/m54134_170121_033545") mustEqual 4)
  }
}