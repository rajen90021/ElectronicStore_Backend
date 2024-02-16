package com.rajen.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class pagableResponse<T> {
	
	
	private List<T> content;
	private int pagenumber;
	private int pageseize;
	private long totalelement;
	private int totalpages;
	private boolean lastpage;

}
