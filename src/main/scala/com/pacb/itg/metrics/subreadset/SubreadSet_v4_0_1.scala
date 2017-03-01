package com.pacb.itg.metrics.subreadset

import java.nio.file.Path

import falkner.jayson.metrics.{Metric, Str}

import scala.xml.{Elem, Node, XML}

/**
  * Discovered 4.0.1 data while working on ITG-466. Added support in ITG-470.
  *
  * Assuming a straight pass-through of 4.0.0's format is appropriate. Will update the docs here if any sort of
  * changelog or similar is discovered.
  */
object SubreadSet_v4_0_1 {

  val version = "4.0.1"

  def apply(p: Path, parsedXml: Elem): SubreadSet_v4_0_1 = apply(p, Some(parsedXml))
  def apply(p: Path, parsedXml: Option[Elem] = None): SubreadSet_v4_0_1 = {
    parsedXml match {
      case Some(xml) => new SubreadSet_v4_0_1(p, xml)
      case None => new SubreadSet_v4_0_1(p, XML.loadFile(p.toFile))
    }
  }
}

class SubreadSet_v4_0_1(override val p: Path, override val xml: Node) extends SubreadSet_v3_0_1(p, xml) {
  override val version = s"${SubreadSet.version}~${SubreadSet_v4_0_1.version}"

  // unique to 4.0.1 (Seabiscuit)
  lazy val unique_4_0_1: List[Metric] = List(
    Str("Code Version", SubreadSet.version),
    Str("Spec Version", SubreadSet_v4_0_1.version)
  )

  override val values: List[Metric] = unique_4_0_1 ++ shared

}