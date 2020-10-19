package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int addCredential(Credential credential){
        return credentialMapper.addCredential(new Credential(null, credential.getCredentialUrl(), credential.getCredentialUsername(), credential.getCredentialKey(), credential.getCredentialPassword(), credential.getCredentialId()));
    }

    public List<Credential> getAllCredentialsForUser(int userId){
        return credentialMapper.getAllCredentialsForUser(userId);
    }

    public void updateCredential(Credential credential){
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId){
        credentialMapper.deleteCredential(credentialId);
    }

}
