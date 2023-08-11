package com.avocado.Item.service;

import com.avocado.Item.controller.dto.*;
import com.avocado.Item.domain.entity.Item;
import com.avocado.Item.domain.repository.ItemRepository;
import com.avocado.Item.domain.repository.MySaleResponseMapping;
import com.avocado.live.board.domain.entity.LiveHistory;
import com.avocado.live.board.domain.repository.LiveHistoryRepository;
import com.avocado.live.broadcast.domain.Broadcast;
import com.avocado.live.broadcast.service.BroadcastService;
import com.avocado.member.domain.entity.Member;
import com.avocado.normal.auction.domain.repository.NormalHistoryRepository;
import com.avocado.normal.board.controller.dto.NormalItemDetailResponseDto;
import com.avocado.normal.board.controller.dto.NormalItemInfoDto;
import com.avocado.normal.board.service.NormalBoardService;
import com.avocado.normal.entity.NormalHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final NormalBoardService normalBoardService;
    //private final LiveAuctionService liveAuctionService; // 추후 변경
    private final BroadcastService broadcastService;
    private final NormalHistoryRepository normalHistoryRepository;
    private final LiveHistoryRepository liveHistoryRepository;

    // 위탁 요청 물품 등록
    @Transactional
    public boolean saveItem(ConsignRequestDto consignRequestDto, Member member) {
        try {
            itemRepository.save(consignRequestDto.ConsignRequestDtoToEntity(member));
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    // 나의 위탁 물품 리스트 가져오기
    public MySaleResponseDto getMySales(Long memberId) {
        List<MySaleResponseEntries> entries = itemRepository.findItemsByMemberId(memberId);

        return new MySaleResponseDto(entries);
    }

    // 나의 위탁 상시경매 물품 상세보기
    public MySaleDetailResponseDto getMyNormalSale(Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        NormalItemInfoDto normalItemDetailResponseDto = normalBoardService.getItemDetail(itemId);
        NormalHistory forTopBid = normalHistoryRepository.findFirstByNormalAuction_IdOrderByBidPriceDescCreatedAtAsc(normalItemDetailResponseDto.getAuctionId()).orElse(null);
        Integer currentBid = 0; // 프론트에서 처리
        if(forTopBid != null) {
            currentBid = forTopBid.getBidPrice();
        }
        return new MySaleDetailResponseDto(item, normalItemDetailResponseDto, currentBid);
    }

    // 나의 위탁 라이브경매 물품 상세보기(미완)
//    public MySaleDetailResponseDto getMyLiveSale(Long itemId) {
//        Item item = itemRepository.findById(itemId).orElse(null);
//
//        // 상품id를 통해 라이브경매 데이터를 가져오는 서비스가 만들어져야 함
//        LiveAuction liveAuction = LiveAuctionService.getLiveAuction(itemId);
//        Long broadcastId = liveAuction.getBroadcastId();
//
//        // 방송id를 통해 방송 데이터를 가져오는 서비스가 만들어져야 함
//        Broadcast broadcast = BroadcastService.getBroadcast(broadcastId);
//
//        return new MySaleDetailResponseDto(item, liveAuction, broadcast);
//    }

    // 나의 상시경매 입찰 물품 리스트
    public MyBidResponseDto getMyNormalBids(Long memberId) {
        List<MyBidResponseEntries> entries = itemRepository.findMyNormalBidsByMemberId(memberId);
        for(MyBidResponseEntries entry : entries) {
            NormalHistory forTopBid = normalHistoryRepository.findFirstByNormalAuction_IdOrderByBidPriceDescCreatedAtAsc(entry.getAuctionId()).orElse(null);
            entry.setCurrentBid(forTopBid.getBidPrice());
        }
        MyBidResponseDto myBidResponseDto = new MyBidResponseDto(entries);

        return myBidResponseDto;
    }

    //내 라이브 경매 입찰 물품 리스트

    public MyBidResponseDto getMyLiveBids(Long memberId) {
        List<MyBidResponseEntries> entries = itemRepository.findMyLiveBidsByMemberId(memberId);
        for(MyBidResponseEntries entry : entries) {
            LiveHistory forTopBid = liveHistoryRepository.findFirstByLiveAuction_IdOrderByBidPriceDescCreatedAtAsc(entry.getAuctionId()).orElse(null);
            entry.setCurrentBid(forTopBid.getBidPrice());
        }
        MyBidResponseDto myBidResponseDto = new MyBidResponseDto(entries);

        return myBidResponseDto;
    }

    // 나의 낙찰 물품 리스트
    public MySuccessBidResponseDto getMySuccessBids(Long memberId) {
        List<MySuccessBidEntries> entries = itemRepository.findMySuccessBidByMemberId(memberId);

        return new MySuccessBidResponseDto(entries);
    }


    //나의 라이브 낙찰 물품 리스트
    public MySuccessBidResponseDto getMyLiveSuccessBids(Long memberId) {
        List<MySuccessBidEntries> entries = itemRepository.findMySuccessLiveBidByMemberId(memberId);

        return new MySuccessBidResponseDto(entries);
    }

}
