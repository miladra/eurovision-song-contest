package com.amazing.vote.web.rest;

/**
 * Used this interface for moving Swagger annotation into this and spare main controller.
 */

import com.amazing.vote.service.dto.VoteHistoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

public interface IVoteHistoryResource {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created with body the new vote.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VoteHistoryDTO.class))}),
            @ApiResponse(responseCode = "400", description = "The vote has already an ID.", content = @Content)})
    @Operation(summary = "Create a new stock")
    public ResponseEntity<VoteHistoryDTO> createVoteHistory(@PathVariable(value = "year", required = true) final Integer year,
                                                            @Valid @RequestBody VoteHistoryDTO voteHistoryDTO,
                                                            @RequestParam(required = false, defaultValue = "en") String lang) throws Exception;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get 1st, 2nd and the 3rd place for each year.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VoteHistoryDTO.class))}),})
    @Operation(summary = "Get 1st, 2nd and the 3rd place for each year.")
    public ResponseEntity<Map<String,String>> getAnnualReport(@PathVariable(value = "year", required = true) final Integer year,
                                                              @RequestParam(required = false, defaultValue = "en") String lang);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Top Three Favorite Songs", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VoteHistoryDTO.class))}),})
    @Operation(summary = "Get Top Three Favorite Songs")
    public ResponseEntity<Map<String, String>> getTopThreeFavoriteSongs(@PathVariable(value = "year", required = true) final Integer year,
                                                                        @PathVariable(value = "country-from", required = true) final String countryFrom,
                                                                        @RequestParam(required = false, defaultValue = "en") String lang);
}
