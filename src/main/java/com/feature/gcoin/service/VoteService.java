package com.feature.gcoin.service;

import com.feature.gcoin.dto.UserDTO;
import com.feature.gcoin.dto.VoteDTO;
import com.feature.gcoin.model.User;

import java.util.List;

public interface VoteService {

    //list best of staff and number vote
    UserDTO bestStaffs(String address);

    // user vote user
    boolean bestStaffsVotes(String addressVoter, String addressCandidate);

    // choose best staff
    boolean addBestStaff(String address);

    Long getNumberOfVote(String address);

    boolean voteToStaff(VoteDTO voteDTO);

    boolean openSessionVote();
}
