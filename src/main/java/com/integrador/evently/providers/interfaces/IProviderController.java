package com.integrador.evently.providers.interfaces;

import com.integrador.evently.providers.dto.ProviderDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface IProviderController {

    List<ProviderDTO> getAllProviders();
    ResponseEntity<ProviderDTO> getProviderById(Long id );
    ResponseEntity<ProviderDTO> saveProvider(ProviderDTO providerDTO);
    ResponseEntity<ProviderDTO> updateProvider(Long id, ProviderDTO providerDTO);
    void deleteProvider(Long id);
}
