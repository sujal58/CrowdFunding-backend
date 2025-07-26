package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycApiResponse {
    public String status;
    public int code;
    public String message;
    public double kyc_score;
    public double confidence;
    public String timestamp;
}
