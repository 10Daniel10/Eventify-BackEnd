package com.integrador.evently.providers.service;

import com.integrador.evently.providers.dto.ProviderDTO;
import com.integrador.evently.providers.interfaces.IProviderService;
import com.integrador.evently.providers.model.Provider;
import com.integrador.evently.providers.repository.ProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderService implements IProviderService {

    private final ProviderRepository providerRepository;
    private final ModelMapper modelMapper;

    public ProviderService(ProviderRepository providerRepository, ModelMapper modelMapper) {
        this.providerRepository = providerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProviderDTO> getAll() {
        List<Provider> providers = providerRepository.findAll();
        return providers.stream()
                .map(activity -> modelMapper.map(activity, ProviderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProviderDTO getProviderById(Long id) {
        Provider provider = providerRepository.findById(id).orElse(null);
        return (provider != null) ? modelMapper.map(provider, ProviderDTO.class) : null;
    }

    @Override
    public ProviderDTO saveProvider(ProviderDTO providerDTO) {
        Provider provider = modelMapper.map(providerDTO, Provider.class);
        provider = providerRepository.save(provider);
        return modelMapper.map(provider, ProviderDTO.class);
    }

    @Override
    public ProviderDTO updateProvider(Long id, ProviderDTO providerDTO) {
        Provider provider = providerRepository.findById(id).orElse(null);

        if (provider != null) {
            setProvider(provider, providerDTO);
            Provider updatedProvider = providerRepository.save(provider);
            return modelMapper.map(updatedProvider, ProviderDTO.class);
        }

        return null;
    }

    @Override
    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }

    private void setProvider(Provider provider, ProviderDTO providerDTO) {
        if (providerDTO.getName() != null) provider.setName(providerDTO.getName());
        if (providerDTO.getInformation() != null) provider.setInformation(providerDTO.getInformation());
        if (providerDTO.getAddress() != null) provider.setAddress(providerDTO.getAddress());
    }
}
