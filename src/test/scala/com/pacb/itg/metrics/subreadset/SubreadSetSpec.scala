package com.pacb.itg.metrics.subreadset

import java.nio.file.Paths

import org.specs2.mutable.Specification


class SubreadSetSpec extends Specification with TestData {

  val subreadFileName = s"$movieTsName.subreadset.xml"
  val tsName = "SubreadSetCollection_160924_021453999"
  val srsPath = Paths.get(testData + movie + subreadFileName)
  lazy val srs = SubreadSet(movieDir)

  "SubreadSet checking" should {
    "Load from movie directory" in (srs.asString("Time-Stamped Name") mustEqual tsName)
    "Name" in (srs.asString("Name") mustEqual "09232016_A88_LVP19_11kb_Batch_Qual+Liner_Chk")
    "UniqueID" in (srs.asString("UUID") mustEqual  "ef4f6dd1-d62a-4c94-90ff-c7ebda0eca82")
    "CreatedAt String" in (srs.asString("Created At") mustEqual "2016-09-24T02:14:53Z")
    "Tags" in (srs.asString("Tags") mustEqual "subreadset")
    "TotalLength String" in (srs.asString("Total Length") mustEqual "532500977")
    "NumRecords String" in (srs.asString("Num Records") mustEqual "153993")
    "CollectionMetaData.InstrumentId" in (srs.asString("Instrument ID") mustEqual "54088")
    "CollectionMetaData.InstrumentName" in (srs.asString("Instrument Name") mustEqual "Sequel")
    "CollectionMetaData.UniqueId" in (srs.asString("Collection: UUID") mustEqual "ef4f6dd1-d62a-4c94-90ff-c7ebda0eca82")
    "CollectionMetaData.TimestampedName" in (srs.asString("Collection: Time-Stamped Name") mustEqual "54088-CollectionMetadata-2016-32-23T21:32:53.968Z")
    "CollectionMetaData.Status" in (srs.asString("Status") mustEqual "Ready")
    "CollectionMetaData.CreatedAt" in (srs.asString("Collection: Created At") mustEqual "2016-09-23T19:40:37.65Z")
    "CollectionMetaData.ModifiedAt" in (srs.asString("Collection: Modified At") mustEqual "0001-01-01T00:00:00")
    "CollectionMetaData.InstCtrlVer" in (srs.asString("InstCtrlVer") mustEqual "3.1.2.183937")
    "CollectionMetaData.SigProcVer" in (srs.asString("SigProcVer") mustEqual "3.1.1-182013")
    "CollectionMetaData.RunDetails.TimeStampName" in (srs.asString("Run: Time-Stamped Name") mustEqual "r54088_20160923_213253")
    "CollectionMetaData.RunDetails.Name" in (srs.asString("Run: Name") mustEqual "09232016_A88_LVP19_11kb_Batch_Qual+Liner_Chk")
    "CollectionMetaData.RunDetails.WhenCreated" in (srs.asString("Run: When Created") mustEqual "2016-09-23T19:40:37.65Z")
    "CollectionMetaData.RunDetails.WhenStarted" in (srs.asString("Run: When Started") mustEqual "2016-09-23T21:37:08.213808Z")
    "CollectionMetaData.WellSample.WellName" in (srs.asString("Well: Name") mustEqual "A01")
    "CollectionMetaData.WellSample.Concentration Raw String" in (srs.asString("Concentration") mustEqual "0.0")
    "CollectionMetaData.WellSample.InsertSize Raw String" in (srs.asString("Insert Size") mustEqual "10000")
    "CollectionMetaData.WellSample.SampleReuseEnabled Raw String" in (srs.asBoolean("Sample Reuse Enabled") mustEqual false)
    "CollectionMetaData.WellSample.StageHotstartEnabled Raw String" in (srs.asBoolean("Stage Hotstart Enabled") mustEqual false)
    "CollectionMetaData.WellSample.SizeSelectionEnabled Raw String" in (srs.asBoolean("Size Selection Enabled") mustEqual false)
    "CollectionMetaData.WellSample.UseCount Raw String" in (srs.asString("Use Count") mustEqual "0")
    "CollectionMetaData.WellSample.DnaControlComplex" in (srs.asString("DNA Control Complex") mustEqual "Sequel™ SMRT®Cell Control Complex 1.0")
    "CollectionMetaData.Automation" in (srs.asString("Automation: Name") mustEqual "Workflow_Magbead.py")
    "CollectionMetaData.Automation.AutomationParameter@MovieLength" in (srs.asString("Automation: Movie Length") mustEqual "120")
//    "CollectionMetaData.Automation.AutomationParameter@ImmobilizationTime Unmodified String" in (srs.immoblizationTimeString mustEqual "120")
//    "CollectionMetaData.Automation.AutomationParameter@ImmobilizationTime" in (srs.immoblizationTime mustEqual 120)
//    "CollectionMetaData.Automation.AutomationParameter@CollectionNumber Unmodified String" in (srs.collectionNumberString mustEqual "0")
//    "CollectionMetaData.Automation.AutomationParameter@CollectionNumber" in (srs.collectionNumber mustEqual 0)
//    "CollectionMetaData.Automation.AutomationParameter@UseStageHotstart Unmodified String" in (srs.useStageHotStartString mustEqual "False")
//    "CollectionMetaData.Automation.AutomationParameter@UseStageHotstart" in (srs.useStageHotStart mustEqual false)
//    "CollectionMetaData.Automation.AutomationParameter@InsertSize Unmodified String" in (srs.aInsertSizeString mustEqual "10000")
//    "CollectionMetaData.Automation.AutomationParameter@InsertSize" in (srs.aInsertSize mustEqual 10000)
//    //
//    "CollectionMetaData.InsertSize Unmodified String" in (srs.insertSizeString mustEqual "10000")
//    "CollectionMetaData.InsertSize" in (srs.insertSize mustEqual 10000)
//    "CollectionMetaData.CollectionNumber Unmodified String" in (srs.collectionNumberString mustEqual "0")
//    "CollectionMetaData.CollectionNumber" in (srs.collectionNumber mustEqual 0)
//    "CollectionMetaData.CellIndex Unmodified String" in (srs.cellIndexString mustEqual "0")
//    "CollectionMetaData.CellIndex" in (srs.cellIndex mustEqual 0)
//    // CollectionMetaData.CellPac
//    "CollectionMetaData.CellPac@PartNumber" in (srs.cpPart mustEqual "100-512-700")
//    "CollectionMetaData.CellPac@LotNumber" in (srs.cpLot mustEqual "320661")
//    "CollectionMetaData.CellPac@Barcode" in (srs.cpBarcode mustEqual "BA035012636102634292807410")
//    "CollectionMetaData.CellPac@Expiration" in (srs.cpExpiration mustEqual "2017-05-02")
//    // CollectionMetaData.TemplatePrepKit
//    "CollectionMetaData.TemplatePrepKit@MinInsertSize Unmodified String" in (srs.minInsertString mustEqual "500")
//    "CollectionMetaData.TemplatePrepKit@MinInsertSize" in (srs.minInsert mustEqual 500)
//    "CollectionMetaData.TemplatePrepKit@MaxInsertSize Unmodified String" in (srs.maxInsertString mustEqual "20000")
//    "CollectionMetaData.TemplatePrepKit@MaxInsertSize" in (srs.maxInsert mustEqual 20000)
//    "CollectionMetaData.TemplatePrepKit@PartNumber" in (srs.tpkPart mustEqual "100-259-100")
//    "CollectionMetaData.TemplatePrepKit@LotNumber" in (srs.tpkLot mustEqual "DM1117")
//    "CollectionMetaData.TemplatePrepKit@Barcode" in (srs.tpkBarcode mustEqual "DM1117100259100111716")
//    "CollectionMetaData.TemplatePrepKit@Expiration" in (srs.tpkExpiration mustEqual "2016-11-17")
//    "CollectionMetaData.TemplatePrepKit.LeftAdaptorSequence" in (srs.leftAdaptor mustEqual "ATCTCTCTCAACAACAACAACGGAGGAGGAGGAAAAGAGAGAGAT")
//    "CollectionMetaData.TemplatePrepKit.RightAdaptorSequence" in (srs.rightAdaptor mustEqual "ATCTCTCTCAACAACAACAACGGAGGAGGAGGAAAAGAGAGAGAT")
//    // CollectionMetaData.SequencingKitPlate
//    "CollectionMetaData.SequencingKitPlate@PartNumber" in (srs.skpPart mustEqual "100-902-100")
//    "CollectionMetaData.SequencingKitPlate@LotNumber" in (srs.skpLot mustEqual "160727")
//    "CollectionMetaData.SequencingKitPlate@Barcode" in (srs.skpBarcode mustEqual "160727100902100012617")
//    "CollectionMetaData.SequencingKitPlate@ExpirationDate" in (srs.skpExpiration mustEqual "2017-01-26")
//    "CollectionMetaData.SequencingKitPlate@Name" in (srs.skpName mustEqual "Sequel™ Sequencing Plate 1.2")
//    // CollectionMetaData.SequencingKitPlate ReagentTubes
//    "CollectionMetaData.SequencingKitPlate.ReagentTubes.PartNumber 100-619-700" in (srs.skpReagentPart("100-619-700") mustEqual "100-619-700")
//    "CollectionMetaData.SequencingKitPlate.ReagentTubes.LotNumber 100-619-700" in (srs.skpReagentLot("100-619-700") mustEqual "107053")
//    "CollectionMetaData.SequencingKitPlate.ReagentTubes.ExpirationDate 100-619-700" in (srs.skpReagentExpiration("100-619-700") mustEqual "2018-07-25")
//    "CollectionMetaData.SequencingKitPlate.ReagentTubes.PartNumber 100-619-600" in (srs.skpReagentPart("100-619-600") mustEqual "100-619-600")
//    "CollectionMetaData.SequencingKitPlate.ReagentTubes.LotNumber 100-619-600" in (srs.skpReagentLot("100-619-600") mustEqual "007666")
//    "CollectionMetaData.SequencingKitPlate.ReagentTubes.ExpirationDate 100-619-600" in (srs.skpReagentExpiration("100-619-600") mustEqual "2020-05-31")
//    // CollectionMetaData.Primary
//    "CollectionMetaData.Primary.AutomationName" in (srs.pAutomationName mustEqual "Default")
//    "CollectionMetaData.Primary.ConfigFileName" in (srs.configFile mustEqual "SqlPoC_SubCrf_2C2A-t2.xml")
//    "CollectionMetaData.Primary.SequencingCondition" in (srs.sequencingCondition mustEqual "DefaultPrimarySequencingCondition")
//    // TODO: Output options -- a bunch of nested stuff here
//    // TODO: Secondary fields. These seem unused
  }
}
