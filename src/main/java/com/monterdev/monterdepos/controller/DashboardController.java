package com.monterdev.monterdepos.controller;

import com.monterdev.monterdepos.service.DashboardService;
import com.monterdev.monterdepos.service.POServiceImpl;

public class DashboardController extends DashboardService {
    private POServiceImpl serviceImpl;


    public DashboardController() {
        serviceImpl = POServiceImpl.instantiate();
        initializeItemList();
    }


}
