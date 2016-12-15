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

  def movieInCellIndex(wellMovie: String): Int =
    SubreadSet_v3_0_1(Paths.get(s"/pbi/collections/312/3120185/r54009_20161107_213956/$wellMovie.subreadset.xml")).movieInCellIndex

  // first 3 cells worth of movies for r54009_20161107_213956
  // cell index, collection count = movie in cell index
  "Movie In Chip Index" should {
    // first cell. movies 0-4
    "0, 0 = 0" in (movieInCellIndex("1_A01/m54009_161107_214323") mustEqual 0)
    "0, 1 = 1" in (movieInCellIndex("2_A01/m54009_161107_234350") mustEqual 1)
    "0, 2 = 2" in (movieInCellIndex("3_A01/m54009_161108_001110") mustEqual 2)
    "0, 3 = 3" in (movieInCellIndex("4_A01/m54009_161108_003820") mustEqual 3)
    "0, 4 = 4" in (movieInCellIndex("5_A01/m54009_161108_010450") mustEqual 4)
    // second cell. movies 0-5
    "1, 5 = 0" in (movieInCellIndex("6_B01/m54009_161107_235309") mustEqual 0)
    "1, 6 = 1" in (movieInCellIndex("7_B01/m54009_161108_020410") mustEqual 1)
    "1, 7 = 2" in (movieInCellIndex("8_B01/m54009_161108_023130") mustEqual 2)
    "1, 8 = 3" in (movieInCellIndex("9_B01/m54009_161108_025846") mustEqual 3)
    "1, 9 = 4" in (movieInCellIndex("10_B01/m54009_161108_032517") mustEqual 4)
    // third cell. movies 0-5
    "2, 10 = 0" in (movieInCellIndex("11_C01/m54009_161108_021330") mustEqual 0)
    "2, 11 = 1" in (movieInCellIndex("12_C01/m54009_161108_042434") mustEqual 1)
    "2, 12 = 2" in (movieInCellIndex("13_C01/m54009_161108_045145") mustEqual 2)
    "2, 13 = 3" in (movieInCellIndex("14_C01/m54009_161108_051855") mustEqual 3)
    "2, 14 = 4" in (movieInCellIndex("15_C01/m54009_161108_054553") mustEqual 4)
  }
}