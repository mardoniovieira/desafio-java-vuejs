package com.effecti.bot.dto;

import com.effecti.bot.model.Bidding;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiddingDto {

    private String publicationNumber;
    private String status;
    private String viprocNumber;
    private String contractObject;
    private String noticeNumberAndContractorAndDelivery;
    private String systematicsAndAcquisitionForm;
    private String arrivalAndOpening;
    private Boolean visualized;

    public BiddingDto(Bidding bidding, Boolean visualized) {
        setPublicationNumber(bidding.getPublicationNumber());
        setStatus(bidding.getStatus());
        setViprocNumber(bidding.getViprocNumber());
        setContractObject(bidding.getContractObject());
        setNoticeNumberAndContractorAndDelivery(bidding.getNoticeNumberAndContractorAndDelivery());
        setSystematicsAndAcquisitionForm(bidding.getSystematicsAndAcquisitionForm());
        setArrivalAndOpening(bidding.getReceptionAndOpening());
        setVisualized(visualized);
    }

    public Bidding toBidding() {
        return new Bidding(
                getPublicationNumber(),
                getStatus(),
                getViprocNumber(),
                getContractObject(),
                getNoticeNumberAndContractorAndDelivery(),
                getSystematicsAndAcquisitionForm(),
                getArrivalAndOpening()
        );
    }
}
