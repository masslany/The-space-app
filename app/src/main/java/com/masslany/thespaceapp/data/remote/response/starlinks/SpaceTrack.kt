package com.masslany.thespaceapp.data.remote.response.starlinks


import com.google.gson.annotations.SerializedName

data class SpaceTrack(
    @SerializedName("APOAPSIS")
    val aPOAPSIS: Double,
    @SerializedName("ARG_OF_PERICENTER")
    val aRGOFPERICENTER: Double,
    @SerializedName("BSTAR")
    val bSTAR: Double,
    @SerializedName("CCSDS_OMM_VERS")
    val cCSDSOMMVERS: String,
    @SerializedName("CENTER_NAME")
    val cENTERNAME: String,
    @SerializedName("CLASSIFICATION_TYPE")
    val cLASSIFICATIONTYPE: String,
    @SerializedName("COMMENT")
    val cOMMENT: String,
    @SerializedName("COUNTRY_CODE")
    val cOUNTRYCODE: String,
    @SerializedName("CREATION_DATE")
    val cREATIONDATE: String,
    @SerializedName("DECAY_DATE")
    val dECAYDATE: String,
    @SerializedName("DECAYED")
    val dECAYED: Int,
    @SerializedName("ECCENTRICITY")
    val eCCENTRICITY: Double,
    @SerializedName("ELEMENT_SET_NO")
    val eLEMENTSETNO: Int,
    @SerializedName("EPHEMERIS_TYPE")
    val ePHEMERISTYPE: Int,
    @SerializedName("EPOCH")
    val ePOCH: String,
    @SerializedName("FILE")
    val fILE: Int,
    @SerializedName("GP_ID")
    val gPID: Int,
    @SerializedName("INCLINATION")
    val iNCLINATION: Double,
    @SerializedName("LAUNCH_DATE")
    val lAUNCHDATE: String,
    @SerializedName("MEAN_ANOMALY")
    val mEANANOMALY: Double,
    @SerializedName("MEAN_ELEMENT_THEORY")
    val mEANELEMENTTHEORY: String,
    @SerializedName("MEAN_MOTION")
    val mEANMOTION: Double,
    @SerializedName("MEAN_MOTION_DDOT")
    val mEANMOTIONDDOT: Double,
    @SerializedName("MEAN_MOTION_DOT")
    val mEANMOTIONDOT: Double,
    @SerializedName("NORAD_CAT_ID")
    val nORADCATID: Int,
    @SerializedName("OBJECT_ID")
    val oBJECTID: String,
    @SerializedName("OBJECT_NAME")
    val oBJECTNAME: String,
    @SerializedName("OBJECT_TYPE")
    val oBJECTTYPE: String,
    @SerializedName("ORIGINATOR")
    val oRIGINATOR: String,
    @SerializedName("PERIAPSIS")
    val pERIAPSIS: Double,
    @SerializedName("PERIOD")
    val pERIOD: Double,
    @SerializedName("RA_OF_ASC_NODE")
    val rAOFASCNODE: Double,
    @SerializedName("RCS_SIZE")
    val rCSSIZE: String,
    @SerializedName("REF_FRAME")
    val rEFFRAME: String,
    @SerializedName("REV_AT_EPOCH")
    val rEVATEPOCH: Int,
    @SerializedName("SEMIMAJOR_AXIS")
    val sEMIMAJORAXIS: Double,
    @SerializedName("SITE")
    val sITE: String,
    @SerializedName("TIME_SYSTEM")
    val tIMESYSTEM: String,
    @SerializedName("TLE_LINE0")
    val tLELINE0: String,
    @SerializedName("TLE_LINE1")
    val tLELINE1: String,
    @SerializedName("TLE_LINE2")
    val tLELINE2: String
)