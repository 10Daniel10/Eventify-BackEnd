package com.integrador.evently.providers.interfaces;

import com.integrador.evently.providers.dto.ProviderDTO;

import java.util.List;

public interface IProviderService {
    List<ProviderDTO> getAll();
    ProviderDTO getProviderById(Long id);
    ProviderDTO saveProvider(ProviderDTO providerDTO);
    ProviderDTO updateProvider(Long id, ProviderDTO providerDTO);
    void deleteProvider(Long id);
}
