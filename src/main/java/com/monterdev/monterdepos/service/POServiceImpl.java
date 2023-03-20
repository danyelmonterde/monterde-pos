package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.model.POSInstance;

public class POServiceImpl  {

    private static POServiceImpl service;
    private POSInstance posInstance;


    public static POServiceImpl instantiate() {
        if (service == null) {
            service = new POServiceImpl();
            return service;
        } else {
            return service;
        }

    }

    public POSInstance getInstance() {
        if (posInstance == null) {
            this.posInstance = new POSInstance();
            return this.posInstance;
        } else {
            return this.posInstance;
        }
    }

}
