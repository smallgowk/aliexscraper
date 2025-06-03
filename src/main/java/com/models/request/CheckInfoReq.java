package com.models.request;

public class CheckInfoReq {
    private String version;
    private String diskSerialNumber;
    private String module;

    public CheckInfoReq(String version, String diskSerialNumber, String module) {
        this.version = version;
        this.diskSerialNumber = diskSerialNumber;
        this.module = module;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDiskSerialNumber() {
        return diskSerialNumber;
    }

    public void setDiskSerialNumber(String diskSerialNumber) {
        this.diskSerialNumber = diskSerialNumber;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
