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
public class SmartphoneConfiguration {
    private List<String> availableColors;
    private List<Integer> batteryCapacities;
    private List<String> availableAccessories;
}
