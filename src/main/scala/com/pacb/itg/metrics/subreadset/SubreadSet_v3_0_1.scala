package com.pacb.itg.metrics.subreadset

import java.nio.file.{Files, Path}

import falkner.jayson.metrics._
import scala.xml.{Elem, Node, XML}
import scala.collection.JavaConverters._


/**
  * Based on /pbi/collections/312/3120166/r54054_20160902_214708/4_A01/m54054_160903_003144.subreadset.xml
  */
object SubreadSet_v3_0_1 {

  val version = "3.0.1"

  def apply(p: Path, parsedXml: Elem): SubreadSet_v3_0_1 = apply(p, Some(parsedXml))
  def apply(p: Path, parsedXml: Option[Elem] = None): SubreadSet_v3_0_1 = {
    parsedXml match {
      case Some(xml) => new SubreadSet_v3_0_1(p, xml)
      case None => new SubreadSet_v3_0_1(p, XML.loadFile(p.toFile))
    }
  }
}

/**
  * Exports as much per-movie meta-data as possible
  *
  * This code is intended to flatten the relatively complex XML files used to export run related meta-data so that
  * simple analyses can be done via Excel, JMP and other tools that leverage tabular data.
  *
  * Scala's support for XPath-style XML manipulation is used here to clarify where in a file data is being pulled from.
  */
class SubreadSet_v3_0_1(val p: Path, val xml: Node) extends Metrics {

  lazy val movie = p.getFileName.toString.stripSuffix(".subreadset.xml")

  // weird one where the instrument appears to save all the collections even though the subreadset represents just one
  lazy val cmd: Node = (xml \\ "CollectionMetadata").filter(n => (n \ "@Context").text == movie).head.asInstanceOf[Node]

  def getAutomationParam(name: String): String =
    (cmd \ "Automation" \ "AutomationParameters" \ "AutomationParameter").filter(n => (n \ "@Name").text == name).map(_ \ "@SimpleValue").head.text

  def reagentTube(part: String) = (cmd \ "SequencingKitPlate" \ "ReagentTubes").filter(n => (n \ "@PartNumber").text == part).head

