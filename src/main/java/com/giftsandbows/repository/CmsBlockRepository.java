package com.giftsandbows.repository;

import com.giftsandbows.model.CmsBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CmsBlockRepository extends JpaRepository<CmsBlock, Long> {

    List<CmsBlock> findByPage(String page);
}
