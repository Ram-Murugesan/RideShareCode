package com.example.ramkumar.rideshare;

import java.util.List;

/**
 * Created by AjayArvind on 7/5/2016.
 */public class RowItemSelect {
    private Long regMobileNoBookNow;
    private String userName;
    private String indexKey;
    private Long mobileNo;

    //Change Password
    public RowItemSelect(Long mobileNo, String indexKey, int i) {
        this.mobileNo=mobileNo;
        this.indexKey=indexKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getRegMobileNoBookNow() {
        return regMobileNoBookNow;
    }

    public void setRegMobileNoBookNow(Long regMobileNoBookNow) {
        this.regMobileNoBookNow = regMobileNoBookNow;
    }

    public RowItemSelect(Long regMobileNoBookNow, String userName) {
        this.regMobileNoBookNow=regMobileNoBookNow;
        this.userName=userName;


    }

    public Long getNewMobileNo() {
        return newMobileNo;
    }

    public void setNewMobileNo(Long newMobileNo) {
        this.newMobileNo = newMobileNo;
    }

    private Long newMobileNo;
    private String name;
    private boolean selected;

    private String regName,regEmail,regPassword,check,regDob;
    private Long regMobileNo;

    private String tName ;
    private String tAddress;
    private String adminStat;
    private Long tMobileNo;
    private String tIndexKey;
    private String imageValue;
    private List checkBoxList;



    private Long dMobileNo;
    private String dAdminStat,driverProfileIndexKey,licenseCategoryStr,otherGovtProofSpinnerStr;
    private String driverNameStr,driverDOBStr,permanentAddressStr,licenseNumberStr,dateOfIssueStr,dateOfExpiryStr,panCardIdStr,panCardNameStr;
    private String addressProofStr,licenseProofStr,profilePictureStr,panCardProofStr,otherGovtProofStr;

    private String uploadAddressStr, uploadLicenseStr, uploadProfilePictureStr, uploadPanCardStr, uploadOtherGovtStr;

    public String getDpImageValue() {
        return dpImageValue;
    }

    public void setDpImageValue(String dpImageValue) {
        this.dpImageValue = dpImageValue;
    }

    //DriverPayment Variables
    private String accountNoStr;
    private String accountNameStr;
    private String ifscCodeStr;

    public String getDpIndex() {
        return dpIndex;
    }

    public void setDpIndex(String dpIndex) {
        this.dpIndex = dpIndex;
    }

    private String dpIndex;
    private Long dpMobileNo;
    private String accountStr, verifyStr, dpAdminStatus;
    private String dpImageValue;

//ForgetMobileNoVerify

    public String getIndexKey() {
        return indexKey;
    }

    public void setIndexKey(String indexKey) {
        this.indexKey = indexKey;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public RowItemSelect(Long newMobileNo) {
        this.newMobileNo=newMobileNo;
    }

    public String getAccountNoStr() {
        return accountNoStr;
    }

    public void setAccountNoStr(String accountNoStr) {
        this.accountNoStr = accountNoStr;
    }

    public String getAccountNameStr() {
        return accountNameStr;
    }

    public void setAccountNameStr(String accountNameStr) {
        this.accountNameStr = accountNameStr;
    }

    public String getIfscCodeStr() {
        return ifscCodeStr;
    }

    public void setIfscCodeStr(String ifscCodeStr) {
        this.ifscCodeStr = ifscCodeStr;
    }

    public Long getDpMobileNo() {
        return dpMobileNo;
    }

    public void setDpMobileNo(Long dpMobileNo) {
        this.dpMobileNo = dpMobileNo;
    }

    public String getAccountStr() {
        return accountStr;
    }

    public void setAccountStr(String accountStr) {
        this.accountStr = accountStr;
    }

    public String getVerifyStr() {
        return verifyStr;
    }

    public void setVerifyStr(String verifyStr) {
        this.verifyStr = verifyStr;
    }

    public String getDpAdminStatus() {
        return dpAdminStatus;
    }

    public void setDpAdminStatus(String dpAdminStatus) {
        this.dpAdminStatus = dpAdminStatus;
    }

    public RowItemSelect(String accountNoStr, Long dpMobileNo, String accountNameStr, String ifscCodeStr, String accountStr, String verifyStr, String dpAdminStatus, String dpImageValue, String dpIndex) {

        this.accountNoStr = accountNoStr;
        this.dpMobileNo = dpMobileNo;
        this.accountNameStr = accountNameStr;
        this.ifscCodeStr = ifscCodeStr;
        this.accountStr = accountStr;
        this.verifyStr = verifyStr;
        this.dpAdminStatus = dpAdminStatus;
        this.dpImageValue =dpImageValue;
        this.dpIndex = dpIndex;

    }

    public String getUploadLicenseStr() {
        return uploadLicenseStr;
    }

    public void setUploadLicenseStr(String uploadLicenseStr) {
        this.uploadLicenseStr = uploadLicenseStr;
    }

    public String getUploadProfilePictureStr() {
        return uploadProfilePictureStr;
    }

    public void setUploadProfilePictureStr(String uploadProfilePictureStr) {
        this.uploadProfilePictureStr = uploadProfilePictureStr;
    }

    public String getUploadPanCardStr() {
        return uploadPanCardStr;
    }

    public void setUploadPanCardStr(String uploadPanCardStr) {
        this.uploadPanCardStr = uploadPanCardStr;
    }

    public String getUploadOtherGovtStr() {
        return uploadOtherGovtStr;
    }

    public void setUploadOtherGovtStr(String uploadOtherGovtStr) {
        this.uploadOtherGovtStr = uploadOtherGovtStr;
    }



    public String getUploadAddressStr() {
        return uploadAddressStr;
    }

    public void setUploadAddressStr(String uploadAddressStr) {
        this.uploadAddressStr = uploadAddressStr;
    }

    public String getDriverNameStr() {
        return driverNameStr;
    }

    public void setDriverNameStr(String driverNameStr) {
        this.driverNameStr = driverNameStr;
    }

    public String getDriverDOBStr() {
        return driverDOBStr;
    }

    public void setDriverDOBStr(String driverDOBStr) {
        this.driverDOBStr = driverDOBStr;
    }

    public String getPermanentAddressStr() {
        return permanentAddressStr;
    }

    public void setPermanentAddressStr(String permanentAddressStr) {
        this.permanentAddressStr = permanentAddressStr;
    }

    public String getLicenseNumberStr() {
        return licenseNumberStr;
    }

    public void setLicenseNumberStr(String licenseNumberStr) {
        this.licenseNumberStr = licenseNumberStr;
    }

    public String getDateOfIssueStr() {
        return dateOfIssueStr;
    }

    public void setDateOfIssueStr(String dateOfIssueStr) {
        this.dateOfIssueStr = dateOfIssueStr;
    }

    public String getDateOfExpiryStr() {
        return dateOfExpiryStr;
    }

    public void setDateOfExpiryStr(String dateOfExpiryStr) {
        this.dateOfExpiryStr = dateOfExpiryStr;
    }

    public String getPanCardIdStr() {
        return panCardIdStr;
    }

    public void setPanCardIdStr(String panCardIdStr) {
        this.panCardIdStr = panCardIdStr;
    }

    public String getPanCardNameStr() {
        return panCardNameStr;
    }

    public void setPanCardNameStr(String panCardNameStr) {
        this.panCardNameStr = panCardNameStr;
    }

    public String getAddressProofStr() {
        return addressProofStr;
    }

    public void setAddressProofStr(String addressProofStr) {
        this.addressProofStr = addressProofStr;
    }

    public String getLicenseProofStr() {
        return licenseProofStr;
    }

    public void setLicenseProofStr(String licenseProofStr) {
        this.licenseProofStr = licenseProofStr;
    }

    public String getProfilePictureStr() {
        return profilePictureStr;
    }

    public void setProfilePictureStr(String profilePictureStr) {
        this.profilePictureStr = profilePictureStr;
    }

    public String getPanCardProofStr() {
        return panCardProofStr;
    }

    public void setPanCardProofStr(String panCardProofStr) {
        this.panCardProofStr = panCardProofStr;
    }

    public String getOtherGovtProofStr() {
        return otherGovtProofStr;
    }

    public void setOtherGovtProofStr(String otherGovtProofStr) {
        this.otherGovtProofStr = otherGovtProofStr;
    }


    public Long getdMobileNo() {
        return dMobileNo;
    }

    public void setdMobileNo(Long dMobileNo) {
        this.dMobileNo = dMobileNo;
    }

    public String getdAdminStat() {
        return dAdminStat;
    }

    public void setdAdminStat(String dAdminStat) {
        this.dAdminStat = dAdminStat;
    }

    public String getDriverProfileIndexKey() {
        return driverProfileIndexKey;
    }

    public void setDriverProfileIndexKey(String driverProfileIndexKey) {
        this.driverProfileIndexKey = driverProfileIndexKey;
    }

    public String getLicenseCategoryStr() {
        return licenseCategoryStr;
    }

    public void setLicenseCategoryStr(String licenseCategoryStr) {
        this.licenseCategoryStr = licenseCategoryStr;
    }

    public String getOtherGovtProofSpinnerStr() {
        return otherGovtProofSpinnerStr;
    }

    public void setOtherGovtProofSpinnerStr(String otherGovtProofSpinnerStr) {
        this.otherGovtProofSpinnerStr = otherGovtProofSpinnerStr;
    }

    public RowItemSelect(String driverProfileIndexKey, String dAdminStat, Long dMobileNo, String driverNameStr, String driverDOBStr, String permanentAddressStr, String licenseNumberStr, String dateOfIssueStr, String dateOfExpiryStr, String panCardIdStr, String panCardNameStr, String addressProofStr, String licenseProofStr, String profilePictureStr, String panCardProofStr, String otherGovtProofStr, String uploadAddressStr, String uploadLicenseStr, String uploadProfilePictureStr, String uploadPanCardStr, String uploadOtherGovtStr, String licenseCategoryStr, String otherGovtProofSpinnerStr) {
        this.dAdminStat = dAdminStat;
        this.dMobileNo = dMobileNo;
        this.driverProfileIndexKey=driverProfileIndexKey;


        this.driverNameStr=driverNameStr;
        this.driverDOBStr=driverDOBStr;
        this.permanentAddressStr=permanentAddressStr;
        this.licenseNumberStr=licenseNumberStr;
        this.dateOfIssueStr=dateOfIssueStr;

        this.dateOfExpiryStr=dateOfExpiryStr;
        this.panCardIdStr=panCardIdStr;
        this.panCardNameStr=panCardNameStr;
        this.addressProofStr=addressProofStr;
        this.licenseProofStr=licenseProofStr;
        this.profilePictureStr=profilePictureStr;
        this.panCardProofStr=panCardProofStr;
        this.otherGovtProofStr=otherGovtProofStr;

        this.uploadAddressStr = uploadAddressStr;
        this.uploadLicenseStr = uploadLicenseStr;
        this.uploadProfilePictureStr = uploadProfilePictureStr;
        this.uploadPanCardStr = uploadPanCardStr;
        this.uploadOtherGovtStr = uploadOtherGovtStr;

        this.licenseCategoryStr=licenseCategoryStr;
        this.otherGovtProofSpinnerStr=otherGovtProofSpinnerStr;


    }

    public Boolean getShortTrip() {
        return shortTrip;
    }

    public void setShortTrip(Boolean shortTrip) {
        this.shortTrip = shortTrip;
    }

    public Boolean getLongTrip() {
        return longTrip;
    }

    public void setLongTrip(Boolean longTrip) {
        this.longTrip = longTrip;
    }

    public Boolean getOutStation() {
        return outStation;
    }

    public void setOutStation(Boolean outStation) {
        this.outStation = outStation;
    }

    public Boolean getDays() {
        return days;
    }

    public void setDays(Boolean days) {
        this.days = days;
    }

    public Boolean getWeeks() {
        return weeks;
    }

    public void setWeeks(Boolean weeks) {
        this.weeks = weeks;
    }

    public Boolean getMonths() {
        return months;
    }

    public void setMonths(Boolean months) {
        this.months = months;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getdIndexKey() {
        return dIndexKey;
    }

    public void setdIndexKey(String dIndexKey) {
        this.dIndexKey = dIndexKey;
    }

    private Boolean shortTrip,longTrip,outStation,days,weeks,months;
    private String adminStatus,dIndexKey;
    public RowItemSelect(Boolean shortTrip, Boolean longTrip, Boolean outStation, Boolean days, Boolean weeks, Boolean months, Long regMobileNo, String adminStatus, String dIndexKey) {
            this.shortTrip =shortTrip;
             this.longTrip=longTrip;
        this.outStation=outStation;
        this.days=days;
        this.weeks=weeks;
        this.months=months;
        this.adminStatus=adminStatus;
        this.regMobileNo=regMobileNo;
        this.dIndexKey=dIndexKey;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettAddress() {
        return tAddress;
    }

    public void settAddress(String tAddress) {
        this.tAddress = tAddress;
    }

    public String getAdminStat() {
        return adminStat;
    }

    public void setAdminStat(String adminStat) {
        this.adminStat = adminStat;
    }

    public Long gettMobileNo() {
        return tMobileNo;
    }

    public void settMobileNo(Long tMobileNo) {
        this.tMobileNo = tMobileNo;
    }

    public String gettIndexKey() {
        return tIndexKey;
    }

    public void settIndexKey(String tIndexKey) {
        this.tIndexKey = tIndexKey;
    }

    public String getImageValue() {
        return imageValue;
    }

    public void setImageValue(String imageValue) {
        this.imageValue = imageValue;
    }

    public List getCheckBoxList() {
        return checkBoxList;
    }

    public void setCheckBoxList(List checkBoxList) {
        this.checkBoxList = checkBoxList;
    }

    public RowItemSelect(String tName , String tAddress, String adminStat, Long tMobileNo, String tIndexKey, String imageValue, List checkBoxList)
    {
        this.tName=tName;
        this.tAddress=tAddress;
        this.adminStat=adminStat;
        this.tMobileNo=tMobileNo;
        this.tIndexKey=tIndexKey;
        this.imageValue=imageValue;
        this.checkBoxList=checkBoxList;


    }
    public RowItemSelect(String name, boolean selected) {
            super();

            this.name = name;
            this.selected = selected;
        }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public Long getRegMobileNo() {
        return regMobileNo;
    }

    public void setRegMobileNo(Long regMobileNo) {
        this.regMobileNo = regMobileNo;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(String regPassword) {
        this.regPassword = regPassword;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String regCorrectPassword) {
        this.check = regCorrectPassword;
    }

    public String getRegDob() {
        return regDob;
    }

    public void setRegDob(String regDob) {
        this.regDob = regDob;
    }

    public RowItemSelect(String regName, Long regMobileNo, String regEmail, String regPassword, String regDob, String check){

        this.regName = regName;
        this.regMobileNo = regMobileNo;
        this.regEmail = regEmail;
        this.regPassword = regPassword;
        this.regDob = regDob;
        this.check = check;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    //Login Details
    private Long loginMobileNo;
    private String password,loginEmail;

    public Long getLoginMobileNo() {
        return loginMobileNo;
    }

    public void setLoginMobileNo(Long loginMobileNo) {
        this.loginMobileNo = loginMobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public RowItemSelect(Long loginMobileNo, String loginEmail, String password)
    {

        this.loginMobileNo = loginMobileNo;
        this.loginEmail=loginEmail;

        this.password=password;
    }

    public String getCheckBoxName() {
        return checkBoxName;
    }

    public void setCheckBoxName(String checkBoxName) {
        this.checkBoxName = checkBoxName;
    }

    private String checkBoxName;
    public RowItemSelect(String checkBoxName)
    {

        this.checkBoxName = checkBoxName;

    }


}