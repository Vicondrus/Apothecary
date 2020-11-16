package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.services.PillBoxService;
import org.springframework.stereotype.Service;

@Service
public class PillBoxServiceImpl implements PillBoxService {

    @Override public String testService(String testString) {
        System.out.println("Working: " + testString);
        return "LOL. It works!";
    }
}
