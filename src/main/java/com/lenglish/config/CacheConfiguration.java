package com.lenglish.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Config.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.CustomerUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.CustomerUser.class.getName() + ".exams", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.CustomerUser.class.getName() + ".lessons", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Post.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Post.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Room.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Lesson.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Lesson.class.getName() + ".customerUsers", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Feedback.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Question.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Question.class.getName() + ".tests", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Answer.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Exam.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Exam.class.getName() + ".questions", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.Exam.class.getName() + ".customerUsers", jcacheConfiguration);
            cm.createCache(com.lenglish.domain.LessonLog.class.getName(), jcacheConfiguration);
            cm.createCache(com.lenglish.domain.ExamLog.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
