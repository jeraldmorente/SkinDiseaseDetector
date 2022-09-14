package cict.thesis.iskinclinic;

import com.google.gson.annotations.SerializedName;


public class SkinAppDiseasesList {

    @SerializedName("skinID") private int skinID;
    @SerializedName("skinDiseaseName") private String skinDiseaseName;
    @SerializedName("skinDiseaseImage") private String skinDiseaseImage;
    @SerializedName("skinDiseaseDesc") private String skinDiseaseDesc;
    @SerializedName("skinDiseaseCauses") private String skinDiseaseCauses;
    @SerializedName("skinDiseaseSymptoms") private String skinDiseaseSymptoms;
    @SerializedName("skinDiseasePrevention") private String skinDiseasePrevention;
    @SerializedName("skinDiseaseTreatment") private String skinDiseaseTreatment;

    public int getSkinID() {
        return skinID;
    }

    public String getSkinDiseaseName() {
        return skinDiseaseName;
    }

    public String getSkinDiseaseImage() {
        String sdImage = "https://iskinclinic.000webhostapp.com/pages/skindiseases/image/"+skinDiseaseImage;
 
        return sdImage;
    }

    public String getSkinDiseaseDesc() {
        return skinDiseaseDesc;
    }
    public String getSkinDiseaseCauses() {
        return skinDiseaseCauses;
    }

    public String getSkinDiseasePrevention() {
        return skinDiseasePrevention;
    }

    public String getSkinDiseaseSymptoms() {
        return skinDiseaseSymptoms;
    }

    public String getSkinDiseaseTreatment() {
        return skinDiseaseTreatment;
    }


}
