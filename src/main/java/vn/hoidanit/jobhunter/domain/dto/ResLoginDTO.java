package vn.hoidanit.jobhunter.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResLoginDTO {

    @JsonProperty("token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
