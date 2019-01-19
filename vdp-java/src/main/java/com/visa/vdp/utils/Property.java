package com.visa.vdp.utils;

public enum Property {

    SHARED_SECRET("sharedSecret"),
    KEYSTORE_PATH("keyStorePath"),
    API_KEY("apiKey"),
    END_POINT("visaUrl"),
    USER_ID("userId"),
    PASSWORD("password"),
    KEYSTORE_PASSWORD("keyStorePassword"),
    PRIVATE_KEY_PASSWORD("privateKeyPassword"),
    CALL_ID("visa-direct-call-id"),
    CHECKOUT_CALL_ID("checkoutCallId"),
    MLC_PRIMARY_ACCOUNT_NUMBER("mlcPrimaryAccountNumber"), 
    MLC_DEVICE_ID("mlcDeviceId"), 
    MLC_ISSUER_ID("mlcIssuerId"), 
    MLC_MESSAGE_ID("mlcMessageId"),
    MLC_CLIENT_MESSAGE_ID("mlcClientMessageID"),
    VCTC_TEST_PAN("vctcTestPan"), 
    VTA_SERVICE_ID("vtaServiceId"), 
    VTA_CUSTOMER_ID("vtaCustomerId"), 
    VTA_COMMUNITY_CODE("vtaCommunityCode"),
    VTA_PORTFOLIO_NUMER("vtaPortfolioNumber"),
    TNS_PARTNER_BID("tnsPartnerBid"),
    VTA_NOTIFICATION_ID(""), 
    VTA_CONTACT_TYPE("vtaNotificationContactType"),
    VTA_CONTACT_VALUE("vtaNotificationContactValue"), 
    VTA_CREATE_CUSTOMER_LAST_FOUR("vtaCreateCustomerLastFour"),
    VTA_PREFFERED_LANGUAGE_CODE("vtaPreferredLanguageCode"), 
    VTA_REPLACE_CARDS_ADD2("vtaReplaceCardStreetAddressTwo"), 
    VTA_REPLACE_CARDS_POSTAL_CODE("vtaReplaceCardPostalCode"), 
    VTA_REPLACE_CARDS_ADD1("vtaReplaceCardStreetAddressOne"), 
    VTA_REPLACE_COUNTRY_CODE("vtaReplaceCardCountryCode"), 
    VTA_REPLACE_STATE_PROVINCE("vtaReplaceCardStateProvince"), 
    VTA_REPLACE_CARDS_ADD3("vtaReplaceCardStreetAddressThree"), 
    VTA_REPLACE_NUMERIC_CODE("vtaReplaceCardNumericCode"), 
    VTA_REPLACE_CARDS_CITY("vtaReplaceCardCity"), 
    VTA_REPLACE_CARD_NUM("vtaReplaceCardNumber"), 
    VTA_CREATE_CUSTOMER_ADD1("vtaCreateCustomerStreetAddressOne"),
    VTA_CREATE_CUSTOMER_CITY("vtaCreateCustomerCity"), 
    VTA_CREATE_CUSTOMER_NUMERIC_CODE("vtaCreateCustomerNumericCountryCode"), 
    VTA_CREATE_CUSTOMER_COUNTRY_CODE("vtaCreateCustomerCountryCode"), 
    VTA_CREATE_CUSTOMER_POSTAL_CODE("vtaCreateCustomerPostalCode"), 
    VTA_CREATE_CUSTOMER_STATE_PROVINCE("vtaCreateCustomerStateProvince"), 
    VTA_CREATE_CUSTOMER_ADD3("vtaCreateCustomerStreetAddressThree"), 
    VTA_CREATE_CUSTOMER_ADD2("vtaCreateCustomerStreetAddressTwo"), 
    VTA_REPLACE_SCR_CODE("vtaReplaceCardSecurityCode"), 
    VTA_REPLACE_CARD_LAST4("vtaReplaceCardLastFour"), 
    VTA_CREATE_CUSTOMER_CARD_NUM("vtaCreateCustomerCardNumber"), 
    VTA_CREATE_CARD_SECURITY_CODE("vtaCreateCustomerCardSecurityCode"),
    VTA_REPLACE_EXPIRY_DATE("vtaReplaceCardExpiryDate"), 
    VTA_CREATE_CUSTOMER_EXPR_DATE("vtaCreateCustomerCardExpiryDate"),
    VTA_CREATE_CUSTOMER_LAST4("vtaCreateCustomerLastFour"),
    VTA_PREF_CNTRY_CODE("vtaCreateCustomerPreferedCountryCode"), 
    VTA_PREF_CRNCY_CODE("vtaCreateCustomerPreferedCurrencyCode"), 
    VTA_PREF_LANG("vtaCreateCustomerPreferedLanguage"), 
    VTA_PREF_TIMEZONE("vtaCreateCustomerPreferedTimeZone"), 
    VTA_CREATE_CUSTOMER_ISACTIVE("vtaCreateCustomerIsActive"), 
    VTA_NEW_CARD_ADDRESS("vtaReplaceCardNewAddress"), 
    VTA_CREATE_CARD_ADDRESS("vtaCreateCustomerAddress"), 
    TNS_CARD_ACCOUNT_NUMBERS("tnsCardNumbers"),
    VPP_CLIENT_ENC_PRIVATE_KEY_PATH("vppClientEncPrivateKeyPath"),
    VPP_SERVER_ENC_PUBLIC_CERT_PATH("vppServerEncPublicCertPath"),
    VPP_KEYID("vppKeyId");


    String value;

    private Property(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