  override val namespace = "SS"
  override val version = s"${SubreadSet.version}~${SubreadSet_v3_0_1.version}"
  lazy val unique: List[Metric] = List(
    Str("Code Version", SubreadSet.version),
    Str("Spec Version", SubreadSet_v3_0_1.version)
  )
  // used in 4.0.0 (aka Goat)
  lazy val shared: List[Metric] = List(
    Str("Path", p.toAbsolutePath.toString),
    // TODO: add the external resources links. points to .pbi (PacBio index) and .sts.xml (chips stats file)
    Num("Total Length", (xml \ "DataSetMetadata" \ "TotalLength").text),
    Num("Num Records", (xml \ "DataSetMetadata" \ "NumRecords").text),
    Str("Version", (xml \ "@Version").text),
    Str("UUID", (xml \ "@UniqueId").text),
    Str("Created At", (xml \ "@CreatedAt").text),
    Str("Time-Stamped Name", (xml \ "@TimeStampedName").text),
    Str("Name", (xml \ "@Name").text),
    Str("Meta-Type", (xml \ "@MetaType").text),
    Str("Tags", (xml \ "@Tags").text),
    Str("Context", (cmd \ "@Context").text),
    Str("Status", (cmd \ "@Status").text),
    Str("Instrument ID", (cmd \ "@InstrumentId").text),
    Str("Instrument Name", (cmd \ "@InstrumentName").text),
    Str("Collection: UUID", (cmd \ "@UniqueId").text),
    Str("Collection: Meta-Type", (cmd \ "@MetaType").text),
    Str("Collection: Time-Stamped Name", (cmd \ "@TimeStampedName").text),
    Str("Collection: Created At", (cmd \ "@CreatedAt").text),
    Str("Collection: Modified At", (cmd \ "@ModifiedAt").text),
    Str("Run: Name", (cmd \ "RunDetails" \ "Name").map(_.text).head),
    Str("Run: Time-Stamped Name", (cmd \ "RunDetails" \ "TimeStampedName").map(_.text).head),
    Str("Run: When Created", (cmd \ "RunDetails" \ "WhenCreated").map(_.text).head),
    Str("Run: When Started", (cmd \ "RunDetails" \ "WhenStarted").map(_.text).head),
    Str("InstCtrlVer", (cmd \ "InstCtrlVer").map(_.text).head),
    Str("SigProcVer", (cmd \ "SigProcVer").map(_.text).head),
    Num("Cell Index", (cmd \ "CellIndex").map(_.text).head),
    Num("Movie Index In Cell", movieInCellIndex),
    Str("Well", (cmd \ "WellSample" \ "@Name").map(_.text).head),
    //Str("Well: Description", (cmd \ "WellSample" \ "@Description").map(_.text).head), // never used?
    Str("Well: Name", (cmd \ "WellSample" \ "WellName").map(_.text).head),
    Str("Concentration", (cmd \ "WellSample" \ "Concentration").map(_.text).head),
    Num("Insert Size", (cmd \ "WellSample" \ "InsertSize").map(_.text).head),
    Bool("Sample Reuse Enabled", (cmd \ "WellSample" \ "SampleReuseEnabled").map(_.text).head.toBoolean),
    Bool("Stage Hotstart Enabled", (cmd \ "WellSample" \ "StageHotstartEnabled").map(_.text).head.toBoolean),
    Bool("Size Selection Enabled", (cmd \ "WellSample" \ "SizeSelectionEnabled").map(_.text).head.toBoolean),
    Num("Use Count", (cmd \ "WellSample" \ "UseCount").map(_.text).head),
    Str("DNA Control Complex", (cmd \ "WellSample" \ "DNAControlComplex").map(_.text).head),
    Str("Automation: Name", (cmd \ "Automation" \ "@Name").map(_.text).head),
    Bool("Reuse Cell", getAutomationParam("ReuseCell").toBoolean),
    Num("Cell Reuse Index", getAutomationParam("CellReuseIndex")),
    Num("Automation: Insert Size", getAutomationParam("InsertSize")),
    Bool("Reuse Reagents", getAutomationParam("ReuseReagents").toBoolean),
    Num("Reuse Reagents Index", getAutomationParam("ReuseReagentsIndex")),
    Num("Coupler Laser Power", getAutomationParam("CouplerLaserPower")),
    Num("Automation: Movie Length", getAutomationParam("MovieLength")),
    Num("Cell At Stage", getAutomationParam("CellAtStage")),
    Bool("Skip Alignment", getAutomationParam("SkipAlignment").toBoolean),
    Bool("Use Stage Hotstart", getAutomationParam("UseStageHotStart").toBoolean),
    Str("Laser Titration", getAutomationParam("LaserTitration")),
    Num("Immobilization Time", getAutomationParam("ImmobilizationTime")),
    Num("Collection Number", getAutomationParam("CollectionNumber")),
    Str("CellPac: Part Number", (cmd \ "CellPac" \ "@PartNumber").map(_.text).head),
    Str("CellPac: Lot Number", (cmd \ "CellPac" \ "@LotNumber").map(_.text).head),
    Str("CellPac: Barcode", (cmd \ "CellPac" \ "@Barcode").map(_.text).head),
    Str("CellPac: Expiration", (cmd \ "CellPac" \ "@ExpirationDate").map(_.text).head),
    Str("Template Kit: Min Insert Size", (cmd \ "TemplatePrepKit" \ "@MinInsertSize").map(_.text).head),
    Str("Template Kit: Max Insert Size", (cmd \ "TemplatePrepKit" \ "@MaxInsertSize").map(_.text).head),
    Str("Template Kit: Part Number", (cmd \ "TemplatePrepKit" \ "@PartNumber").map(_.text).head),
    Str("Template Kit: Lot Number", (cmd \ "TemplatePrepKit" \ "@LotNumber").map(_.text).head),
    Str("Template Kit: Barcode", (cmd \ "TemplatePrepKit" \ "@Barcode").map(_.text).head),
    Str("Template Kit: Expiration", (cmd \ "TemplatePrepKit" \ "@ExpirationDate").map(_.text).head),
    Str("Template Kit: Name", (cmd \ "TemplatePrepKit" \ "@Name").map(_.text).head),
    Str("Template Kit: Left Adaptor Sequence", (cmd \ "TemplatePrepKit" \ "LeftAdaptorSequence").map(_.text).head),
    Str("Template Kit: Right Adaptor Sequence", (cmd \ "TemplatePrepKit" \ "RightAdaptorSequence").map(_.text).head),
    Str("Binding Kit: Part Number", (cmd \ "BindingKit" \ "@PartNumber").map(_.text).head),
    Str("Binding Kit: Lot Number", (cmd \ "BindingKit" \ "@LotNumber").map(_.text).head),
    Str("Binding Kit: Barcode", (cmd \ "BindingKit" \ "@Barcode").map(_.text).head),
    Str("Binding Kit: Expiration", (cmd \ "BindingKit" \ "@ExpirationDate").map(_.text).head),
    Str("Binding Kit: Name", (cmd \ "BindingKit" \ "@Name").map(_.text).head),
    Str("Sequencing Kit: Part Number", (cmd \ "SequencingKitPlate" \ "@PartNumber").map(_.text).head),
    Str("Sequencing Kit: Lot Number", (cmd \ "SequencingKitPlate" \ "@LotNumber").map(_.text).head),
    Str("Sequencing Kit: Barcode", (cmd \ "SequencingKitPlate" \ "@Barcode").map(_.text).head),
    Str("Sequencing Kit: Expiration", (cmd \ "SequencingKitPlate" \ "@ExpirationDate").map(_.text).head),
    Str("Sequencing Kit: Name", (cmd \ "SequencingKitPlate" \ "@Name").map(_.text).head),
    // 100-619-600 = MineralOil
    Str("100-619-600: Lot", (reagentTube("100-619-600") \ "@LotNumber").head.text),
    Str("100-619-600: Expiration", (reagentTube("100-619-600") \ "@ExpirationDate").head.text),
    // 100-619-700 = OSenzyme
    Str("100-619-700: Lot", (reagentTube("100-619-700") \ "@LotNumber").head.text),
    Str("100-619-700: Expiration", (reagentTube("100-619-700") \ "@ExpirationDate").head.text),
    // TODO: Add ReagentTubes ... may differ depending on experiment. Lookup by PartNumber, then have PartNumber, LotNumber and ExpirationDate
    Str("Primary: Automation Name", (cmd \ "Primary" \ "AutomationName").map(_.text).head),
    Str("Primary: Config File", (cmd \ "Primary" \ "ConfigFileName").map(_.text).head),
    Str("Primary: Sequencing Condition", (cmd \ "Primary" \ "SequencingCondition").map(_.text).head),
    //Str("Primary: Copy Files", (cmd \ "Primary" \ "OutputOptions" \ "CopyFiles" \ "CollectionFileCopy").map(_.text)),
    Str("Readout", (cmd \ "Primary" \ "OutputOptions" \ "Readout").map(_.text).head),
    Str("Metrics Verbosity", (cmd \ "Primary" \ "OutputOptions" \ "MetricsVerbosity").map(_.text).head),
    Str("Transfer Resource: ID", (cmd \ "Primary" \ "OutputOptions" \ "TransferResource" \ "Id").map(_.text).head),
    Str("Transfer Resource: Scheme", (cmd \ "Primary" \ "OutputOptions" \ "TransferResource" \ "TransferScheme").map(_.text).head),
    Str("Transfer Resource: Name", (cmd \ "Primary" \ "OutputOptions" \ "TransferResource" \ "Name").map(_.text).head),
    //Str("Transfer Resource: Description", (cmd \ "Primary" \ "OutputOptions" \ "TransferResource" \ "Description").map(_.text).head),
    Str("Transfer Resource: Dest Path", (cmd \ "Primary" \ "OutputOptions" \ "TransferResource" \ "DestPath").map(_.text).head)
    // TODO: Secondary has a new metrics. None seem important to inlclude
  )
  override val values: List[Metric] = unique ++ shared

  /**
    * See ITG-281 this may be a bug where "Use Count" isn't populated correctly
    * EOL QC needs this, it'll probably work fine to derive it from the directory structure and it arguably should be
    * present in subreadset.xml. Adding the code here until Primary can add (or fix) such a number in *.subreadset.xml
    *
    * The modulus 5 trick here only works because EOL QC does laser titrations of 5x
    */
  def movieInCellIndex: Int = p.toString match {
    case s if s.contains("/pbi/collections/312") => asString("Collection Number").toInt % 5
    case _ => throw new Exception("Can't reliably calculate non-EOL QC movieIndexInCell. See ITG-386")
  }
}