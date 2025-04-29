package com.ey.kyc_service.service;

import com.ey.kyc_service.model.KycReviewRequest;
import com.ey.kyc_service.model.KycStatusResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class KycService {

    private static final String UPLOAD_DIR = "uploads";

    public void uploadDocuments(MultipartFile pan, MultipartFile aadhaar) {
        try {
            // Ensure the upload directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save PAN file
            Path panPath = Path.of(UPLOAD_DIR, pan.getOriginalFilename());
            Files.copy(pan.getInputStream(), panPath, StandardCopyOption.REPLACE_EXISTING);

            // Save Aadhaar file
            Path aadhaarPath = Path.of(UPLOAD_DIR, aadhaar.getOriginalFilename());
            Files.copy(aadhaar.getInputStream(), aadhaarPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("PAN and Aadhaar documents uploaded successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload documents", e);
        }
    }


    public KycStatusResponse reviewKyc(KycReviewRequest request) {
        // Check if PAN and Aadhaar statuses are valid


        // Check if age is valid (e.g., must be 18 or older)
        if (request.getAge() < 18) {
            return new KycStatusResponse("FAIL", "Applicant must be at least 18 years old.");
        }

        // If all criteria are met, return success
        return new KycStatusResponse("SUCCESS", null);
    }
}
