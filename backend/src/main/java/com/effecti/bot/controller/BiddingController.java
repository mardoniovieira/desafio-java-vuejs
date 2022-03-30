package com.effecti.bot.controller;

import com.effecti.bot.dto.BiddingDto;
import com.effecti.bot.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bidding")
public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    @GetMapping("/{page}")
    public ResponseEntity<List<BiddingDto>> retrieveBiddingsByPage(@PathVariable Integer page) {
        return ResponseEntity.ok(biddingService.getBiddingsByPage(page));
    }

    @PutMapping("/")
    public ResponseEntity<Boolean> checkBidding(@RequestBody BiddingDto biddingDto) {
        biddingService.checkBidding(biddingDto);
        return ResponseEntity.ok(true);
    }
}
