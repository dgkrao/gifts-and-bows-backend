package com.giftsandbows.controller;

import com.giftsandbows.model.SiteSettings;
import com.giftsandbows.repository.SiteSettingsRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/site-settings")
@CrossOrigin
public class SiteSettingsController {

    private final SiteSettingsRepository settingsRepo;

    public SiteSettingsController(SiteSettingsRepository settingsRepo) {
        this.settingsRepo = settingsRepo;
    }

    @GetMapping
    public SiteSettings get() {
        return settingsRepo.findById(1L).orElse(new SiteSettings());
    }

    @PostMapping
    public SiteSettings save(@RequestBody SiteSettings settings) {
        settings.setId(1L);
        return settingsRepo.save(settings);
    }
}
