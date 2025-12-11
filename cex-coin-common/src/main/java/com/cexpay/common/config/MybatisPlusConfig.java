//package com.cexpay.common.config;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
//import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
//import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// *
// */
//@Configuration
//public class MybatisPlusConfig {
//    /**
//     * 分页插件
//     */
//    @Bean
//    public PaginationInnerInterceptor mybatisPlusInterceptor() {
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        paginationInnerInterceptor.setDbType(DbType.MYSQL);
//        return paginationInnerInterceptor;
//    }
//
//    /**
//     * 乐观锁
//     *
//     * @Vserion
//     */
//    @Bean
//    public OptimisticLockerInnerInterceptor mybatisPlusOptimisticLockerInterceptor() {
//        return new OptimisticLockerInnerInterceptor();
//    }
//
//    /**
//     * 主键生成策略
//     */
//    @Bean
//    public IKeyGenerator iKeyGenerator() {
//        return new H2KeyGenerator();//
//    }
//
//}
