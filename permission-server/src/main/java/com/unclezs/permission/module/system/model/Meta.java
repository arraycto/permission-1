package com.unclezs.permission.module.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author uncle
 * @date 2020/2/18 16:07
 */
@Data
public class Meta implements Serializable {
    private List<String> permission;
    private String keepAlive;
}
