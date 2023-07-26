package com.avocado.liveAuction.service;

import com.avocado.liveAuction.domain.entity.TempBroadCast;
import com.avocado.liveAuction.domain.entity.TempLiveAuction;
import com.avocado.liveAuction.domain.repository.TempLiveAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TempLiveAuctionService {
    private final TempLiveAuctionRepository tempLiveAuctionRepository;

    public List<TempLiveAuction> findAllByBroadcast(Long id) {
        return tempLiveAuctionRepository.findAllByBID(id);
    }

}
