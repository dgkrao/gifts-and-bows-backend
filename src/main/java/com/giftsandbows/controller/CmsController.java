package com.giftsandbows.controller;

import com.giftsandbows.model.CmsBlock;
import com.giftsandbows.repository.CmsBlockRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cms")
@CrossOrigin
public class CmsController {

    private final CmsBlockRepository cmsRepo;

    public CmsController(CmsBlockRepository cmsRepo) {
        this.cmsRepo = cmsRepo;
    }

    @GetMapping("/{page}")
    public List<CmsBlock> byPage(@PathVariable String page) {
        return cmsRepo.findByPage(page);
    }

    @PostMapping
    public CmsBlock save(@RequestBody CmsBlock block) {
        return cmsRepo.save(block);
    }
}
