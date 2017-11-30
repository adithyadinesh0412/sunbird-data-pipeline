package org.ekstep.ep.samza.domain;

import com.google.gson.annotations.SerializedName;
import org.ekstep.ep.samza.reader.NullableValue;
import org.ekstep.ep.samza.reader.Telemetry;

import java.util.HashMap;
import java.util.Map;

public class TObject {
    private String id;
    private String type;
    private String ver;
    private Rollup rollUp;

    @SerializedName("subtype")
    private String subType;

    private final HashMap<String, String> parent = new HashMap<>();

    transient private String defaultType = "Content";
    
    public TObject(Telemetry reader){
    	this.id = computeId(reader);
    	String type = reader.<String>read("edata.eks.type").valueOrDefault(defaultType);
    	if("genieservices.android".equals(this.id) || "org.ekstep.genieservices".equals(this.id)){
    		this.type = "";
    	} else {
    		this.type = type;
    	}
    	this.ver = reader.<String>read("gdata.ver").valueOrDefault("");
    	this.subType = reader.<String>read("edata.eks.subtype").valueOrDefault("");

        String parentId = reader.<String>read("edata.eks.parentid").valueOrDefault("");
        String parentType = reader.<String>read("edata.eks.parenttype").valueOrDefault("");
        this.parent.put("id", parentId);
        this.parent.put("type", parentType);
    }

    private String computeId(Telemetry reader) {
        NullableValue<String> gdataId = reader.<String>read("gdata.id");
        if (!gdataId.isNull()) {
            return gdataId.value();
        }

        return reader.<String>read("edata.eks.id").valueOrDefault("");
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getParent() { return parent; }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Rollup getRollUp() {
        return rollUp;
    }

    public void setRollUp(Rollup rollUp) {
        this.rollUp = rollUp;
    }

    public String getSubType() {
        return subType;
    }
}
