package com.project.crowdfunding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycResponseDto {

    private Long id;
    private Long user;
    private String name;
    private String address;
    private String contactNo;
    private String documentNumber;
    private String documentUrl;
    private String selfieUrl;
    private String status;
    private OffsetDateTime createdAt;
}
