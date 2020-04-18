package com.gaia.dataSource.WQPackage.vo;

public class WQResponse {
    private int return_code;
    private String return_msg;
    private String response_data;
    private String msg_id;
    private Boolean empty;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;


    public int getReturn_code() {
        return this.return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return this.return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResponse_data() {
        return this.response_data;
    }

    public void setResponse_data(String response_data) {
        this.response_data = response_data;
    }

    public String getMsg_id() {
        return this.msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public Boolean getEmpty() {
        //        System.out.println(this.response_data);
//        System.out.println("老大黑貂皮1");
        if(this.response_data.equals("[]")) {
//            System.out.println("老大黑貂皮2");
            this.empty = true;
        }
        else {
            this.empty = false;
        }
        return this.empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }
}
