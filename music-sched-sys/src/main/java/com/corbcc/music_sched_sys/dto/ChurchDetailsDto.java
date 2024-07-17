package com.corbcc.music_sched_sys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChurchDetailsDto {

	private UUID id;
    private String churchName;
    private String acronym;
}
