package com.oyyy.gulimall.ware.vo;

import lombok.Data;

@Data
public class PurchaseItemDoneVo {
    //{itemId:1,status:4,reason:""}
    private Long itemId;  //采购单id
    private Integer status;  //状态
    private String reason;  //原因
}
