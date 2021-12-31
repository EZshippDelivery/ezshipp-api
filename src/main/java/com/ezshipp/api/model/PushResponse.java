package com.ezshipp.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysql.cj.xdevapi.Result;

import lombok.Data;

@Data
public class PushResponse {

	 @JsonProperty("multicast_id")
	    private String multiCastId;

	    @JsonProperty("canonical_ids")
	    private String canonicalIds;

	    private int success;

	    private int failure;

	    private List<Result> results;

	    public String results()    {
	        StringBuilder sb  = new StringBuilder();
	        for (Result result : results) {
	         //  sb.append(success == 0 ? result.getError() : result.getMessageId());
	           sb.append(", ");
	        }
	        return sb.toString();
	    }
}
