package com.example.smartgarage.controllers.rest;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.AuthenticationHelper;
import com.example.smartgarage.models.dtos.BrandDto;
import com.example.smartgarage.models.entities.Brand;
import com.example.smartgarage.services.contracts.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    private final BrandService brandService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public BrandRestController(BrandService brandService, ModelMapper modelMapper
            , AuthenticationHelper authenticationHelper
    ) {
        this.brandService = brandService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public List<Brand> getAll(@RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return brandService.getAll();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public Brand getById(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable Long id) {
        try {
            authenticationHelper.checkAuthorization(headers);
            return brandService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/new")
    public Brand createBrand(
            @RequestHeader("Authorization") HttpHeaders headers,
            @Valid @RequestBody BrandDto brandDto) {
        Brand brand;
        try {
            authenticationHelper.checkAuthorization(headers);
            brand = modelMapper.map(brandDto, Brand.class);
            brandService.save(brand);
            return brandService.getById(brand.getId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{brandId}/update")
    public Brand updateBrand(@PathVariable Long brandId,
                             @Valid @RequestBody BrandDto brandDto,
                             @RequestHeader("Authorization") HttpHeaders headers) {
        Brand existingBrand;
        try {
            authenticationHelper.checkAuthorization(headers);
            //
            existingBrand = brandService.getById(brandId);
            return brandService.update(existingBrand, brandDto);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable Long brandId,
                            @RequestHeader("Authorization") HttpHeaders headers) {
        try {
            authenticationHelper.checkAuthorization(headers);
            brandService.deleteBrandById(brandId);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/name/{brandName}")
    public Brand getByBrandName(@RequestHeader("Authorization") HttpHeaders headers, @PathVariable String brandName, BrandDto brandDto) {
        try {
            authenticationHelper.checkAuthorization(headers);
            brandDto.setBrandName(brandName);
            return brandService.getByName(brandDto.getBrandName());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}