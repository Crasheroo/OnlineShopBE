package com.crashero.model.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputerConfiguration {
    private List<String> availableProcessors;
    private List<Integer> availableRamOptions;
}
