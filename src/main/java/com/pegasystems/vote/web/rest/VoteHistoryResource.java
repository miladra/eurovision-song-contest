package com.pegasystems.vote.web.rest;

import com.pegasystems.vote.service.VoteHistoryService;
import com.pegasystems.vote.service.VoteService;
import com.pegasystems.vote.service.dto.VoteHistoryDTO;
import com.pegasystems.vote.utility.HeaderUtil;
import com.pegasystems.vote.utility.ResponseUtil;
import com.pegasystems.vote.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pegasystems.vote.domain.VoteHistory}.
 */
@RestController
@RequestMapping("/api")
public class VoteHistoryResource {

    private final Logger log = LoggerFactory.getLogger(VoteHistoryResource.class);

    private static final String ENTITY_NAME = "voteHistory";

    @Value("${spring.application.name}")
    private String applicationName;

    private final VoteHistoryService voteHistoryService;

    private final VoteService voteService;
    private final MessageSource messages;

    public VoteHistoryResource(VoteHistoryService voteHistoryService, VoteService voteService, MessageSource messages) {
        this.voteHistoryService = voteHistoryService;
        this.voteService = voteService;
        this.messages = messages;
    }

    /**
     * {@code POST  /vote/{year}} : Create a new voteHistory.
     * @param year the year of the voteHistoryDTO to save.
     * @param voteHistoryDTO the voteHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voteHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the voteHistoryDTO is not valid,
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/v1/votes/{year}")
    public ResponseEntity<VoteHistoryDTO> createVoteHistory(@PathVariable(value = "year", required = true) final Integer year,
                                                            @Valid @RequestBody VoteHistoryDTO voteHistoryDTO,
                                                            @RequestParam(required = false, defaultValue = "en") String lang) throws Exception {
        log.debug("REST request to save VoteHistory : {}", voteHistoryDTO);

        if (voteHistoryDTO.getId() != null) {
            throw new BadRequestAlertException(String.format(messages.getMessage("vote.alreadyhaveid", null, new Locale(lang))), ENTITY_NAME, "alreadyhaveid");
        }
        if (voteHistoryDTO.getUserID() == null) {
            throw new BadRequestAlertException(String.format(messages.getMessage("vote.useridnotexist", null, new Locale(lang))), ENTITY_NAME, "useridnotexist");
        } else {
            Optional<VoteHistoryDTO> result = voteHistoryService.findByUserIDAndYear(voteHistoryDTO.getUserID(),year);
            if(result.isPresent()){
                throw new BadRequestAlertException(String.format(messages.getMessage("request.id.duplicated", null, new Locale(lang))), ENTITY_NAME, "useridduplicated");
            }
        }

        VoteHistoryDTO result = voteHistoryService.save(year,voteHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /votes/:year} : report 1st, 2nd and the 3rd place for each year.
     *
     * @param year the year of the VoteResponseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the VoteResponseDTO,
     * or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v1/votes/{year}")
    public ResponseEntity<Map<String,String>> getAnnualReport(@PathVariable(value = "year", required = true) final Integer year,
                                                              @RequestParam(required = false, defaultValue = "en") String lang) {
        log.debug("REST request to get Vote : {}", year);
        Map<String, String> voteResponse = voteService.annualReport(year);
        return ResponseUtil.wrapOrNotFound(Optional.of(voteResponse));
    }

    /**
     * {@code GET  /votes} : find out the top three favorite songs for each country.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votes in body.
     */
    @GetMapping("/v1/votes/{year}/{country-from}")
    public ResponseEntity<Map<String, String>> getTopThreeFavoriteSongs(@PathVariable(value = "year", required = true) final Integer year,
                                                                        @PathVariable(value = "country-from", required = true) final String countryFrom,
                                                                        @RequestParam(required = false, defaultValue = "en") String lang) {
        log.debug("REST request to get all Votes");
        Map<String, String> voteResponse= voteService.topThreeFavoriteSongsEachCountry(year , countryFrom);
        return ResponseUtil.wrapOrNotFound(Optional.of(voteResponse));
    }





}
