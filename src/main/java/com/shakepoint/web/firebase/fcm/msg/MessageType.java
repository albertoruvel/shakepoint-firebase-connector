package com.shakepoint.web.firebase.fcm.msg;

public enum MessageType {
    TRAINER_PROMO_USED(""),
    FREE_PROMO_CODE(""),
    FREE_PROMO_AFTER_10_PURCHASES(""),
    BIRTH_DAY("");
    String value;

    MessageType(String value) {
        this.value = value;
    }
}
