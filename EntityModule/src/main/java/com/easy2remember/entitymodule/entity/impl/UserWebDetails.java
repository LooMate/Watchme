package com.easy2remember.entitymodule.entity.impl;

import com.easy2remember.entitymodule.dto.UserWebDetailsDto;
import com.easy2remember.entitymodule.entity.util.UserDetailsAbstractEntity;

import java.time.LocalDateTime;

public class UserWebDetails extends UserDetailsAbstractEntity {

    private String userBrowser;

    private String browserLanguage;

    private String operationSystem;

    private String location;

    private String screenSizeAndColorDepth;

    private int timeZoneOffset;

    private String webVendorAndRenderGpu;

    private String cpuName;

    private int cpuCoreNum;

    private int deviceMemory;

    private boolean touchSupport;

    private boolean adBlockerUsed;

    private Long userId;

    public UserWebDetails() {
    }

    public UserWebDetails(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String userBrowser,
                          String browserLanguage, String operationSystem, String location,
                          String screenSizeAndColorDepth, int timeZoneOffset, String timeZone,
                          String webVendorAndRenderGpu, String cpuName, int cpuCoreNum, int deviceMemory,
                          boolean touchSupport, boolean adBlockerUsed, Long userId) {
        super(id, createdAt, lastChangedAt,timeZone);
        this.userBrowser = userBrowser;
        this.browserLanguage = browserLanguage;
        this.operationSystem = operationSystem;
        this.location = location;
        this.screenSizeAndColorDepth = screenSizeAndColorDepth;
        this.timeZoneOffset = timeZoneOffset;
        this.webVendorAndRenderGpu = webVendorAndRenderGpu;
        this.cpuName = cpuName;
        this.cpuCoreNum = cpuCoreNum;
        this.deviceMemory = deviceMemory;
        this.touchSupport = touchSupport;
        this.adBlockerUsed = adBlockerUsed;
        this.userId = userId;
    }


    public static UserWebDetailsDto generateDto(UserWebDetails userWebDetails) {
        return new UserWebDetailsDto(userWebDetails.getCreatedAt(),
                userWebDetails.getLastChangedAt(),
                userWebDetails.getUserBrowser(),
                userWebDetails.getBrowserLanguage(),
                userWebDetails.getOperationSystem(),
                userWebDetails.getLocation(),
                userWebDetails.getScreenSizeAndColorDepth(),
                userWebDetails.getTimeZoneOffset(),
                userWebDetails.getTimeZone(),
                userWebDetails.getWebVendorAndRenderGpu(),
                userWebDetails.getCpuName(),
                userWebDetails.getCpuCoreNum(),
                userWebDetails.getDeviceMemory(),
                userWebDetails.isTouchSupport(),
                userWebDetails.isAdBlockerUsed(),
                userWebDetails.getUserId());
    }

    public String getUserBrowser() {
        return userBrowser;
    }

    public void setUserBrowser(String userBrowser) {
        this.userBrowser = userBrowser;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getScreenSizeAndColorDepth() {
        return screenSizeAndColorDepth;
    }

    public void setScreenSizeAndColorDepth(String screenSizeAndColorDepth) {
        this.screenSizeAndColorDepth = screenSizeAndColorDepth;
    }

    public int getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(int timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public String getWebVendorAndRenderGpu() {
        return webVendorAndRenderGpu;
    }

    public void setWebVendorAndRenderGpu(String webVendorAndRenderGpu) {
        this.webVendorAndRenderGpu = webVendorAndRenderGpu;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public int getCpuCoreNum() {
        return cpuCoreNum;
    }

    public void setCpuCoreNum(int cpuCoreNum) {
        this.cpuCoreNum = cpuCoreNum;
    }

    public int getDeviceMemory() {
        return deviceMemory;
    }

    public void setDeviceMemory(int deviceMemory) {
        this.deviceMemory = deviceMemory;
    }

    public boolean isTouchSupport() {
        return touchSupport;
    }

    public void setTouchSupport(boolean touchSupport) {
        this.touchSupport = touchSupport;
    }

    public boolean isAdBlockerUsed() {
        return adBlockerUsed;
    }

    public void setAdBlockerUsed(boolean adBlockerUsed) {
        this.adBlockerUsed = adBlockerUsed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBrowserLanguage() {
        return browserLanguage;
    }

    public void setBrowserLanguage(String browserLanguage) {
        this.browserLanguage = browserLanguage;
    }

    @Override
    public String toString() {
        return "UserWebDetails{" +
                "userBrowser='" + userBrowser + '\'' +
                ", browserLanguage='" + browserLanguage + '\'' +
                ", operationSystem='" + operationSystem + '\'' +
                ", location='" + location + '\'' +
                ", screenSizeAndColorDepth='" + screenSizeAndColorDepth + '\'' +
                ", timeZoneOffset=" + timeZoneOffset +
                ", webVendorAndRenderGpu='" + webVendorAndRenderGpu + '\'' +
                ", cpuName='" + cpuName + '\'' +
                ", cpuCoreNum=" + cpuCoreNum +
                ", deviceMemory=" + deviceMemory +
                ", touchSupport=" + touchSupport +
                ", adBlockerUsed=" + adBlockerUsed +
                ", userId=" + userId +
                '}';
    }
}
