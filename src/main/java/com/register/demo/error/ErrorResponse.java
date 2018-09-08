package com.register.demo.error;

public class ErrorResponse {
    private String messageCode;
    private String messageDesc;

    public ErrorResponse(String messageCode, String messageDesc) {
        this.messageCode = messageCode;
        this.messageDesc = messageDesc;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }
}
