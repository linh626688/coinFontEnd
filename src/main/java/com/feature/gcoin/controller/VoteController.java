package com.feature.gcoin.controller;

import com.feature.gcoin.common.util.Constants;
import com.feature.gcoin.dto.ServicesDTO;
import com.feature.gcoin.dto.VoteDTO;
import com.feature.gcoin.dto.reponse.Response;
import com.feature.gcoin.dto.request.UserRequest;
import com.feature.gcoin.model.User;
import com.feature.gcoin.service.ServicesService;
import com.feature.gcoin.service.UserService;
import com.feature.gcoin.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET, value = "/bestStaffs")
    public ResponseEntity<?> bestStaffs() {
        List<User> lst = userService.findAll();
        return ResponseEntity.ok(new Response(Constants.SUCCESS, "Successful", lst));
    }

    @RequestMapping(method = POST, value = "/voteToStaff")
    public ResponseEntity<?> voteToStaff(@RequestBody VoteDTO voteDTO){
        boolean res = voteService.voteToStaff(voteDTO);
        return ResponseEntity.ok(new Response(Constants.SUCCESS, "Successful", res));
    }

    @RequestMapping(method = POST, value = "/addBestStaff")
    public ResponseEntity<?> addBestStaff(@RequestBody String address) {
        boolean response = voteService.addBestStaff(address);
        return ResponseEntity.ok(new Response(Constants.SUCCESS, "Successful", response));
    }

    @RequestMapping(method = GET, value = "/getstatusVoter/{address}")
    public ResponseEntity<?> getstatusVoter(String address) {
        Long numberOfVote = voteService.getNumberOfVote(address);
        return ResponseEntity.ok(new Response(Constants.SUCCESS, "Successful", numberOfVote));
    }
    @RequestMapping(method = POST, value = "/openSessionVote")
    public ResponseEntity<?> openSessionVote() {
        boolean response = voteService.openSessionVote();
        return ResponseEntity.ok(new Response(Constants.SUCCESS, "Successful", response));
    }

}