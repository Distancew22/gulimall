package com.oyyy.gulimall.product.feign;

import com.oyyy.common.to.SkuReductionTo;
import com.oyyy.common.to.SpuBoundTo;
import com.oyyy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gulimall-coupon")
public interface CouponFeignService {
    /**
     * 1、couponFeignService.saveSpuBounds(spuBoundTo)找到这个远程服务
     * 2、将saveSpuBounds(@RequestBody SpuBoundTo spuBounds)请求体转换为json
     * 3、找到gulimall-coupon服务，发送/coupon/spubounds/save请求
     * 4、远程服务save(@RequestBody SpuBoundsEntity spuBounds)收到请求
     *    将请求体中的json转换为SpuBoundsEntity
     *
     *    只要json数据模型是兼容的。双方服务无需使用同一个to
     */
    @PostMapping("/coupon/spubounds/save")
    public R saveSpuBounds(@RequestBody SpuBoundTo spuBounds);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    public R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
