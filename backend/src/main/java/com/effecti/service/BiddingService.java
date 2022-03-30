package com.effecti.service;

import com.effecti.dto.BiddingDto;
import com.effecti.model.Bidding;
import com.effecti.repository.BiddingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BiddingService {

    @Autowired
    private BiddingRequestService biddingRequest;

    @Autowired
    private BiddingRepository biddingRepository;

    public List<BiddingDto> getBiddingsByPage(Integer page) {
        List<BiddingDto> biddings = new ArrayList<>();
        List<Map<String, String>> biddingsMap = new ArrayList<>();

        try {
            biddingsMap = biddingRequest.getBiddingsMapByPage(page);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map<String, String> map : biddingsMap) {
            Bidding bidding = new Bidding(map);
            // Check if the bidding always visualized
            Boolean visualized = biddingRepository.findOneByHash(bidding.getHash()) != null;
            biddings.add(new BiddingDto(bidding, visualized));
        }
        return biddings;
    }

    public void checkBidding(BiddingDto biddingDto) {
        Bidding bidding = biddingDto.toBidding();
        if (biddingDto.getVisualized()) {
            this.saveBidding(bidding);
        } else {
            this.deleteBidding(bidding);
        }
    }

    private void saveBidding(Bidding bidding) {
        biddingRepository.save(bidding);
    }

    private void deleteBidding(Bidding bidding) {
        biddingRepository.deleteBiddingByHash(bidding.getHash());
    }
}
