package com.pacb.itg.metrics.subreadset

import java.nio.file.{Files, Path, Paths}

import scala.collection.JavaConverters._
import scala.xml.{Elem, XML}

/**
  * Creates versioned instances of *.subreadset.xml files
  *
  * XSD locations: http://swarm/projects/xsd-models/files/mainline/xsd
  *
  * There appears to be no version info for the schemas. We should keep a copy somewhere in this repo.
  *
  * For now, this code assumes 3.0.1 release and associated schemas, but it is being architected so that as the XML files
  * change the API use remains similar and it still works.
  */
object SubreadSet {

  // placeholder to support other versions down the road
  def apply(p: Path): SubreadSet_v3_0_1 = {
    Files.exists(p) match {
      case true => Files.isDirectory(p) match {
        case false => loadVersion(p)
        case _ => loadVersion(Files.list(p).toArray.toList.asInstanceOf[List[Path]].filter(_.toString.endsWith(".subreadset.xml")).head)
      }
      case _ => throw new Exception(s"Path $p must be a .subreadset.xml file that exists or directory with one in it.")
    }
  }

  // check what version exists and send it back
  def loadVersion(p: Path): SubreadSet_v3_0_1 ={
    val srs = XML.loadFile(p.toFile)
    val version = (srs \ "@Version").text
    version match {
      case "3.0.1" => SubreadSet_v3_0_1(p, srs)
      case _ => throw new Exception(s"Version $version not currently supported")
    }
  }
}


// current latest version
class SubreadSet(p: Path, xml: Elem) extends SubreadSet_v3_0_1(p, xml)