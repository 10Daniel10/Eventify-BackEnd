package com.integrador.evently.providers.controller;

import com.integrador.evently.providers.dto.ProviderDTO;
import com.integrador.evently.providers.interfaces.IProviderController;
import com.integrador.evently.providers.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
public class ProviderController implements IProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public List<ProviderDTO> getAllProviders() {
        return providerService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getProviderById(@PathVariable Long id ) {
        ProviderDTO providerDTO = providerService.getProviderById(id);
        return (providerDTO != null)
                ? new ResponseEntity<>(providerDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> saveProvider(@RequestBody ProviderDTO providerDTO) {
        ProviderDTO savedProvider= providerService.saveProvider(providerDTO);
        return new ResponseEntity<>(savedProvider, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProvider(@PathVariable Long id, @RequestBody ProviderDTO providerDTO) {
        ProviderDTO updatedProvider= providerService.updateProvider(id, providerDTO);

        if (updatedProvider != null) {
            return ResponseEntity.ok(updatedProvider);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteProvider(Long id) {
        providerService.deleteProvider(id);
        //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
