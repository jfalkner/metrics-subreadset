package com.pacb.itg.metrics.subreadset

import java.nio.file.{Files, Path, Paths}

import falkner.jayson.metrics.Metrics

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

  val version = "0.0.10"

  lazy val blank = new SubreadSet(null, null)

  lazy val currentVersion = blank.version

  def badPath(p: Path): String = s"Path $p must be a .subreadset.xml file that exists or directory with one in it."

  def unsupportedVersion(version: String): String = s"Version $version not currently supported"

  // placeholder to support other versions down the road
  def apply(p: Path): Metrics = {
    Files.exists(p) match {
      case true => Files.isDirectory(p) match {
        case false => loadVersion(p)
        case _ => loadVersion(Files.list(p).toArray.toList.asInstanceOf[List[Path]].filter(_.toString.endsWith(".subreadset.xml")).head)
      }
      case _ => throw new Exception(badPath(p))
    }
  }

  // check what version exists and send it back
  def loadVersion(p: Path): Metrics ={
    val srs = XML.loadFile(p.toFile)
    val version = (srs \ "@Version").text
    version match {
      case "4.0.0" => SubreadSet_v4_0_0(p, srs)
      case "3.0.1" => SubreadSet_v3_0_1(p, srs)
      case _ => throw new Exception(unsupportedVersion(version))
    }
  }
}


// current latest version
class SubreadSet(p: Path, xml: Elem) extends SubreadSet_v4_0_0(p, xml)