package com.tnv.moneymanager.model;

public class LeftMenuItem {
    public enum MenuIds {
        GROUP_HOME, GROUP_MAILBOX, GROUP_REPORT_COMMON, GROUP_PROFILE_IMPACT, GROUP_REPORT_DEBT_RECOVERY, GROUP_REGISTER_FIELD, GROUP_LOGOUT,

        MENU_BOOK_DIARY, MENU_BOOK_FOLLOW, MENU_BOOK_FOLLOW_EMP
    }

    private int iconResId;
    private int titleRes;
    private MenuIds id;
    private String number;
    private String transactionCount;
    private boolean spanable;

    public LeftMenuItem(MenuIds id, int iconResId, int titleRes) {
        super();
        this.iconResId = iconResId;
        this.titleRes = titleRes;
        this.id = id;
    }

    public LeftMenuItem(MenuIds id, int iconResId, int titleRes, boolean isSpanable) {
        super();
        this.iconResId = iconResId;
        this.titleRes = titleRes;
        this.id = id;
        this.spanable = isSpanable;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public MenuIds getId() {
        return id;
    }

    public void setId(MenuIds id) {
        this.id = id;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public String getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(String transactionCount) {
        this.transactionCount = transactionCount;
    }

    public boolean isSpanable() {
        return spanable;
    }

    public void setSpanable(boolean spanable) {
        this.spanable = spanable;
    }
}
