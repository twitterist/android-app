package org.twitterist.app.model;

/**
 * Created by marco.wuethrich on 21.09.2015.
 */
public class JObject {

    private String status = null;
    private Double result = 0D;

    public JObject() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
