package net.Askbd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import net.Askbd.documents.Misc;
import net.Askbd.repos.MiscRepository;

@Service
public class CachingService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MiscRepository miscRepository;

    public Misc getCategoriesInfo() {
        Misc categoryInfo = (Misc) redisTemplate.opsForValue().get("categoryList");
        //Misc misc = null;
        if(categoryInfo == null) {
            categoryInfo = miscRepository.findByItemName("categoryList");
            redisTemplate.opsForValue().set("categoryList", categoryInfo);
        } else {
            System.out.println("from redis");
            System.out.println(categoryInfo);
        }
        return categoryInfo;
    }
}
