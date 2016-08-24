package com.nmakademija.nmaakademija.entity;

import java.io.Serializable;

/**
 * Created by NMA on 2016.08.23.
 */
public class Role implements Serializable {
    private String name;
    private int section;

    public String getName() {
        return name;
    }

    public int getSection() {
        return section;
    }
}
