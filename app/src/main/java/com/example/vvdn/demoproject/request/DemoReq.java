package com.example.vvdn.demoproject.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DemoReq implements Serializable {

    @Expose
    @SerializedName("action")
    public String action = "getgetway";

    @Expose
    @SerializedName("mewardid")
    public String mewardid = "3RDIMZG9E1521286070";

    @Expose
    @SerializedName("tokenkey")
    public String tokenkey = "VML5JPOZ41521286070_b7a7c97a-24c2-4ce1-9399-354d95d4f069_HBPDA7P4S_HBSOPT4FP";

}
