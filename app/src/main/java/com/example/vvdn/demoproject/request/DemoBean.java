package com.example.vvdn.demoproject.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DemoBean {
    @Expose
    @SerializedName("storeid")
    public String storeid="9";

    @Expose
    @SerializedName("quantity")
    public String quantity="1";

    @Expose
    @SerializedName("custom")
    public String custom="";

    @Expose
    @SerializedName("cubeId")
    public String cubeId="YLY9RNJMY";

}
