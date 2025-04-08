package com.billontrax.modules.verification;

import com.billontrax.common.CommonUtil;
import com.billontrax.exceptions.ErrorMessageException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class VerificationService {
    private final VerificationRepository verificationRepository;

    public Verification createVerification(String verificationType, Map<String, Object> parameters) {
        List<Verification> verificationList = verificationRepository.findAllByVerificationType(verificationType);
        List<Verification> updatedList = verificationList.stream().peek(v -> v.setIsDeleted(true)).toList();
        verificationRepository.saveAll(updatedList);
        Verification verification = new Verification();
        verification.setVerificationType(verificationType);
        verification.setParameters(parameters);
        verification.setToken(CommonUtil.generateToken(8));
        return verificationRepository.save(verification);
    }

    public boolean isVerificationValidByToken(String token) {
        Optional<Verification> optionalVerification = verificationRepository.fetchByToken(token);
        if (optionalVerification.isEmpty()) {
            throw new ErrorMessageException("Sorry! please content the System support.");
        }
        Verification verification = optionalVerification.get();
        if (Objects.nonNull(verification.getExpiredOn()) && verification.getExpiredOn().after(new Date())) {
            throw new ErrorMessageException("Sorry! your request expired, please content the system support.");
        }
        return true;
    }

    public void completeVerification(String token) {
        Verification verification = verificationRepository.fetchByToken(token).orElseThrow(() -> new ErrorMessageException("Sorry! please content the System support."));
        verification.setIsCompleted(true);
        verificationRepository.save(verification);
    }

}
