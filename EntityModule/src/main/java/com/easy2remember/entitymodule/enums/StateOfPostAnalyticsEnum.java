package com.easy2remember.entitymodule.enums;

public enum StateOfPostAnalyticsEnum {
    ENABLE(true), DISABLE(false);


    private boolean stateOfAnalytics;

    StateOfPostAnalyticsEnum(boolean state) {
        this.stateOfAnalytics = state;
    }

    public boolean getStateOfAnalytics() {
        return stateOfAnalytics;
    }
}
