package com.pacb.itg.metrics.subreadset

import java.nio.file.Path

import falkner.jayson.metrics.{Metric, Str}

import scala.xml.{Elem, Node, XML}


object SubreadSet_v4_0_0 {

  val version = "4.0.0"

  def apply(p: Path, parsedXml: Elem): SubreadSet_v4_0_0 = apply(p, Some(parsedXml))
  def apply(p: Path, parsedXml: Option[Elem] = None): SubreadSet_v4_0_0 = {
    parsedXml match {
      case Some(xml) => new SubreadSet_v4_0_0(p, xml)
      case None => new SubreadSet_v4_0_0(p, XML.loadFile(p.toFile))
    }
  }
}

class SubreadSet_v4_0_0(override val p: Path, override val xml: Node) extends SubreadSet_v3_0_1(p, xml) {
  override val version = s"${SubreadSet.version}~${SubreadSet_v4_0_0.version}"

  // unique to 4.0.0 (Seabiscuit)
  lazy val unique_4_0_0: List[Metric] = List(
    Str("Code Version", SubreadSet.version),
    Str("Spec Version", SubreadSet_v4_0_0.version)
  )

  override val values: List[Metric] = unique_4_0_0 ++ shared

}
