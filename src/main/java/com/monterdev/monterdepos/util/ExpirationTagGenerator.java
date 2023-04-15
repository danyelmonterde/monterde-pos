package com.monterdev.monterdepos.util;

import com.monterdev.monterdepos.dao.ExpirationTagDao;
import com.monterdev.monterdepos.model.ExpirationTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ExpirationTagGenerator {


    private static String startingTag = "0";
    private static int min = 100;
    private static int max = 999;

    public static String generateExpirationTag(){
        ExpirationTagDao expirationTagDao = ExpirationTagDao.getInstance();
        List<ExpirationTag> expirationTagList = expirationTagDao.getExpirationTagList();
        String generatedExpirationTag = startingTag + String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
        Optional<ExpirationTag> initialExpirationTag = expirationTagList.stream().filter(e-> e.getExpirationTag().equals(generatedExpirationTag)).findFirst();
        if(!initialExpirationTag.isPresent()){
            return generatedExpirationTag;
        }else{
            List<ExpirationTag> tags = new ArrayList<>();
            expirationTagList.stream().forEach(e->{
                if(!e.getExpirationTag().equals(generatedExpirationTag)){
                    tags.add(e);
                }
            });
            Optional<ExpirationTag> optionalExpirationTag = tags.stream().filter(e-> e.getItemCount() == 0).findFirst();
            return optionalExpirationTag.isPresent() ? optionalExpirationTag.get().getExpirationTag(): generateExpirationTag();
        }
    }
}
