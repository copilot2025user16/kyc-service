package com.ey.kyc_service.controller;

import com.ey.kyc_service.model.KycReviewRequest;
import com.ey.kyc_service.model.KycStatusResponse;
import com.ey.kyc_service.service.KycService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocuments(@RequestParam("pan") MultipartFile pan,
                                                  @RequestParam("aadhaar") MultipartFile aadhaar) {
        kycService.uploadDocuments(pan, aadhaar);
        return ResponseEntity.ok("Documents uploaded successfully.");
    }

    @PostMapping("/review")
    public ResponseEntity<KycStatusResponse> reviewKyc(@RequestBody KycReviewRequest request) {
        KycStatusResponse response = kycService.reviewKyc(request);
        return ResponseEntity.ok(response);
    }
}
