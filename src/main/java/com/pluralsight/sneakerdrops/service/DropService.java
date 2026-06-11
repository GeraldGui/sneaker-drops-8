package com.pluralsight.sneakerdrops;

import org.springframework.stereotype.Service;

@Service
public class DropService {

    public String getStatus() {
        return "Sneaker drops loading...";
    }
}
